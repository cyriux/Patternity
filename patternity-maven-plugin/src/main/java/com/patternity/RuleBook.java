/**
 * 
 */
package com.patternity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.patternity.annotation.designpattern.Composite;
import com.patternity.ast.ClassElement;
import com.patternity.rule.Rule;
import com.patternity.rule.RuleContext;

/**
 * Verifies allowed dependencies based on the design decisions (class
 * stereotypes, package in layers...) declared through annotations.
 */
@Composite
public class RuleBook implements Rule {

	private final List<Rule> rules = new ArrayList<Rule>();

	public RuleBook(final Rule... rules) {
		this(Arrays.asList(rules));
	}

	public RuleBook(final List<Rule> rules) {
		this.rules.addAll(rules);
	}

	public void verify(final ClassElement element, final RuleContext context) {
		for (Rule rule : rules) {
			rule.verify(element, context);
		}
	}

	@Override
	public String toString() {
		return "RuleEngine " + rules.size() + " rules";
	}

}
