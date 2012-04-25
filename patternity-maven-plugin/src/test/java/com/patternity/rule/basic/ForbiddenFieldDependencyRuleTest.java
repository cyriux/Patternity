package com.patternity.rule.basic;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.patternity.ast.ClassElement;
import com.patternity.ast.ModelRepository;
import com.patternity.ast.PackageElement;
import com.patternity.data.domain.Epic6KnowsItsInfra;
import com.patternity.data.infra.JdbcEpicRepository;
import com.patternity.rule.Configuration;
import com.patternity.rule.RuleContext;
import com.patternity.usecase.Usecases;

public class ForbiddenFieldDependencyRuleTest {

	private ForbiddenFieldDependencyRule rule = new ForbiddenFieldDependencyRule("MyDomainModel", "InfraLayer");
	private RuleContext context;
	private Configuration configuration;
	private ModelRepository modelRepository;

	@Before
	public void setUp() {
		context = Mockito.mock(RuleContext.class);
		configuration = Mockito.mock(Configuration.class);
		modelRepository = Mockito.mock(ModelRepository.class);
		when(context.getConfiguration()).thenReturn(configuration);
		when(context.getModelRepository()).thenReturn(modelRepository);
	}

	@Test
	public void scanPackageInfo() throws IOException {
		final PackageElement packageElement = Usecases.scanPackage(this.getClass());
		assertThat(packageElement.getQualifiedName(), equalTo("com/patternity/rule/basic/package-info"));
		assertThat(packageElement.getAnnotations().get(0).getQualifiedName(),
				equalTo("com/patternity/data/annotation/InfraLayer"));
	}

	@Test
	public void ruleIsViolated() throws IOException {
		final ClassElement epic6 = Usecases.scanClass(Epic6KnowsItsInfra.class);
		final ClassElement repo = Usecases.scanClass(JdbcEpicRepository.class);

		when(context.isMarked(same(epic6), same("MyDomainModel"))).thenReturn(true);
		when(modelRepository.findModel("com/patternity/data/infra/JdbcEpicRepository")).thenReturn(repo);
		when(context.isMarked(same(repo), same("InfraLayer"))).thenReturn(true);

		rule.validate(epic6, context);
		verify(context).reportViolation(eq(rule.toString()),
				argThat(stringContainsInOrder(asList("has forbidden dependencies"))));

	}

}
