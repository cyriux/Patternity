package com.patternity.rule.basic;

import com.patternity.ast.*;
import com.patternity.rule.ClassRule;
import com.patternity.rule.RuleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class ValueObjectStateMustNotDependOnEntity implements ClassRule {

    private static final Logger logger = LoggerFactory.getLogger(ValueObjectStateMustNotDependOnEntity.class);

    @Override
    public void validate(ClassElement classModel, RuleContext context) {
        if (!isElligibleToRule(classModel, context))
            return;

        StringBuilder invalidFields = new StringBuilder();
        for (FieldElement model : classModel.getFields()) {
            DependOnEntityVisitor visitor = new DependOnEntityVisitor(context);
            visitor.enterModel(model);
            if (visitor.isInvalid())
                invalidFields.append(model.getFieldName()).append(", ");
        }
        if (invalidFields.length() > 0) {
            invalidFields.setLength(invalidFields.length() - 2);
            context.reportViolation("ValueObjectStateMustNotDependOnEntity", "The value object '" + classModel.getQualifiedName() + "' contains fields that reference directly or indirectly entity: " + invalidFields);
        }
    }

    private boolean isElligibleToRule(ClassElement classModel, RuleContext context) {
        return context.getConfiguration().isValueObject(classModel);
    }

    public static class DependOnEntityVisitor extends ModelVisitor {
        private Set<String> traversed = new HashSet<String>();
        private boolean isInvalid;
        private RuleContext context;

        public DependOnEntityVisitor(RuleContext context) {
            this.context = context;
        }

        @Override
        public void enterModel(ModelElement<?> model) {
            logger.debug("Analysing {}", model);

            switch (model.getModelType()) {
                case ANNOTATION:
                    isInvalid |= dependOnEntity((AnnotationElement) model);
                    break;
                case FIELD:
                    isInvalid |= dependOnEntity((FieldElement) model);
                    break;
                case CLASS:
                    ClassElement classModel = (ClassElement) model;
                    // prevent re-entrant case
                    if (!traversed.add(classModel.getQualifiedName()))
                        return;
                    isInvalid |= dependOnEntity(classModel);
                    break;
                case METHOD:
                    break;
            }
            if (isInvalid)
                markDone();
        }

        public RuleContext getContext() {
            return context;
        }

        public boolean isInvalid() {
            return isInvalid;
        }

        private boolean dependOnEntity(ClassElement model) {
            if (context.getConfiguration().isEntity(model))
                return true;
            if (directDepencyOnEntity(model))
                return true;
            for (FieldElement fieldModel : model.getFields()) {
                enterModel(fieldModel);
            }
            return false;
        }

        private boolean dependOnEntity(AnnotationElement model) {
            if (directDepencyOnEntity(model))
                return true;
            return false;
        }

        private boolean dependOnEntity(FieldElement model) {
            if (directDepencyOnEntity(model))
                return true;
            return false;
        }

        private boolean directDepencyOnEntity(ModelElement<?> model) {
            RuleContext context = getContext();
            ModelRepository repository = context.getModelRepository();
            for (String qualifiedName : model.getDependencies()) {
                ClassElement oModel = repository.findModel(qualifiedName);
                if (oModel != null) {
                    enterModel(oModel);
                    if (isDone())
                        return true;
                }
                else {
                    logger.debug("Model '{}' not present in repository: one assumes it does not participate to any rules violation", qualifiedName);
                }

            }
            return false;
        }

    }

}
