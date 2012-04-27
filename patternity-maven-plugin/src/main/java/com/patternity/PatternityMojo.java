package com.patternity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.patternity.ast.ClassElement;
import com.patternity.ast.ClassHandler;
import com.patternity.ast.ClassScanner;
import com.patternity.ast.asm.AsmScanner;

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
			printViolations(violations);
			throw new MojoFailureException(violations.toString());
		}
		System.out.println("PatternityMojo verify-dependencies done.");
	}

	private void printViolations(final Collection<Violation> violations) {
		System.err.println("PatternityMojo verify-dependencies found " + violations.size() + " violations: "
				+ violations);
		for (Violation violation : violations) {
			System.err.println(violation);
		}
	}

	protected Collection<Violation> processClasses() {
		final File root = new File(outputDirectory, "classes");
		System.out.println("PatternityMojo verify-dependencies starting...");
		final List<ClassElement> classes = new ClassesBuilder().parseAll(root);
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return "PatternityMojo 'verify-dependencies' outputDirectory=" + outputDirectory;
	}

}
