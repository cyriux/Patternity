package com.patternity.rule.basic;

import com.patternity.ast.ClassElement;
import com.patternity.ast.FieldElement;
import com.patternity.rule.Rule;
import com.patternity.rule.RuleContext;

/**
 * A rule that verifies that classes marked by the 'source' tag must not have
 * any persistent reference to any classes marked by the 'target' tag
 */
public class ForbiddenFieldDependencyRule implements Rule {

	private final String sourceTag;
	private final String targetTag;
	private final transient String toString;


	public ForbiddenFieldDependencyRule(String sourceTag, String targetTag) {
		this.sourceTag = sourceTag;
		this.targetTag = targetTag;
		toString = "ForbiddenFieldDependencyRule from: " + sourceTag + " to: " + targetTag;
	}

	@Override
	public void validate(ClassElement classElement, RuleContext context) {
		if (!isElligible(classElement, context))
			return;

		final StringBuilder forbiddenReferences = new StringBuilder();
		for (FieldElement field : classElement.getFields()) {
			final ClassElement fieldType = typeOf(field, context);
			if (context.isMarked(fieldType, targetTag)) {
				forbiddenReferences.append(fieldType.getQualifiedName()).append(", ");
			}
		}

		if (forbiddenReferences.length() > 0) {
			forbiddenReferences.setLength(forbiddenReferences.length() - 2);
			context.reportViolation(this, "The class '" + classElement.getQualifiedName()
					+ "' has forbidden dependencies: " + forbiddenReferences);
		}
	}

	protected boolean isElligible(ClassElement classElement, RuleContext context) {
		return context.isMarked(classElement, sourceTag);
	}

	private final static ClassElement typeOf(FieldElement field, RuleContext context) {
		for (String qualifiedName : field.getDependencies()) {
			ClassElement fieldType = context.findElement(qualifiedName);
			return fieldType;
		}
		return null;
	}

	@Override
	public String toString() {
		return toString;
	}

}
