package com.patternity.rule;

import com.patternity.ast.ClassElement;

/**
 * Represents a rule to validate a class against the given context
 */
public interface ClassRule {
	void validate(ClassElement model, RuleContext context);
}
