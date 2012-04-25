package com.patternity.rule;

import com.patternity.ast.ClassElement;
import com.patternity.ast.ModelElement;

/**
 * Represents the context each rule uses to perform its verification and to
 * report diagnostics
 */
public interface RuleContext {

	public void reportViolation(String rule, String message);

	public ClassElement findModel(String qualifiedName);

	public boolean isMarked(final ModelElement<?> element, final String tag);
}
