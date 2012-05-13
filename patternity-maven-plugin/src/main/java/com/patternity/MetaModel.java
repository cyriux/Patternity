package com.patternity;

import com.patternity.annotation.ddd.stereotype.ValueObject;
import com.patternity.ast.ClassElement;
import com.patternity.ast.ModelElement;

/**
 * Represents the metamodel of packages, classes and their methods and fields,
 * including their annotations. The purpose of this model is to make it easy for
 * the verification rules to do their job.
 */
@ValueObject
public interface MetaModel extends Iterable<ClassElement> {

	ClassElement findElement(String qualifiedName);

	boolean isMarked(final ModelElement<?> element, final String tag);

	int size();

}
