package com.patternity.rule.basic;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.patternity.ast.AnnotationElement;
import com.patternity.ast.ClassElement;
import com.patternity.ast.FieldElement;
import com.patternity.ast.ModelElement;
import com.patternity.ast.ModelVisitor;
import com.patternity.rule.Rule;
import com.patternity.rule.RuleContext;

/**
 * A rule that verifies that classes marked by the 'source' tag must not have
 * any reference (state) to any classes marked by the 'target' tag
 * 
 * Note: this is almost a duplicate of {@link ForbiddenFieldDependencyRule}
 * which should evolve to verify every kind of dependencies, not only
 * dependencies of state
 */
public class ForbiddenStateDependencyRule implements Rule {

	private final String sourceTag;
	private final String targetTag;
	private final transient String toString;

	public ForbiddenStateDependencyRule(String sourceTag, String targetTag) {
		this.sourceTag = sourceTag;
		this.targetTag = targetTag;
		toString = "ForbiddenStateDependencyRule from: " + sourceTag + " to: " + targetTag;
	}

	@Override
	public void validate(ClassElement classModel, RuleContext context) {
		if (!isElligible(classModel, context))
			return;

		final StringBuilder invalidFields = new StringBuilder();
		for (FieldElement model : classModel.getFields()) {
			DependOnForbiddenTagVisitor visitor = new DependOnForbiddenTagVisitor(targetTag, context);
			visitor.enterModel(model);
			if (visitor.isInvalid())
				invalidFields.append(model.getFieldName()).append(", ");
		}
		if (invalidFields.length() > 0) {
			invalidFields.setLength(invalidFields.length() - 2);
			context.reportViolation(this, "The value object '" + classModel.getQualifiedName()
					+ "' contains fields that reference directly or indirectly entity: " + invalidFields);
		}
	}

	protected boolean isElligible(ClassElement element, RuleContext context) {
		return context.isMarked(element, sourceTag);
	}

	/**
	 * A visitor to fail if any traversed dependency is marked by the forbidden
	 * tag
	 */
	public static class DependOnForbiddenTagVisitor extends ModelVisitor {
		private final String forbiddenTag;

		private final RuleContext context;
		private final Set<String> traversed = new HashSet<String>();
		private boolean isInvalid;

		private static final Logger logger = LoggerFactory.getLogger(ForbiddenStateDependencyRule.class);

		public DependOnForbiddenTagVisitor(final String forbiddenTag, RuleContext context) {
			this.context = context;
			this.forbiddenTag = forbiddenTag;
		}

		@Override
		public void enterModel(final ModelElement<?> model) {
			logger.debug("Analysing {}", model);

			switch (model.getModelType()) {
			case ANNOTATION:
				isInvalid |= dependOnEntity((AnnotationElement) model);
				break;
			case FIELD:
				isInvalid |= dependOnEntity((FieldElement) model);
				break;
			case CLASS:
				final ClassElement classModel = (ClassElement) model;
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

		public boolean isInvalid() {
			return isInvalid;
		}

		private boolean dependOnEntity(ClassElement model) {
			if (context.isMarked(model, forbiddenTag))
				return true;
			if (directDependencyOnEntity(model))
				return true;
			for (FieldElement fieldModel : model.getFields()) {
				enterModel(fieldModel);
			}
			return false;
		}

		private boolean dependOnEntity(AnnotationElement model) {
			return directDependencyOnEntity(model);
		}

		private boolean dependOnEntity(FieldElement model) {
			return directDependencyOnEntity(model);
		}

		private boolean directDependencyOnEntity(ModelElement<?> element) {
			for (String qualifiedName : element.getDependencies()) {
				final ClassElement dependency = context.findElement(qualifiedName);
				if (dependency != null) {
					enterModel(dependency);
					if (isDone())
						return true;
				} else {
					logger.debug(
							"Model '{}' not present in repository: one assumes it does not participate to any rules violation",
							qualifiedName);
				}

			}
			return false;
		}

	}

	@Override
	public String toString() {
		return toString;
	}

}
