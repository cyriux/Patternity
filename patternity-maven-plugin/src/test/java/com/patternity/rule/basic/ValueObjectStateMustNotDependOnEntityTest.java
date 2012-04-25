package com.patternity.rule.basic;

import com.patternity.ast.ClassElement;
import com.patternity.ast.ModelRepository;
import com.patternity.data.annotation.Entity;
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
public class ValueObjectStateMustNotDependOnEntityTest {
    private ValueObjectStateMustNotDependOnEntity rule;
    private RuleContext context;
    private Configuration configuration;
    private ModelRepository modelRepository;

    @Before
    public void setUp() {
        rule = new ValueObjectStateMustNotDependOnEntity();
        context = Mockito.mock(RuleContext.class);
        configuration = Mockito.mock(Configuration.class);
        modelRepository = Mockito.mock(ModelRepository.class);
        when(context.getConfiguration()).thenReturn(configuration);
        when(context.getModelRepository()).thenReturn(modelRepository);
    }

    @Test
    public void ruleIsVerified_noDependencyNoFields() throws IOException {
        ClassElement classModel = Usecases.scanClass(VO1_noDependencyNoFields.class);
        when(configuration.isValueObject(same(classModel))).thenReturn(true);

        rule.validate(classModel, context);
        verify(context).getConfiguration();
        verifyNoMoreInteractions(context);
    }

    @Test
    public void ruleIsVerified_noDependency() throws IOException {
        ClassElement classModel = Usecases.scanClass(VO1_noDependency.class);
        when(configuration.isValueObject(same(classModel))).thenReturn(true);

        rule.validate(classModel, context);
        verify(context).getConfiguration();
        // why 5? actually i don't know how to express 'anyNumberOfTimes()'
        verify(context, Mockito.atMost(5)).getModelRepository();
        verifyNoMoreInteractions(context);
    }

    @Test
    public void ruleIsNotVerified_fieldDependency() throws IOException {
        ClassElement classModelVO = Usecases.scanClass(VO1_withDependencyInField.class);
        ClassElement classModelEN = Usecases.scanClass(E1.class);
        when(configuration.isValueObject(same(classModelVO))).thenReturn(true);
        when(configuration.isEntity(same(classModelEN))).thenReturn(true);
        when(modelRepository.findModel("com/patternity/rule/basic/ValueObjectStateMustNotDependOnEntityTest$E1")).thenReturn(classModelEN);

        rule.validate(classModelVO, context);
        verify(context).reportViolation(eq("ValueObjectStateMustNotDependOnEntity"),
                argThat(stringContainsInOrder(asList("ref"))));
    }

    @Test
    public void ruleIsNotVerified_indirectFieldDependency() throws IOException {
        ClassElement classModel = Usecases.scanClass(VO1_withDependencyIndirectFieldType.class);
        ClassElement classModelRF = Usecases.scanClass(Ref.class);
        ClassElement classModelEN = Usecases.scanClass(E1.class);
        when(configuration.isValueObject(same(classModel))).thenReturn(true);
        when(configuration.isEntity(same(classModelRF))).thenReturn(false);
        when(configuration.isEntity(same(classModelEN))).thenReturn(true);
        when(modelRepository.findModel("com/patternity/rule/basic/ValueObjectStateMustNotDependOnEntityTest$E1")).thenReturn(classModelEN);
        when(modelRepository.findModel("com/patternity/rule/basic/ValueObjectStateMustNotDependOnEntityTest$Ref")).thenReturn(classModelRF);

        rule.validate(classModel, context);
        verify(context).reportViolation(eq("ValueObjectStateMustNotDependOnEntity"),
                argThat(stringContainsInOrder(asList("ref"))));
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
