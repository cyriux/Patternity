package com.patternity.rule.basic;

import com.patternity.ast.ClassElement;
import com.patternity.data.annotation.ValueObject;
import com.patternity.rule.Configuration;
import com.patternity.rule.RuleContext;
import com.patternity.usecase.Usecases;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static java.util.Arrays.asList;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.*;

/**
 *
 */
public class ValueObjectFieldsMustBeFinalTest {

    private ValueObjectFieldsMustBeFinal rule;
    private RuleContext context;
    private Configuration configuration;

    @Before
    public void setUp() {
        rule = new ValueObjectFieldsMustBeFinal();
        context = Mockito.mock(RuleContext.class);
        configuration = Mockito.mock(Configuration.class);
        when(context.getConfiguration()).thenReturn(configuration);
    }

    @Test
    public void ruleIsNotVerified() throws IOException {
        ClassElement classModel = Usecases.scanClass(VO1_nonFinalField.class);
        when(configuration.isValueObject(same(classModel))).thenReturn(true);

        rule.validate(classModel, context);

        verify(context).reportViolation(eq("ValueObjectFieldsMustBeFinal"),
                argThat(stringContainsInOrder(asList("uuid"))));
    }

    @Test
    public void ruleIsVerified_finalField() throws IOException {
        ClassElement classModel = Usecases.scanClass(VO2_finalField.class);
        when(configuration.isValueObject(same(classModel))).thenReturn(true);

        rule.validate(classModel, context);

        verify(context).getConfiguration();
        verifyNoMoreInteractions(context);
    }

    @Test
    public void ruleIsVerified_finalField_nonFinalStatic() throws IOException {
        ClassElement classModel = Usecases.scanClass(VO3_finalField_nonFinalStatic.class);
        when(configuration.isValueObject(same(classModel))).thenReturn(true);

        rule.validate(classModel, context);

        verify(context).getConfiguration();
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
