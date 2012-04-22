package com.patternity.rule.basic;

import com.patternity.ast.ClassModel;
import com.patternity.ast.FieldModel;
import com.patternity.rule.ClassRule;
import com.patternity.rule.RuleContext;

/**
 *
 */
public class ValueObjectFieldsMustBeFinal implements ClassRule {

    @Override
    public void validate(ClassModel classModel, RuleContext context) {
        if (!isElligibleToRule(classModel, context))
            return;

        StringBuilder nonFinalFields = new StringBuilder();
        for (FieldModel model : classModel.getFieldModels()) {
            if (!model.getModifiers().isFinal() && !model.getModifiers().isStatic()) {
                nonFinalFields.append(model.getFieldName()).append(", ");
            }
        }

        if (nonFinalFields.length() > 0) {
            nonFinalFields.setLength(nonFinalFields.length() - 2);
            context.reportViolation("ValueObjectFieldsMustBeFinal", "The value object '" + classModel.getQualifiedName() + "' contains non final fields: " + nonFinalFields);
        }
    }

    private boolean isElligibleToRule(ClassModel classModel, RuleContext context) {
        return context.getConfiguration().isValueObject(classModel);
    }

}
