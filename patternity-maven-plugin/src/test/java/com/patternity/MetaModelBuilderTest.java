package com.patternity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.hamcrest.number.OrderingComparison;
import org.junit.Test;


public class MetaModelBuilderTest {

	@Test
	public void testProcess() {
		final File root = testRoot();
		final MetaModel metaModel = new MetaModelBuilder().build(root);

		System.out.println(root.getAbsoluteFile());
		System.out.println("scanned " + metaModel.size() + " classes");

		// fragile
		assertThat(metaModel.size(), OrderingComparison.greaterThan(75));
		assertThat(metaModel.findElement("com/patternity/rule/basic/ForbiddenStateDependencyRule"), notNullValue());
		assertThat(metaModel.findElement("com/patternity/ast/package-info"), notNullValue());
	}

	private File testRoot() {
		final String base = MetaModelBuilder.class.getResource("/").getFile();
		return new File(base + "..", "classes");
	}

}
