package com.patternity.ast;

/**
 * Represents a method in the metamodel
 */
public class MethodElement extends ModelElement<MethodElement> {
	private final String name;

	public MethodElement(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public ModelType getModelType() {
		return ModelType.METHOD;
	}
}
