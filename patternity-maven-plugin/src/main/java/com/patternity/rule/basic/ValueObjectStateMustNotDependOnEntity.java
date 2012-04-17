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
    public void validate(ClassModel classModel, RuleContext context) {
        if (!isElligibleToRule(classModel, context))
            return;

        StringBuilder invalidFields = new StringBuilder();
        for (FieldModel model : classModel.getFieldModels()) {
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

    private boolean isElligibleToRule(ClassModel classModel, RuleContext context) {
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
        public void enterModel(Model<?> model) {
            logger.debug("Analysing {}", model);

            switch (model.getModelType()) {
                case ANNOTATION:
                    isInvalid |= dependOnEntity((AnnotationModel) model);
                    break;
                case FIELD:
                    isInvalid |= dependOnEntity((FieldModel) model);
                    break;
                case CLASS:
                    ClassModel classModel = (ClassModel) model;
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

        private boolean dependOnEntity(ClassModel model) {
            if (context.getConfiguration().isEntity(model))
                return true;
            if (directDepencyOnEntity(model))
                return true;
            for (FieldModel fieldModel : model.getFieldModels()) {
                enterModel(fieldModel);
            }
            return false;
        }

        private boolean dependOnEntity(AnnotationModel model) {
            if (directDepencyOnEntity(model))
                return true;
            return false;
        }

        private boolean dependOnEntity(FieldModel model) {
            if (directDepencyOnEntity(model))
                return true;
            return false;
        }

        private boolean directDepencyOnEntity(Model<?> model) {
            RuleContext context = getContext();
            ModelRepository repository = context.getModelRepository();
            for (String qualifiedName : model.getDependencies()) {
                ClassModel oModel = repository.findModel(qualifiedName);
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
