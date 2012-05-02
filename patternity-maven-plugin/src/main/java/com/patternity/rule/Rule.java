package com.patternity.rule;

import com.patternity.ast.ClassElement;

/**
 * Represents a rule to verify a class against the given context
 */
public interface Rule {
	void verify(ClassElement element, RuleContext context);
}
