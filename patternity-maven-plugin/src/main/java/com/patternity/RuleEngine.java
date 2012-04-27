/**
 * 
 */
package com.patternity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.patternity.rule.Rule;

/**
 * Verifies allowed dependencies based on the design decisions (class
 * stereotypes, package in layers...) declared through annotations.
 * 
 * @author Mohamed Bourogaa
 * @author Cyrille Martraire
 */
public class RuleEngine {

	private final List<Rule> rules = new ArrayList<Rule>();

	public Collection<Violation> verifyDependencies(File file) {
		final List<Violation> violations = new ArrayList<Violation>();
		return violations;
	}

}
