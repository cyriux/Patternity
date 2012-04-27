package com.patternity;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.util.List;

import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.number.OrderingComparison;
import org.junit.Test;

import com.patternity.ast.ClassElement;

public class ClassesBuilderTest {

	@Test
	public void testProcess() {
		final File root = testRoot();
		final List<ClassElement> classes = new ClassesBuilder().parseAll(root);

		System.out.println(root.getAbsoluteFile());
		System.out.println("scanned " + classes.size() + " classes");

		// fragile
		assertThat(classes, IsCollectionWithSize.hasSize(OrderingComparison.greaterThan(75)));
		assertThat(classes, hasItem(new ClassElement("com/patternity/rule/basic/ForbiddenStateDependencyRule$1")));
		assertThat(classes, hasItem(new ClassElement("com/patternity/ast/package-info")));
	}

	private File testRoot() {
		final String base = ClassesBuilder.class.getResource("/").getFile();
		return new File(base + "..", "classes");
	}

}
