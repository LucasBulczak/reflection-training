package com.alisarrian.reflection.members;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "Members")
class AnnotationsTest {

    private static final String PACKAGE = "com.alisarrian.reflection.members.";
    private static final String FIRST_CLASS = PACKAGE + "FirstClass";
    private static final String SECOND_CLASS = PACKAGE + "SecondClass";

    private Class<?> firstClazz;
    private Class<?> secondClazz;

    @BeforeMethod
    void setUp() throws ClassNotFoundException {
        firstClazz = Class.forName(FIRST_CLASS);
        secondClazz = Class.forName(SECOND_CLASS);
    }

    @Test
    void givenFQNOfClass_whenGetAnnotations_thenReturnAnnotationsThatClazzImplements() {
        assertThat(Arrays.toString(firstClazz.getAnnotations()))
                .as("Returns this element's annotation for the specified type if such an annotation is present, else null.")
                .contains("CustomAnnotationRuntime");
    }

    @Test
    void givenFQNOfClass_whenGetAnnotations_thenReturnAnnotationsThatClazzImplements2() {
        assertThat(Arrays.toString(secondClazz.getAnnotations()))
                .as("Should return empty array because annotation does not have a runtime retention policy.")
                .doesNotContain("CustomAnnotation");
    }

}
