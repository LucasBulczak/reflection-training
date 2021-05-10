package com.alisarrian.reflection.members;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "Members")
class ModifiersTest {

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
    void givenFQNOfClass_whenGetModifiers_thenReturnModifiers() {
        assertThat(Modifier.toString(firstClazz.getModifiers()))
                .as("Returns the Java language modifiers for this class or interface, encoded in an integer. " +
                        "The modifiers consist of the Java Virtual Machine's constants for public, protected, private, " +
                        "final, static, abstract and interface; they should be decoded using the methods of class Modifier. ")
                .contains("abstract")
                .doesNotContain("public", "static");
    }

    @Test
    void givenFQNOfClass_whenGetTypeParameters_thenReturnTypeParameters() {
        assertThat(Arrays.toString(firstClazz.getTypeParameters()))
                .as("Should return an array of TypeVariable objects")
                .contains("L", "R");
    }

    @Test
    void givenFQNOfClass_whenGetGenericInterface_thenReturnInterfaceThatClazzImplements() {
        assertThat(Arrays.toString(firstClazz.getInterfaces()))
                .as("Should determine the interfaces implemented by the class or interface represented by this object.")
                .contains("Serializable");
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
