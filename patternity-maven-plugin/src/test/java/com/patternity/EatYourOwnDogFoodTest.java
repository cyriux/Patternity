package com.patternity;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collection;

import org.junit.Test;

import com.patternity.rule.basic.FinalFieldsRule;
import com.patternity.rule.basic.ForbiddenFieldDependencyRule;

public class EatYourOwnDogFoodTest {

	private static final String MIXIN = "com/patternity/annotation/designpattern/Mixin";
	private static final String VALUE_OBJECT = "com/patternity/annotation/ddd/stereotype/ValueObject";
	private static final ForbiddenFieldDependencyRule FORBIDDEN_MIXIN_2_VO = new ForbiddenFieldDependencyRule(MIXIN,
			VALUE_OBJECT);

	@Test
	public void testProcess() {
		final File outputDirectory = testRoot();

		final MetaModel metaModel = new MetaModelBuilder().build(outputDirectory);

		final RuleBook ruleBook = loadRuleBook();

		final Processor processor = new Processor(ruleBook);
		final Collection<Violation> violations = processor.process(metaModel);
		System.out.println(violations);
		assertEquals(1, violations.size());
		final Violation violation = violations.iterator().next();
		assertEquals(FORBIDDEN_MIXIN_2_VO, violation.getRule());
	}

	public RuleBook loadRuleBook() {
		final String entity = "com/patternity/annotation/Entity";
		final String service = "com/patternity/annotation/Service";
		final ForbiddenFieldDependencyRule vo2entity = new ForbiddenFieldDependencyRule(VALUE_OBJECT, entity);
		final ForbiddenFieldDependencyRule vo2service = new ForbiddenFieldDependencyRule(VALUE_OBJECT, service);
		final FinalFieldsRule voHazFinalFields = new FinalFieldsRule(VALUE_OBJECT);

		return new RuleBook(FORBIDDEN_MIXIN_2_VO, vo2entity, vo2service, voHazFinalFields);
	}

	private File testRoot() {
		final String base = EatYourOwnDogFoodTest.class.getResource("/").getFile();
		return new File(base + "..", "classes");
	}

}
