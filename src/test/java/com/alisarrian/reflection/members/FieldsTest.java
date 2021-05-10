package com.alisarrian.reflection.members;

import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// TODO: 5/8/2021 translate

/**
 * The following tables provide a summary of all the member-locating methods and their characteristics.
 * <p>
 * Class Methods for Locating Fields
 * <p>
 * __________________________________________________________________________________________
 * | Class API              | List of members?  | Inherited members?    | Private members?  |
 * __________________________________________________________________________________________
 * | getDeclaredField()     | no                | no                    | yes               |
 * | getField()             | no                | yes                   | no                |
 * | getDeclaredFields()    | yes               | no                    | yes               |
 * | getFields()            | yes               | yes                   | no                |
 * __________________________________________________________________________________________
 */
@Test(groups = "Members")
class FieldsTest {
    private final String PUBLIC_FIELD = "publicField";
    private final String PRIVATE_FIELD = "privateField";
    private final String PUBLIC_SUPER_TYPE_FIELD = "publicSuperTypeField";
    private final String SUPER_TYPE_FIELD = "superTypeField";

    private final SubType INSTANCE = new SubType();


    @Test
    void givenPublicField_whenGetDeclaredField_thenReturnProperField() throws Exception {

        Field field = SubType.class.getDeclaredField(PUBLIC_FIELD);

        assertThat(field.getName())
                .as("Returns a Field object that reflects the specified declared field of the class" +
                        "or interface represented by this Class object." +
                        "The name parameter is a String that specifies the simple name of the desired field. ")
                .isEqualTo(PUBLIC_FIELD);

        Object value = field.get(INSTANCE);
        assertThat(((String) value))
                .as("The value of a particular field is obtained by calling Field.get(Object) and passing" +
                        "an object of the class containing the field.")
                .isEqualTo("some value");

    }

    @Test
    void givenPrivateField_whenGetDeclaredField_thenReturnProperField() throws Exception {

        Field field = SubType.class.getDeclaredField(PRIVATE_FIELD);

        assertThat(field.getName())
                .as("The getDeclaredField() method allows us to access private fields.")
                .isEqualTo(PRIVATE_FIELD);

        assertThat((field.get(INSTANCE)))
                .as("The value of a particular field is obtained by calling Field.get(Object) and passing" +
                        "an object of the class containing the field.")
                .isEqualTo(10);

    }

    @Test
    void givenInheritedField_whenGetDeclaredField_thenThrowNoSuchFieldEx() {

        assertThatExceptionOfType(NoSuchFieldException.class)
                .as("The getDeclaredField() method does not allows us to access inherited members," +
                        "even public ones.")
                .isThrownBy(() -> SubType.class.getDeclaredField(SUPER_TYPE_FIELD));

    }

    @Test
    void givenPublicField_whenGetField_thenReturnProperField() throws Exception {

        Field field = SubType.class.getField(PUBLIC_FIELD);

        assertThat(field.getName())
                .as("Returns a Field object that reflects the specified public member field of the class" +
                        "or interface represented by this Class object." +
                        "The name parameter is a String specifying the simple name of the desired field. ")
                .isEqualTo(PUBLIC_FIELD);

        assertThat(((String) field.get(INSTANCE)))
                .as("The value of a particular field is obtained by calling Field.get(Object) and passing" +
                        "an object of the class containing the field.")
                .isEqualTo("some value");

    }

    @Test
    void givenPrivateField_whenGetField_thenThrowNoSuchFieldEx() {

        assertThatExceptionOfType(NoSuchFieldException.class)
                .as("The getField() method does not allows us to access private members.")
                .isThrownBy(() -> SubType.class.getField(PRIVATE_FIELD));

    }

    @Test
    void givenPublicInheritedField_whenGetDeclaredField_thenReturnProperField() throws Exception {

        Field field = SubType.class.getField(PUBLIC_SUPER_TYPE_FIELD);

        assertThat(field.getName())
                .as("The getField() method allows us to access public inherited members.")
                .isEqualTo(PUBLIC_SUPER_TYPE_FIELD);

        assertThat((field.get(INSTANCE)))
                .as("The value of a particular field is obtained by calling Field.get(Object) and passing" +
                        "an object of the class containing the field.")
                .isEqualTo("The public super type variable");
    }

    @Test
    void givenNonPublicInheritedField_whenGetDeclaredField_thenReturnProperField() {

        assertThatExceptionOfType(NoSuchFieldException.class)
                .as("The getField() method does not allows us to access non public inherited members.")
                .isThrownBy(() -> SubType.class.getField(SUPER_TYPE_FIELD));

    }

    class SuperType {

        public String publicSuperTypeField = "The public super type variable";
        String superTypeField = "The super type variable";

    }

    class SubType extends SuperType {

        public String publicField = "some value";
        private int privateField = 10;

    }
}
