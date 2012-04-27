package com.patternity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.patternity.ast.ClassElement;
import com.patternity.rule.Rule;
import com.patternity.rule.RuleContext;
import com.patternity.rule.RuleContextMixin;

/**
 * The main processor that does everything, called from the maven pojo
 */
public class Processor {
	private final RuleBook ruleBook;

	public Processor(final RuleBook ruleBook) {
		this.ruleBook = ruleBook;
	}

	public Collection<Violation> process(final MetaModel metaModel) {
		final List<Violation> violations = new ArrayList<Violation>();
		final ViolationReporter reporter = new ViolationReporter() {

			@Override
			public void reportViolation(Rule rule, String message) {
				violations.add(new Violation(rule, message));
			}
			@Override
			public String toString() {
				return "Processor/ViolationReporter";
			}
		};
		final RuleContext context = new RuleContextMixin(metaModel, reporter);
		for (ClassElement classElement : metaModel) {
			ruleBook.verify(classElement, context);
		}
		return violations;
	}

}
