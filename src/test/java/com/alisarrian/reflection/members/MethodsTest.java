package com.alisarrian.reflection.members;

// TODO: 5/8/2021 dodac JavaDoc

import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Class Methods for Locating Methods
 * __________________________________________________________________________________________
 * | Class API              | List of members?  | Inherited members?    | Private members?  |
 * __________________________________________________________________________________________
 * | getDeclaredMethod()    | no                | no                    | yes               |
 * | getMethod()            | no                | yes                   | no                |
 * | getDeclaredMethods()   | yes               | no                    | yes               |
 * | getMethods()           | yes               | yes                   | no                |
 * __________________________________________________________________________________________
 */
@Test(groups = "Members")
class MethodsTest {

    private static final String PUBLIC_METHOD = "publicMethod";
    private static final String PARAMETERLESS_PUBLIC_METHOD = "parameterlessPublicMethod";
    private static final String PRIVATE_METHOD = "privateMethod";
    private static final String PUBLIC_SUPER_METHOD = "publicSuperMethod";

    private final SubType INSTANCE = new SubType();

    private final PrintStream original = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(outputStream);

    @BeforeMethod
    public void setUp() {
        System.setOut(printStream);
    }

    @AfterMethod
    public void tearDown() {
        System.setOut(original);
    }

    // getDeclaredMethod()

    @Test
    void givenPublicMethodThatTakeParameterOfTypeInt_whenGetDeclaredMethod_thenReturnProperMethod() throws Exception {

        Method method = SubType.class.getDeclaredMethod(PUBLIC_METHOD, int.class);

        Assertions.assertThat(method.getName())
                .as("Returns a Method object that reflects the specified declared method of the class" +
                        "or interface represented by this Class object. ")
                .isEqualTo(PUBLIC_METHOD);

    }

    @Test
    void givenPublicMethod_whenGetDeclaredMethod_thenReturnProperMethod() throws Exception {

        Method method = SubType.class.getDeclaredMethod(PARAMETERLESS_PUBLIC_METHOD, null);

        Assertions.assertThat(method.getName())
                .as("Method that does not take any parameters is treated differently - we need to specify" +
                        "parameters as null or do not declare them at all.")
                .isEqualTo(PARAMETERLESS_PUBLIC_METHOD);

    }

    @Test
    void givenPublicMethod_whenGetDeclaredMethod_thenReturnProperMethod2() throws Exception {

        Method method = SubType.class.getDeclaredMethod(PARAMETERLESS_PUBLIC_METHOD);

        Assertions.assertThat(method.getName())
                .as("Method that does not take any parameters is treated differently - we need to specify" +
                        "parameters as null or do not declare them at all.")
                .isEqualTo(PARAMETERLESS_PUBLIC_METHOD);

    }

    @Test
    void givenPrivateMethod_whenGetDeclaredMethod_thenReturnProperMethod() throws Exception {

        Method method = SubType.class.getDeclaredMethod(PRIVATE_METHOD);

        Assertions.assertThat(method.getName())
                .as("Method that does not take any parameters is treated differently - we need to specify" +
                        "parameters as null or do not declare them at all.")
                .isEqualTo(PRIVATE_METHOD);

    }

    @Test
    void givenPublicInheritedMethod_whenGetDeclaredMethod_thenThrowNoSuchMethodEx() {

        assertThatExceptionOfType(NoSuchMethodException.class)
                .as("The getDeclaredMethod() method does not allows us to access inherited members," +
                        "even public ones.")
                .isThrownBy(() -> SubType.class.getDeclaredMethod(PUBLIC_SUPER_METHOD));

    }

    @Test
    void givenPublicMethodThatTakeParameterOfTypeInt_whenInvoke_thenGetProperResult() throws Exception {

        Method method = SubType.class.getDeclaredMethod(PUBLIC_METHOD, int.class);
        method.invoke(INSTANCE, 10);

        Assertions.assertThat(outputStream.toString())
                .as("Method obtained via getDeclaredMethod() was invoked with parameter int : '10'.")
                .contains("publicMethod() with param int : 10");

    }

    @Test
    void givenPublicMethod_whenInvoke_thenGetProperResult() throws Exception {

        Method method = SubType.class.getDeclaredMethod(PARAMETERLESS_PUBLIC_METHOD);
        method.invoke(INSTANCE);

        Assertions.assertThat(outputStream.toString())
                .as("Method obtained via getDeclaredMethod() was invoked without parameters.")
                .contains("parameterlessPublicMethod()");

    }

    // getMethod()

    @Test
    void givenPublicMethodThatTakeParameterOfTypeInt_whenGetMethod_thenReturnProperMethod() throws Exception {

        Method method = SubType.class.getMethod(PUBLIC_METHOD, int.class);

        Assertions.assertThat(method.getName())
                .as("Returns a Method object that reflects the specified public member method of the class" +
                        " or interface represented by this Class object." +
                        "The name parameter is a String specifying the simple name of the desired method")
                .isEqualTo(PUBLIC_METHOD);

    }

    @Test
    void givenPublicMethod_whenGetMethod_thenReturnProperMethod() throws Exception {

        Method method = SubType.class.getMethod(PARAMETERLESS_PUBLIC_METHOD, null);

        Assertions.assertThat(method.getName())
                .as("Method that does not take any parameters is treated differently - we need to specify" +
                        "parameters as null or do not declare them at all.")
                .isEqualTo(PARAMETERLESS_PUBLIC_METHOD);

    }

    @Test
    void givenPublicMethod_whenGetMethod_thenReturnProperMethod2() throws Exception {

        Method method = SubType.class.getMethod(PARAMETERLESS_PUBLIC_METHOD);

        Assertions.assertThat(method.getName())
                .as("Method that does not take any parameters is treated differently - we need to specify" +
                        "parameters as null or do not declare them at all.")
                .isEqualTo(PARAMETERLESS_PUBLIC_METHOD);

    }


    @Test
    void givenPrivateMethod_whenGetMethod_thenThrowNoSuchMethodEx() {

        assertThatExceptionOfType(NoSuchMethodException.class)
                .as("The getMethod() method does not allows us to access private members.")
                .isThrownBy(() -> SubType.class.getMethod(PRIVATE_METHOD));

    }

    @Test
    void givenPublicInheritedMethod_whenGetMethod_thenReturnProperMethod() throws Exception {

        Method method = SubType.class.getMethod(PUBLIC_SUPER_METHOD);

        Assertions.assertThat(method.getName())
                .as("The getMethod() method allows us to access inherited members," +
                        "even public ones.")
                .isEqualTo(PUBLIC_SUPER_METHOD);

    }


    @Test
    void givenPublicMethodThatTakeParameterOfTypeInt_whenInvoke_thenGetProperResult2() throws Exception {

        Method method = SubType.class.getMethod(PUBLIC_METHOD, int.class);
        method.invoke(INSTANCE, 10);

        Assertions.assertThat(outputStream.toString())
                .as("Method obtained via getMethod() was invoked with parameter int : '10'.")
                .contains("publicMethod() with param int : 10");

    }

    @Test
    void givenPublicMethod_whenInvoke_thenGetProperResult2() throws Exception {

        Method method = SubType.class.getMethod(PARAMETERLESS_PUBLIC_METHOD);
        method.invoke(INSTANCE);

        Assertions.assertThat(outputStream.toString())
                .as("Method obtained via getMethod() was invoked without parameters.")
                .contains("parameterlessPublicMethod()");

    }

    class SuperType {

        public void publicSuperMethod() {
        }

    }

    class SubType extends SuperType {

        private void privateMethod() {
        }

        public void publicMethod(int i) {
            System.out.println("publicMethod() with param int : " + i);
        }

        public void parameterlessPublicMethod() {
            System.out.println("parameterlessPublicMethod() ");
        }
    }
}
