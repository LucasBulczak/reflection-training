package com.alisarrian.reflection.members;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class Methods for Locating Constructors
 * ______________________________________________________________________
 * | Class API					| List of members?	| Private members?	|
 * ______________________________________________________________________
 * | getDeclaredConstructor()	| no				| yes               |
 * | getConstructor()			| no				| no                |
 * | getDeclaredConstructors()	| yes				| yes               |
 * | getConstructors()			| yes				| no                |
 * ______________________________________________________________________
 * <p>
 * Note: Constructors are not inherited.
 */
@Test(groups = "Members")
class ConstructorsTest {

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


    @Test
    void givenClassWithPrivateNoArgsConstructor_whenGetDeclaredConstructor_thenReturnProperConstructor() throws Exception {

        Constructor<SubType> ctor = SubType.class.getDeclaredConstructor();
        ctor.newInstance();

        assertThat(outputStream.toString())
                .as("Returns a Constructor object that reflects the specified constructor of the class or interface represented by this Class object. ")
                .contains("private SubType() without params");

        assertThat(ctor.getModifiers())
                .isEqualTo(Modifier.PRIVATE);

    }

    @Test
    void givenClassWithPackagePrivateConstructor_whenGetDeclaredConstructor_thenReturnProperConstructor() throws Exception {

        Constructor<SubType> ctor = SubType.class.getDeclaredConstructor(int.class);
        ctor.newInstance(10);

        assertThat(outputStream.toString())
                .as("Returns a Constructor object that reflects the specified constructor of the class or interface represented by this Class object. ")
                .contains("package-private SubType() with param int : 10");

        assertThat(ctor.getModifiers())
                .as("There is no special Modifier for package-private.")
                .isZero();

    }

    @Test
    void givenClassWithPublicConstructor_whenGetDeclaredConstructor_thenReturnProperConstructor() throws Exception {

        Constructor<SubType> ctor = SubType.class.getDeclaredConstructor(String.class);
        ctor.newInstance("yolo");

        assertThat(outputStream.toString())
                .as("Returns a Constructor object that reflects the specified constructor of the class or interface represented by this Class object. ")
                .contains("public SubType() with param String : yolo");

        assertThat(ctor.getModifiers())
                .isEqualTo(Modifier.PUBLIC);

    }

    @Test
    void givenClassWithConstructorThatTakesTwoArguments_whenGetDeclaredConstructor_thenReturnProperConstructor() throws Exception {

        Constructor<SubType> ctor = SubType.class.getDeclaredConstructor(String.class, int.class);
        ctor.newInstance("yolo!", 256);

        assertThat(outputStream.toString())
                .as("Returns a Constructor object that reflects the specified constructor of the class or interface represented by this Class object. ")
                .contains("public SubType() with params String : yolo! and int : 256");

    }

    @Test
    void givenClassWithConstructorThatTakesVarargs_whenGetDeclaredConstructor_thenReturnProperConstructor() throws Exception {

        Constructor<SubType> ctor = SubType.class.getDeclaredConstructor(String[].class);
        ctor.newInstance((Object) new String[]{"yolo!", " and", " one", " more time! xD"});

        assertThat(outputStream.toString())
                .as("Returns a Constructor object that reflects the specified constructor of the class or interface represented by this Class object. ")
                .contains("SubType() with varargs String : [yolo!,  and,  one,  more time! xD]");

    }

    static class SubType {
        private SubType() {
            System.out.println("private SubType() without params");
        }

        SubType(int i) {
            System.out.println("package-private SubType() with param int : " + i);
        }

        public SubType(String s) {
            System.out.println("public SubType() with param String : " + s);
        }

        public SubType(String s, int times) {
            System.out.println("public SubType() with params String : " + s + " and int : " + times);
        }

        SubType(String... args) {
            System.out.println("SubType() with varargs String : " + Arrays.toString(args));
        }
    }
}
