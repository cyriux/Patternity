package com.patternity;

import com.patternity.rule.Rule;

/**
 * Represents a violation of a rule
 */
// ValueObject
public class Violation {

	private final Rule rule;
	private final String message;

	public Violation(Rule rule, String message) {
		this.rule = rule;
		this.message = message;
	}

	public Rule getRule() {
		return rule;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Rule:" + rule + " is violated:" + message;
	}

}