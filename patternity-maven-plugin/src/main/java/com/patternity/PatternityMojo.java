package com.patternity;

import java.io.File;
import java.util.Collection;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Goal to verify allowed dependencies.
 * 
 * Example: mvn patternity:verify-dependencies
 * 
 * @goal verify-dependencies
 * @phase test
 * 
 * @author Mohamed Bourogaa
 * @author Cyrille Martraire
 */
public class PatternityMojo extends AbstractMojo {

	private final static String DEFAULT_RULE = "com.patternity.annotation.ValueObject->com.patternity.annotation.Entity";

	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${project.build.directory}"
	 * @required
	 */
	private File outputDirectory;

	public void execute() throws MojoExecutionException, MojoFailureException {
		System.out.println("PatternityMojo verify-dependencies starting...");
		final Collection<Violation> violations = processClasses();
		if (!violations.isEmpty()) {
			throw new MojoFailureException(violations.toString());
		}
	}

	protected Collection<Violation> processClasses() {
		return new DependencyVerifier(DEFAULT_RULE).verifyDependencies(new File(outputDirectory, "classes"));
	}

	@Override
	public String toString() {
		return "PatternityMojo 'verify-dependencies' outputDirectory=" + outputDirectory;
	}

}
