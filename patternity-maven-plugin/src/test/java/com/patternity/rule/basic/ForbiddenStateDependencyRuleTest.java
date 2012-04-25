package com.patternity.rule.basic;

import static java.util.Arrays.asList;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.patternity.ast.ClassElement;
import com.patternity.data.annotation.Entity;
import com.patternity.data.annotation.ValueObject;
import com.patternity.rule.RuleContext;
import com.patternity.usecase.Usecases;

public class ForbiddenStateDependencyRuleTest {

	private static final String VALUE_OBJECT = "ValueObject";
	private static final String ENTITY = "Entity";

	private ForbiddenStateDependencyRule rule = new ForbiddenStateDependencyRule(VALUE_OBJECT, ENTITY);
	private RuleContext context;

	@Before
	public void setUp() {
		context = Mockito.mock(RuleContext.class);
	}

	@Test
	public void ruleIsVerified_noDependencyNoFields() throws IOException {
		ClassElement classModel = Usecases.scanClass(VO1_noDependencyNoFields.class);
		when(context.isMarked(same(classModel), same(VALUE_OBJECT))).thenReturn(true);

		rule.validate(classModel, context);
		verify(context, never()).reportViolation("some rule", "never happened");
	}

	@Test
	public void ruleIsVerified_noDependency() throws IOException {
		ClassElement classModel = Usecases.scanClass(VO1_noDependency.class);
		when(context.isMarked(same(classModel), same(VALUE_OBJECT))).thenReturn(true);

		rule.validate(classModel, context);
		verify(context, never()).reportViolation("some rule", "never happened");
	}

	@Test
	public void ruleIsNotVerified_fieldDependency() throws IOException {
		ClassElement vo = Usecases.scanClass(VO1_withDependencyInField.class);
		ClassElement entity = Usecases.scanClass(E1.class);
		when(context.isMarked(same(vo), same(VALUE_OBJECT))).thenReturn(true);
		when(context.isMarked(same(entity), same(ENTITY))).thenReturn(true);
		when(context.isMarked(same(entity), same(ENTITY))).thenReturn(true);

		when(context.findModel("com/patternity/rule/basic/ForbiddenStateDependencyRuleTest$E1")).thenReturn(entity);

		rule.validate(vo, context);
		verify(context).reportViolation(eq(rule.toString()), argThat(stringContainsInOrder(asList("ref"))));
	}

	@Test
	public void ruleIsNotVerified_indirectFieldDependency() throws IOException {
		ClassElement vo = Usecases.scanClass(VO1_withDependencyIndirectFieldType.class);
		ClassElement refEntity = Usecases.scanClass(Ref.class);
		ClassElement entity = Usecases.scanClass(E1.class);

		when(context.isMarked(same(vo), same(VALUE_OBJECT))).thenReturn(true);
		when(context.isMarked(same(refEntity), same(ENTITY))).thenReturn(false);
		when(context.isMarked(same(entity), same(ENTITY))).thenReturn(true);

		when(context.findModel("com/patternity/rule/basic/ForbiddenStateDependencyRuleTest$Ref")).thenReturn(refEntity);
		when(context.findModel("com/patternity/rule/basic/ForbiddenStateDependencyRuleTest$E1")).thenReturn(entity);

		rule.validate(vo, context);
		verify(context).reportViolation(eq(rule.toString()), argThat(stringContainsInOrder(asList("ref"))));
	}

	@ValueObject
	public static class VO1_noDependencyNoFields {
	}

	@ValueObject
	public static class VO1_noDependency {
		private String uuid;
	}

	@ValueObject
	public static class VO1_withDependencyInField {
		private E1 ref;
	}

	@ValueObject
	public static class VO1_withDependencyIndirectFieldType {
		private Ref ref;
	}

	public static class Ref {
		private E1 ref;
	}

	@Entity
	public static class E1 {
	}

}
