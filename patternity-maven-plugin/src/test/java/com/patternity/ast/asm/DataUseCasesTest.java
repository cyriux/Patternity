package com.patternity.ast.asm;

import com.patternity.ClassWithDependencies;
import com.patternity.ast.AnnotationModel;
import com.patternity.ast.ClassHandler;
import com.patternity.ast.ClassModel;
import com.patternity.ast.FieldModel;
import com.patternity.data.domain.*;
import com.patternity.data.service.RepositoryBase;
import com.patternity.data.service.StoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

/**
 *
 */
public class DataUseCasesTest {

    private ClassHandler handler;
    private InputStream stream;

    // Collected during tests
    private ClassModel scannedClass;

    @Before
    public void setUp() {
        handler = new ClassHandler() {
            @Override
            public void handleClass(ClassModel model) {
                scannedClass = model;
            }
        };
    }

    @After
    public void tearDown() throws IOException {
        if (stream != null)
            stream.close();
    }

    @Test
    public void basicCase() throws IOException {
        new AsmScanner().scan(openStreamOf(ClassWithDependencies.class), handler);
    }

    @Test
    public void epic1_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(Epic1.class), handler);
        assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic1"));

        assertThatAllDependenciesMatch(//
                "java/lang/Object",//
                "java/util/List",//
                "com/patternity/data/service/StoryRepository",//
                "com/patternity/data/domain/Story"
        );
    }

    @Test
    public void epic2_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(Epic2.class), handler);
        assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic2"));

        assertThatAllDependenciesMatch(//
                "java/lang/Object",//
                "java/util/List",//
                "com/patternity/data/service/StoryRepository",//
                "com/patternity/data/domain/Story"
        );
    }

    @Test
    public void epic3_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(Epic3.class), handler);
        assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic3"));

        assertThatAllDependenciesMatch(//
                "java/lang/Object",//
                "java/util/List",//
                "com/patternity/data/service/StoryRepository",//
                "com/patternity/data/domain/Story"
        );
    }

    @Test
    public void epic4_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(Epic4.class), handler);
        assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic4"));

        assertThatAllDependenciesMatch(//
                "java/lang/Object",//
                "java/util/List",//
                "com/patternity/data/infrastructure/Provider",//
                "com/patternity/data/service/StoryRepository",//
                "com/patternity/data/domain/Story"
        );
    }

    @Test
    public void epic4WithCast_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(Epic4WithCast.class), handler);
        assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic4WithCast"));

        assertThatAllDependenciesMatch(//
                "java/lang/Object",//
                "java/util/List",//
                "com/patternity/data/infrastructure/Provider",//
                "com/patternity/data/service/StoryRepository",//
                "com/patternity/data/domain/Story"
        );
    }


    @Test
    public void epic5_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(Epic5.class), handler);
        assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic5"));

        assertThatAllDependenciesMatch(//
                "java/lang/Object",//
                "java/util/List",//
                "com/patternity/data/domain/Story",//
                "java/lang/Exception"
        );
    }

    @Test
    public void epic5AnonymousClass_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(Epic5.class, "$1"), handler);
        assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Epic5$1"));

        assertThatAllDependenciesMatch(//
                "java/lang/Object",//
                "java/util/List",//
                "com/patternity/data/service/StoryRepository",//
                "com/patternity/data/domain/Story",//
                "java/lang/Exception",//
                "com/patternity/data/domain/Epic5",//
                "java/util/concurrent/Callable"
        );
    }

    @Test
    public void story_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(Story.class), handler);
        assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/Story"));

        assertThatAllDependenciesMatch(//
                "java/lang/Object"//
        );
    }

    @Test
    public void user1_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(User1.class), handler);
        assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/User1"));

        assertThatAllDependenciesMatch(//
                "java/lang/Object",//
                "com/patternity/data/annotation/Entity"//
        );
    }

    @Test
    public void user2_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(User2.class), handler);
        assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/User2"));

        assertThatAllDependenciesMatch(//
                "java/lang/Object",//
                "com/patternity/data/annotation/Entity",//
                "com/patternity/data/domain/UserId1"
        );
    }

    @Test
    public void user2_fieldDependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(User2.class), handler);

        List<FieldModel> fieldModels = scannedClass.getFieldModels();
        assertThat(fieldModels, notNullValue());
        assertThat(fieldModels.size(), equalTo(1));

        FieldModel fieldModel = fieldModels.get(0);
        assertThat(fieldModel, notNullValue());
        assertThat(fieldModel.getFieldName(), equalTo("userId1"));
        assertThat(fieldModel.getDependencies(), containsInAnyOrder("com/patternity/data/domain/UserId1"));
    }

    @Test
    public void userId1_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(UserId1.class), handler);
        assertThat(scannedClass.getQualifiedName(), equalTo("com/patternity/data/domain/UserId1"));

        assertThatAllDependenciesMatch(//
                "java/lang/Object",//
                "java/lang/String",//
                "com/patternity/data/annotation/ValueObject"//
        );
    }

    @Test
    public void userId1_annotation() throws IOException {
        new AsmScanner().scan(openStreamOf(UserId1.class), handler);
        assertThat(scannedClass, notNullValue());

        List<AnnotationModel> annotationModels = scannedClass.getAnnotationModels();
        assertThat(annotationModels, notNullValue());
        assertThat(annotationModels.size(), equalTo(1));

        AnnotationModel annotationModel = annotationModels.get(0);
        assertThat(annotationModel.getQualifiedName(), equalTo("com/patternity/data/annotation/ValueObject"));
    }

    @Test
    public void userId2_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(UserId2.class), handler);
        assertThatAllDependenciesMatch(//
                "java/lang/Object",//
                "com/patternity/data/domain/User2",//
                "com/patternity/data/domain/UserId1",//
                "com/patternity/data/annotation/ValueObject",//
                "java/lang/IllegalStateException"
        );
    }

    @Test
    public void repositoryBase_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(RepositoryBase.class), handler);
        assertThatAllDependenciesMatch(//
                "java/lang/Object",//
                "java/lang/String",//
                "com/patternity/data/annotation/Repository"
        );
    }

    @Test
    public void storyRepository_dependencies() throws IOException {
        new AsmScanner().scan(openStreamOf(StoryRepository.class), handler);
        assertThatAllDependenciesMatch(//
                "java/util/List",//
                "com/patternity/data/domain/Story",//
                "com/patternity/data/service/RepositoryBase"
        );
    }

    @Test
    public void storyRepository_superClass() throws IOException {
        new AsmScanner().scan(openStreamOf(StoryRepository.class), handler);
        assertThat(scannedClass.getSuperQualifiedName(),  //
                equalTo("com/patternity/data/service/RepositoryBase"));
    }

    private void assertThatAllDependenciesMatch(String... dependencies) {
        assertThat(scannedClass.collectAllDependencies(), containsInAnyOrder(dependencies));
    }

    private InputStream openStreamOf(Class<?> clazz) {
        return openStreamOf(clazz, "");
    }

    private InputStream openStreamOf(Class<?> clazz, String classNameSuffix) {
        stream = this.getClass().getResourceAsStream(formatResourceName(clazz, classNameSuffix));
        return stream;
    }

    private String formatResourceName(Class<?> clazz, String classNameSuffix) {
        return "/" + clazz.getName().replace('.', '/') + classNameSuffix + ".class";
    }
}
