package com.patternity.rule.basic;

import com.patternity.ast.ClassElement;
import com.patternity.ast.FieldElement;
import com.patternity.rule.Rule;
import com.patternity.rule.RuleContext;

/**
 * A
 */
public class FinalFieldsRule implements Rule {

	private final String tag;
	private final transient String toString;

	public FinalFieldsRule(String tag) {
		this.tag = tag;
		toString = "FinalFieldsRule if tag: " + tag;
	}

	@Override
	public void verify(ClassElement element, RuleContext context) {
		if (!isElligibleToRule(element, context))
			return;

		StringBuilder nonFinalFields = new StringBuilder();
		for (FieldElement model : element.getFields()) {
			if (!model.getModifiers().isFinal() && !model.getModifiers().isStatic()) {
				nonFinalFields.append(model.getFieldName()).append(", ");
			}
		}

		if (nonFinalFields.length() > 0) {
			nonFinalFields.setLength(nonFinalFields.length() - 2);
			context.reportViolation(this, "The class '" + element.getQualifiedName() + "' has non final fields: "
					+ nonFinalFields);
		}
	}

	private boolean isElligibleToRule(ClassElement element, RuleContext context) {
		return context.isMarked(element, tag);
	}

	@Override
	public String toString() {
		return toString;
	}
}
