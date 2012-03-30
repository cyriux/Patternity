/**
 * 
 */
package com.patternity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies allowed dependencies based on the design decisions (class
 * stereotypes, package in layers...) declared through annotations.
 * 
 * @author Mohamed Bourogaa
 * @author Cyrille Martraire
 */
public class DependencyVerifier {

	private final Set<String> forbiddenCases;

	public DependencyVerifier(final String forbiddenCase) {
		this.forbiddenCases = Collections.singleton(forbiddenCase);
	}

	public DependencyVerifier(final Set<String> forbiddenCases) {
		this.forbiddenCases = forbiddenCases;
	}

	public Collection<Violation> verifyDependencies(File file) {
		final List<Violation> violations = new ArrayList<Violation>();
		final Iterable<String> className = scan(file);
		for (String from : className) {
			verifyDependencies(from, violations);
		}
		return violations;
	}

	protected Collection<String> scan(File f) {
		return Collections.<String> singleton("com.patternity.sample.Price");
	}

	protected void verifyDependencies(String from, final List<Violation> violations) {
		final Iterable<String> references = referencesOf(from);
		for (String to : references) {
			if (!isAllowedDependency(from, to)) {
				violations.add(new Violation(from, to));
			}
		}
	}

	protected Iterable<String> referencesOf(String from) {
		return Collections.<String> singleton("Integer");
	}

	protected Iterable<String> annotationsOf(String from) {
		final String annotation = from.endsWith("Price") ? "com.patternity.annotation.ValueObject"
				: "com.patternity.annotation.Entity";
		return Collections.<String> singleton(annotation);
	}

	protected boolean isAllowedDependency(String from, String to) {
		final Iterable<String> annotationsFrom = annotationsOf(from);
		for (String annFrom : annotationsFrom) {
			final Iterable<String> annotationsTo = annotationsOf(to);
			for (String annTo : annotationsTo) {
				if (isForbiddenCase(annFrom, annTo)) {
					return false;
				}
			}
		}
		return true;
	}

	protected boolean isForbiddenCase(String annFrom, String annTo) {
		final String currentCase = annFrom + "->" + annTo;
		return forbiddenCases.contains(currentCase);
	}

}
