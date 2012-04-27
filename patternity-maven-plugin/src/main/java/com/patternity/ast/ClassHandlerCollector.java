package com.patternity.ast;

/**
 * Standard handler that collects each element into a list to retrieve later
 */
public class ClassHandlerCollector implements ClassHandler {

	private ClassElement collected;

	@Override
	public void handleClass(ClassElement model) {
		this.collected = model;
	}

	public ClassElement getCollected() {
		return collected;
	}
}
