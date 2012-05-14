package com.patternity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collection;

import org.hamcrest.number.OrderingComparison;
import org.junit.Test;

import com.patternity.annotation.ddd.stereotype.DomainService;
import com.patternity.annotation.ddd.stereotype.Entity;
import com.patternity.annotation.ddd.stereotype.ValueObject;
import com.patternity.annotation.designpattern.Mixin;
import com.patternity.annotation.testing.AcceptanceTests;
import com.patternity.rule.basic.FinalFieldsRule;
import com.patternity.rule.basic.ForbiddenFieldDependencyRule;
import com.patternity.rule.basic.ForbiddenStateDependencyRule;

@AcceptanceTests
public class EatYourOwnDogFoodTest {

	private static final int CLASS_NUMBER_IN_PROJECT = 43;

	private static final String MIXIN = toQualifiedName(Mixin.class);
	private static final String VALUE_OBJECT = toQualifiedName(ValueObject.class);
	private static final String ENTITY = toQualifiedName(Entity.class);
	private static final String SERVICE = toQualifiedName(DomainService.class);

	private static final ForbiddenFieldDependencyRule FORBIDDEN_MIXIN_TO_VO = new ForbiddenFieldDependencyRule(
			MIXIN, VALUE_OBJECT);

	@Test
	public void testProcess() {
		final File outputDirectory = testRoot();

		final MetaModel metaModel = new MetaModelBuilder()
				.build(outputDirectory);
		System.out.println(outputDirectory.getAbsoluteFile());
		System.out.println("scanned " + metaModel.size() + " classes");

		// fragile
		assertThat(metaModel.size(),
				OrderingComparison.greaterThan(CLASS_NUMBER_IN_PROJECT));
		assertThat(
				metaModel
						.findElement(toQualifiedName(ForbiddenStateDependencyRule.class)),
				notNullValue());
		assertThat(metaModel.findElement("com/patternity/ast/package-info"),
				notNullValue());

		final RuleBook ruleBook = loadRuleBook();

		final Processor processor = new Processor(ruleBook);
		final Collection<Violation> violations = processor.process(metaModel);
		System.out.println(violations);
		assertEquals(1, violations.size());
		final Violation violation = violations.iterator().next();
		assertEquals(FORBIDDEN_MIXIN_TO_VO, violation.getRule());
	}

	public RuleBook loadRuleBook() {
		final ForbiddenFieldDependencyRule vo2entity = new ForbiddenFieldDependencyRule(
				VALUE_OBJECT, ENTITY);
		final ForbiddenFieldDependencyRule vo2service = new ForbiddenFieldDependencyRule(
				VALUE_OBJECT, SERVICE);
		final FinalFieldsRule voHazFinalFields = new FinalFieldsRule(
				VALUE_OBJECT);

		return new RuleBook(FORBIDDEN_MIXIN_TO_VO, vo2entity, vo2service,
				voHazFinalFields);
	}

	private final static File testRoot() {
		final String base = EatYourOwnDogFoodTest.class.getResource("/")
				.getFile();
		return new File(base + "..", "classes");
	}

	private final static String toQualifiedName(final Class<?> clazz) {
		return clazz.getName().replace('.', '/');
	}

}
