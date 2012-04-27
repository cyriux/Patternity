package com.patternity.ast.asm;

import com.patternity.ast.ClassElement;
import com.patternity.ast.ClassHandler;

/**
 * Standard handler that collects one single element
 */
public class SingleClassHandlerCollector implements ClassHandler {

	private ClassElement collected;

	@Override
	public void handleClass(ClassElement element) {
		this.collected = element;
	}

	public ClassElement getCollected() {
		return collected;
	}
}
