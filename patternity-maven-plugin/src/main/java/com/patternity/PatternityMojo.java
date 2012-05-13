package com.patternity;

import java.io.File;
import java.util.Collection;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.patternity.rule.basic.FinalFieldsRule;
import com.patternity.rule.basic.ForbiddenFieldDependencyRule;

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
        getLog().info("PatternityMojo verify-dependencies starting...");
		final Collection<Violation> violations = processClasses();
		if (!violations.isEmpty()) {
			printViolations(violations);
			throw new MojoFailureException(violations.toString());
		}
        getLog().info("PatternityMojo verify-dependencies done.");
	}

	private void printViolations(final Collection<Violation> violations) {
        getLog().error("PatternityMojo verify-dependencies found " + violations.size() + " violations");
		for (Violation violation : violations) {
            getLog().error(violation.toString());
		}
	}

	protected Collection<Violation> processClasses() {
        getLog().info("PatternityMojo verify-dependencies starting...");
		final File root = new File(outputDirectory, "classes");

		final MetaModel metaModel = new MetaModelBuilder().build(root);
        getLog().info(metaModel.toString());

		final RuleBook ruleBook = loadRuleBook();
        getLog().info(ruleBook.toString());
		return new Processor(ruleBook).process(metaModel);
	}

	public RuleBook loadRuleBook() {
		final String vo = "com/patternity/annotation/ValueObject";
		final String entity = "com/patternity/annotation/Entity";
		final String service = "com/patternity/annotation/Service";
		final ForbiddenFieldDependencyRule vo2entity = new ForbiddenFieldDependencyRule(vo, entity);
		final ForbiddenFieldDependencyRule vo2service = new ForbiddenFieldDependencyRule(vo, service);
		final FinalFieldsRule voHazFinalFields = new FinalFieldsRule(vo);
		return new RuleBook(vo2entity, vo2service, voHazFinalFields);
	}

	@Override
	public String toString() {
		return "PatternityMojo 'verify-dependencies' outputDirectory=" + outputDirectory;
	}

}
