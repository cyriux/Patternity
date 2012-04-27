package com.patternity;

import static com.patternity.ast.ClassElementTest.newClassElement;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.patternity.annotation.AcceptanceTests;
import com.patternity.ast.ClassElement;
import com.patternity.ast.FieldElement;
import com.patternity.data.annotation.Entity;
import com.patternity.data.annotation.ValueObject;
import com.patternity.rule.basic.ForbiddenFieldDependencyRule;

@AcceptanceTests
public class ProcessorTest {

	public final static String VALUE_OBJECT = ValueObject.class.toString();
	public final static String ENTITY = Entity.class.toString();
	public final static String VO_CLASSNAME = "com/patternity/xxx/MyVO";
	public final static String ENTITY_CLASSNAME = "com/patternity/xxx/MyEntity";

	@Test
	public void NoRuleNoElement() {
		final Processor processor = new Processor(new RuleBook());
		final MetaModel metaModel = new MapBasedMetaModel();
		assertTrue(processor.process(metaModel).isEmpty());
	}

	@Test
	public void OneRuleNoElement() {
		final Processor processor = new Processor(loadRuleBook());
		final MetaModel metaModel = new MapBasedMetaModel();
		assertTrue(processor.process(metaModel).isEmpty());
	}

	@Test
	public void OneRuleNoViolation() {
		final Processor processor = new Processor(loadRuleBook());
		final ClassElement vo = newClassElement(VO_CLASSNAME, VALUE_OBJECT);
		final MetaModel metaModel = new MapBasedMetaModel(vo);
		assertTrue(processor.process(metaModel).isEmpty());
	}

	@Test
	public void OneRule_Violated() {
		final Processor processor = new Processor(loadRuleBook());
		final ClassElement vo = newClassElement(VO_CLASSNAME, VALUE_OBJECT);
		final ClassElement entity = newClassElement(ENTITY_CLASSNAME, ENTITY);
		setFieldDependency(vo, "fieldToEntity", entity);
		final MetaModel metaModel = new MapBasedMetaModel(vo, entity);
		assertThat(processor.process(metaModel).size(), equalTo(1));
	}

	private final static void setFieldDependency(ClassElement classElement, String fieldName, ClassElement fieldType) {
		final FieldElement field = new FieldElement(fieldName);
		field.dependsOn(fieldType.getQualifiedName());
		classElement.addField(field);
	}

	public RuleBook loadRuleBook() {
		return new RuleBook(new ForbiddenFieldDependencyRule(VALUE_OBJECT, ENTITY));
	}

}
