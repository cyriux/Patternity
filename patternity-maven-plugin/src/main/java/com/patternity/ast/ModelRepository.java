package com.patternity.ast;

import com.patternity.util.Visitor;

/**
 * The model, that includes every model element
 */
// @Repository
public interface ModelRepository {
	
	void add(ClassElement model);

	ClassElement findModel(String qualifiedName);

	void traverseModels(Visitor<ClassElement> visitor);
}
