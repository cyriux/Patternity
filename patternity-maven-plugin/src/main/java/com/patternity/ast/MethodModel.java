package com.patternity.ast;

/**
 * Represents a method in the metamodel
 */
public class MethodModel extends ModelElement<MethodModel> {
	private final String name;

	public MethodModel(String name) {
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
