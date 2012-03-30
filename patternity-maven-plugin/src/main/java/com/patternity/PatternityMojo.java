package com.patternity;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
