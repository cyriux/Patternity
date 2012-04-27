package com.patternity;

import java.util.ArrayList;
import java.util.List;

import com.patternity.rule.Rule;

/**
 * A simple implementation of 
 */
@Deprecated
public class AnalysisLogger implements ViolationReporter {

	private List<Violation> violations = new ArrayList<Violation>();

	@Override
	public void reportViolation(final Rule rule, final String message) {
		violations.add(new Violation(rule, message));
	}
}
