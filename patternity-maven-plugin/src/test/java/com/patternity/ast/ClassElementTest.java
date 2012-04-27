package com.patternity.ast;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClassElementTest {

	@Test
	public void packageNameFromQualifiedName() {
		assertThat(new ClassElement("com/patternity/xxx/MyClass").getPackageName(), equalTo("com/patternity/xxx"));
		assertThat(new ClassElement("com/patternity/xxx/MyClass$1").getPackageName(), equalTo("com/patternity/xxx"));
	}

	@Test
	public void isPackageInfo() {
		assertTrue(new ClassElement("com/patternity/xxx/package-info").isPackageInfo());
		assertFalse(new ClassElement("com/patternity/xxx/MyClass").isPackageInfo());
	}
	
	public final static ClassElement newClassElement(final String qName, final String... annotationNames) {
		final ClassElement element = new ClassElement(qName);
		for (String annotationName : annotationNames) {
			element.addAnnotation(new AnnotationElement(annotationName));
		}
		return element;
	}
}
