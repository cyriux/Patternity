package com.patternity.rule;

import com.patternity.annotation.ddd.stereotype.ValueObject;
import com.patternity.ast.ClassElement;

/**
 * Represents a rule to verify a class against the given context
 */
@ValueObject
public interface Rule {
	void verify(ClassElement element, RuleContext context);
}
