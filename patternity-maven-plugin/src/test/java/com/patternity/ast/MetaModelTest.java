package com.patternity.ast;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MetaModelTest {

	@Test
	public void find_Class() {
		final String name = "com/patternity/xxx/ToFind";
		final ClassElement element = new ClassElement(name);
		final String name2 = name + "2";
		final ClassElement element2 = new ClassElement(name2);
		final MetaModel model = new MetaModel(element, element2);
		assertThat(model.findElement(name), equalTo(element));
		assertThat(model.findElement(name2), equalTo(element2));
	}

	@Test
	public void find_Package() {
		final String name = "com/patternity/xxx/package-info";
		final ClassElement element = new ClassElement(name);
		final MetaModel model = new MetaModel(element);
		assertThat(model.findElement(name), equalTo(element));
	}

	@Test
	public void class_IsNotMarked() {
		final String name = "com/patternity/xxx/AnnotatedClass";
		final String tag = "fr/arolla/xxx/Annotation1";
		final ClassElement element = newClassElement(name);
		final MetaModel model = new MetaModel(element);
		assertFalse(model.isMarked(element, tag));
	}

	@Test
	public void class_IsMarked() {
		final String name = "com.patternity/xxx/AnnotatedClass";
		final String tag = "fr/arolla/xxx/Annotation1";
		final ClassElement element = newClassElement(name, tag);
		final MetaModel model = new MetaModel(element);
		assertTrue(model.isMarked(element, tag));
	}

	@Test
	public void class_IsPackageMarked() {
		final String packageInfo = "com/patternity/xxx/package-info";
		final String tag = "fr/arolla/xxx/Annotation1";
		final ClassElement packageInfoElement = newClassElement(packageInfo, tag);
		
		final String name = "com/patternity/xxx/ClassInPackage";
		final ClassElement element = newClassElement(name);

		final MetaModel model = new MetaModel(element, packageInfoElement);
		assertTrue(model.isMarked(element, tag));
	}
	
	@Test
	public void class_IsMarked_alias() {
		final String name = "com.patternity/xxx/AnnotatedClass";
		final String tag = "fr/arolla/xxx/Annotation1";
		final ClassElement element = newClassElement(name, tag);
		final MetaModel model = new MetaModel(element);
		assertTrue(model.isMarked(element, tag));
	}

	private final static ClassElement newClassElement(final String qName, final String... annotationNames) {
		final ClassElement element = new ClassElement(qName);
		for (String annotationName : annotationNames) {
			element.addAnnotation(new AnnotationElement(annotationName));
		}
		return element;
	}

}
