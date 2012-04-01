package com.patternity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

public class DependencyVerifierTest {

	@Test
	public void entityMayKnowvalueObject() throws Exception {
		final DependencyVerifier verifier = new DependencyVerifier("ValueObject->Entity");
		assertFalse(verifier.isForbiddenCase("Entity", "ValueObject"));
	}

	@Test
	public void valueObjectMustNotKnowEntity() throws Exception {
		final DependencyVerifier verifier = new DependencyVerifier("ValueObject->Entity");
		assertTrue(verifier.isForbiddenCase("ValueObject", "Entity"));
	}

	@Test
	public void test_isAllowedDependency() {
		final String forbiddenCase = "ValueObject->Entity";

		final DependencyVerifier verifier = new DependencyVerifier(forbiddenCase) {
			protected Iterable<String> annotationsOf(String from) {
				final String annotation = from.endsWith("VO") ? "ValueObject" : "Entity";
				return Collections.<String> singleton(annotation);
			}

		};
		assertTrue(verifier.isAllowedDependency("Entity", "MyVO"));
		assertFalse(verifier.isAllowedDependency("MyVO", "Entity"));
	}

}
