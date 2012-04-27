package com.patternity.rule.basic;

import static com.patternity.usecase.TestUsecases.scanClass;
import static java.util.Arrays.asList;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.patternity.ast.ClassElement;
import com.patternity.data.annotation.ValueObject;
import com.patternity.rule.RuleContext;

public class FinalFieldsRuleTest {

	private static final String VALUE_OBJECT = "ValueObject";

	private FinalFieldsRule rule = new FinalFieldsRule(VALUE_OBJECT);
	private RuleContext context;

	@Before
	public void setUp() {
		context = Mockito.mock(RuleContext.class);
	}

	@Test
	public void ruleIsNotVerified() throws IOException {
		ClassElement classModel = scanClass(VO1_nonFinalField.class);
		when(context.isMarked(same(classModel), same(VALUE_OBJECT))).thenReturn(true);

		rule.validate(classModel, context);

		verify(context).reportViolation(eq(rule.toString()), argThat(stringContainsInOrder(asList("uuid"))));
	}

	@Test
	public void ruleIsVerified_finalField() throws IOException {
		ClassElement classModel = scanClass(VO2_finalField.class);
		when(context.isMarked(same(classModel), same(VALUE_OBJECT))).thenReturn(true);

		rule.validate(classModel, context);

		verify(context).isMarked(same(classModel), same(VALUE_OBJECT));
		verifyNoMoreInteractions(context);
	}

	@Test
	public void ruleIsVerified_finalField_nonFinalStatic() throws IOException {
		ClassElement classModel = scanClass(VO3_finalField_nonFinalStatic.class);
		when(context.isMarked(same(classModel), same(VALUE_OBJECT))).thenReturn(true);

		rule.validate(classModel, context);

		verify(context).isMarked(same(classModel), same(VALUE_OBJECT));
		verifyNoMoreInteractions(context);
	}

	@ValueObject
	public static class VO1_nonFinalField {
		private String uuid;

		public VO1_nonFinalField(String uuid) {
			this.uuid = uuid;
		}
	}

	@ValueObject
	public static class VO2_finalField {
		private final String uuid;

		public VO2_finalField(String uuid) {
			this.uuid = uuid;
		}
	}

	@ValueObject
	public static class VO3_finalField_nonFinalStatic {
		public static String DEFAULT = null;
		private final String uuid;

		public VO3_finalField_nonFinalStatic(String uuid) {
			this.uuid = uuid;
		}
	}
}
