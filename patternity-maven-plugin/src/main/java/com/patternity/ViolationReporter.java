package com.patternity;

import com.patternity.annotation.properties.SideEffect;
import com.patternity.rule.Rule;

/**
 * The capability of reporting violations
 */
@SideEffect
public interface ViolationReporter {

	void reportViolation(final Rule rule, final String message);

}