package com.patternity.rule;

import java.util.Iterator;

import com.patternity.MetaModel;
import com.patternity.ViolationReporter;
import com.patternity.annotation.Mixin;
import com.patternity.ast.ClassElement;
import com.patternity.ast.ModelElement;

/**
 * A rule context implementation that delegates the work to a MetaModel and to a
 */
@Mixin
public class RuleContextMixin implements RuleContext {
	private final MetaModel metaModel;
	private final ViolationReporter violationReporter;

	public RuleContextMixin(MetaModel metaModel, ViolationReporter violationReporter) {
		this.metaModel = metaModel;
		this.violationReporter = violationReporter;
	}

	public ClassElement findElement(String qualifiedName) {
		return metaModel.findElement(qualifiedName);
	}

	public boolean isMarked(ModelElement<?> element, String tag) {
		return metaModel.isMarked(element, tag);
	}

	public Iterator<ClassElement> iterator() {
		return metaModel.iterator();
	}

	public int size() {
		return metaModel.size();
	}

	public void reportViolation(Rule rule, String message) {
		violationReporter.reportViolation(rule, message);
	}

	@Override
	public String toString() {
		return "RuleContextMixin based on: " + metaModel + " and on: " + violationReporter;
	}

}
