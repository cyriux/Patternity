package com.patternity.ast;

/**
 * A Visitor for operations that walk the metamodel
 */
// @Visitor
public class ModelVisitor {
	private boolean isDone;

	protected void markDone() {
		this.isDone = true;
	}

	public boolean isDone() {
		return isDone;
	}

	public void enterModel(ModelElement<?> model) {
	}

	public void exitModel(ModelElement<?> model) {
	}

}
