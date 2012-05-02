package com.patternity.ast.asm;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.patternity.ast.AnnotationElement;
import com.patternity.ast.ClassElement;
import com.patternity.ast.ClassHandler;
import com.patternity.ast.DependenciesCollector;
import com.patternity.ast.FieldElement;
import com.patternity.data.domain.Epic1;
import com.patternity.data.domain.Epic2;
import com.patternity.data.domain.Epic3;
import com.patternity.data.domain.Epic4;
import com.patternity.data.domain.Epic4WithCast;
import com.patternity.data.domain.Epic5;
import com.patternity.data.domain.Story;
import com.patternity.data.domain.User1;
import com.patternity.data.domain.User2;
import com.patternity.data.domain.UserId1;
import com.patternity.data.domain.UserId2;
import com.patternity.data.service.RepositoryBase;
import com.patternity.data.service.StoryRepository;

public class AsmScannerTest {

	// Collected during tests
	private ClassElement scannedClass;

	@Test
	public void basicCase() throws IOException {
		scan(Epic1.class);
		assertThat(scannedClass, notNullValue());
	}

	@Test
	public void epic1_dependencies() throws IOException {
		scan(Epic1.class);
		assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic1"));

		assertThatAllDependenciesMatch(//
				"java/lang/Object",//
				"java/util/List",//
				"com/patternity/data/service/StoryRepository",//
				"com/patternity/data/domain/Story");
	}

	@Test
	public void epic2_dependencies() throws IOException {
		scan(Epic2.class);
		assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic2"));

		assertThatAllDependenciesMatch(//
				"java/lang/Object",//
				"java/util/List",//
				"com/patternity/data/service/StoryRepository",//
				"com/patternity/data/domain/Story");
	}

	@Test
	public void epic3_dependencies() throws IOException {
		scan(Epic3.class);
		assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic3"));

		assertThatAllDependenciesMatch(//
				"java/lang/Object",//
				"java/util/List",//
				"com/patternity/data/service/StoryRepository",//
				"com/patternity/data/domain/Story");
	}

	@Test
	public void epic4_dependencies() throws IOException {
		scan(Epic4.class);
		assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic4"));

		assertThatAllDependenciesMatch(//
				"java/lang/Object",//
				"java/util/List",//
				"com/patternity/data/infrastructure/Provider",//
				"com/patternity/data/service/StoryRepository",//
				"com/patternity/data/domain/Story");
	}

	@Test
	public void epic4WithCast_dependencies() throws IOException {
		scan(Epic4WithCast.class);
		assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic4WithCast"));

		assertThatAllDependenciesMatch(//
				"java/lang/Object",//
				"java/util/List",//
				"com/patternity/data/infrastructure/Provider",//
				"com/patternity/data/service/StoryRepository",//
				"com/patternity/data/domain/Story");
	}

	@Test
	public void epic5_dependencies() throws IOException {
		scan(Epic5.class);
		assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic5"));

		assertThatAllDependenciesMatch(//
				"java/lang/Object",//
				"java/util/List",//
				"com/patternity/data/domain/Story",//
				"java/lang/Exception");
	}

	@Test
	public void epic5AnonymousClass_dependencies() throws IOException {
		scan(Epic5.class, "$1");
		assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic5$1"));

		assertThatAllDependenciesMatch(//
				"java/lang/Object",//
				"java/util/List",//
				"com/patternity/data/service/StoryRepository",//
				"com/patternity/data/domain/Story",//
				"java/lang/Exception",//
				"com/patternity/data/domain/Epic5",//
				"java/util/concurrent/Callable");
	}

	@Test
	public void story_dependencies() throws IOException {
		scan(Story.class);
		assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Story"));

		assertThatAllDependenciesMatch(//
		"java/lang/Object"//
		);
	}

	@Test
	public void user1_dependencies() throws IOException {
		scan(User1.class);
		assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/User1"));

		assertThatAllDependenciesMatch(//
				"java/lang/Object",//
				"com/patternity/data/annotation/Entity"//
		);
	}

	@Test
	public void user2_dependencies() throws IOException {
		scan(User2.class);
		assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/User2"));

		assertThatAllDependenciesMatch(//
				"java/lang/Object",//
				"com/patternity/data/annotation/Entity",//
				"com/patternity/data/domain/UserId1");
	}

	@Test
	public void user2_fieldDependencies() throws IOException {
		scan(User2.class);

		List<FieldElement> fieldModels = scannedClass.getFields();
		assertThat(fieldModels, notNullValue());
		assertThat(fieldModels.size(), equalTo(1));

		FieldElement fieldModel = fieldModels.get(0);
		assertThat(fieldModel, notNullValue());
		assertThat(fieldModel.getFieldName(), equalTo("userId1"));
		assertThat(fieldModel.getDependencies(), containsInAnyOrder("com/patternity/data/domain/UserId1"));
	}

	@Test
	public void userId1_dependencies() throws IOException {
		scan(UserId1.class);
		assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/UserId1"));

		assertThatAllDependenciesMatch(//
				"java/lang/Object",//
				"java/lang/String",//
				"com/patternity/data/annotation/ValueObject"//
		);
	}

	@Test
	public void userId1_annotation() throws IOException {
		scan(UserId1.class);
		assertThat(scannedClass, notNullValue());

		List<AnnotationElement> annotationModels = scannedClass.getAnnotations();
		assertThat(annotationModels, notNullValue());
		assertThat(annotationModels.size(), equalTo(1));

		AnnotationElement annotationModel = annotationModels.get(0);
		assertThat(annotationModel.getQualifiedName(), equalTo("com/patternity/data/annotation/ValueObject"));
	}

	@Test
	public void userId2_dependencies() throws IOException {
		scan(UserId2.class);
		assertThatAllDependenciesMatch(//
				"java/lang/Object",//
				"com/patternity/data/domain/User2",//
				"com/patternity/data/domain/UserId1",//
				"com/patternity/data/annotation/ValueObject",//
				"java/lang/IllegalStateException");
	}

	@Test
	public void repositoryBase_dependencies() throws IOException {
		scan(RepositoryBase.class);
		assertThatAllDependenciesMatch(//
				"java/lang/Object",//
				"java/lang/String",//
				"com/patternity/data/annotation/Repository");
	}

	@Test
	public void storyRepository_dependencies() throws IOException {
		scan(StoryRepository.class);
		assertThatAllDependenciesMatch(//
				"java/util/List",//
				"com/patternity/data/domain/Story",//
				"com/patternity/data/service/RepositoryBase");
	}

	@Test
	public void storyRepository_superClass() throws IOException {
		scan(StoryRepository.class);
		assertThat(scannedClass.getSuperQualifiedName(), //
				equalTo("com/patternity/data/service/RepositoryBase"));
	}

	private void assertThatAllDependenciesMatch(String... dependencies) {
		final DependenciesCollector collector = new DependenciesCollector();
		scannedClass.traverseModelTree(collector);
		final Set<String> allDependencies = collector.getDependencies();
		assertThat(allDependencies, containsInAnyOrder(dependencies));
	}

	private void scan(final Class<?> clazz) throws IOException {
		scan(clazz, "");
	}

	private void scan(final Class<?> clazz, final String classNameSuffix) throws IOException {
		final ClassHandler handler = new ClassHandler() {
			@Override
			public void handleClass(ClassElement model) {
				scannedClass = model;
			}
		};
		final InputStream stream = openStreamOf(clazz, classNameSuffix);
		new AsmScanner().scan(stream, handler);
		closeStream(stream);
	}

	private final static void closeStream(final InputStream stream) throws IOException {
		if (stream != null)
			stream.close();
	}

	private final InputStream openStreamOf(Class<?> clazz, String classNameSuffix) {
		return this.getClass().getResourceAsStream(formatResourceName(clazz, classNameSuffix));
	}

	private final static String formatResourceName(Class<?> clazz, String classNameSuffix) {
		return "/" + clazz.getName().replace('.', '/') + classNameSuffix + ".class";
	}
}
