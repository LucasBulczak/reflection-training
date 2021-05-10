package com.alisarrian.reflection.classess;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.alisarrian.reflection.classess.BasicMethodsTest.SomeEnum.FIRST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Test(groups = "Classes")
class BasicMethodsTest {

    @Test
    void givenReferenceType_whenGetClass_thenClassObjectIsReturned() {
        assertThat("foo".getClass())
                .as("Calling getClass() on an instance of String class should return a Class object.")
                .isNotNull();

        Assertions.assertThat(FIRST.getClass())
                .as("Calling getClass() on an instance of Enum should return a Class object.")
                .isNotNull();

        byte[] bytes = new byte[1024];
        assertThat(bytes.getClass())
                .as("Calling getClass() on an array should return a Class object.")
                .isNotNull();

        Set<String> s = new HashSet<>();
        assertThat(s.getClass()).as("")
                .as("Calling getClass() on a hashset should return a Class object corresponded to java.util.HashSet.")
                .isNotNull();
    }

    @Test
    void givenPrimitiveType_whenDotClass_thenClassObjectIsReturned() {
//        boolean b = false;
//        Class c = b.getClass();   // compile-time error

        assertThat(boolean.class)
                .as("Calling .class on a primitive should return a Class object.")
                .isNotNull();

        assertThat(byte.class).isNotNull();
        assertThat(short.class).isNotNull();
        assertThat(int.class).isNotNull();
        assertThat(long.class).isNotNull();
        assertThat(float.class).isNotNull();
        assertThat(double.class).isNotNull();
    }

    @Test
    void givenFQNOfReferenceType_whenClassForName_thenClassObjectIsReturned() throws ClassNotFoundException {
        assertThat(Class.forName("java.lang.Short"))
                .as("Calling Class.forName() on a reference type should return a Class object.")
                .isNotNull();
        assertThat(Class.forName("[D"))
                .as("[D is the same as double[].class")
                .isNotNull();
        assertThat(Class.forName("java.util.EnumMap")).isNotNull();

        assertThatExceptionOfType(ClassNotFoundException.class)
                .as("ClassNotFoundException should be thrown because class java.lang.IDoNotExist do not exist.")
                .isThrownBy(() -> Class.forName("java.lang.IDoNotExist"));
    }

    @Test
    void givenWrapperClass_whenType_thenClassObjectIsReturned() {
        assertThat(Boolean.TYPE)
                .as("Calling .TYPE on a wrapper class should return a Class object for specific primitive.")
                .isEqualTo(boolean.class);

        assertThat(Byte.TYPE).isEqualTo(byte.class);
        assertThat(Short.TYPE).isEqualTo(short.class);
        assertThat(Integer.TYPE).isEqualTo(int.class);
        assertThat(Long.TYPE).isEqualTo(long.class);
        assertThat(Float.TYPE).isEqualTo(float.class);
        assertThat(Double.TYPE).isEqualTo(double.class);

        assertThat(Void.TYPE).isEqualTo(void.class);
    }

    @Test
    void givenSubtype_whenGetSuperclass_thenClassObjectForSupertypeIsReturned() {
        assertThat(Integer.class.getSuperclass())
                .as("Calling .getSuperclass() on a subtype should return a Class object for it supertype.")
                .isEqualTo(Number.class);


        assertThat(Object.class.getSuperclass())
                .as("Calling .getSuperclass() on an Object should return null.")
                .isNull();
    }

    @Test
    void givenClass_whenGetClasses_thenReturnAllMemberClasses() {
        assertThat(Arrays.toString(Character.class.getClasses()))
                .as("Calling .getClasses() on a Class object should return all the public classes, interfaces," +
                        "and enums that are members of the class including inherited members.")
                .contains("UnicodeBlock",
                        "UnicodeScript",
                        "Subset");
        assertThat(Arrays.toString(ExampleClasses.class.getClasses()))
                .contains("NewCharacter",
                        "NewInterface",
                        "NewEnum",
                        "IAmInherited")
                .doesNotContain("IAmNotPublicSoIAmNotVisible",
                        "IAmPrivate");
    }

    @Test
    void givenClass_whenGetDeclaredClasses_thenReturnAllDeclaredClasses() {
        assertThat(Arrays.toString(ExampleClasses.class.getDeclaredClasses()))
                .as("Calling .getDeclaredClasses() on a Class object should return all of the classes," +
                        "interfaces, and enums that are explicitly declared in this class.")
                .contains("NewCharacter",
                        "NewInterface",
                        "NewEnum",
                        "IAmPrivate")
                .doesNotContain("IAmNotPublicSoIAmNotVisible",
                        "IAmInherited");
    }

    @Test
    void givenClass_whenGetDeclaringClass_thenReturnClassInWhichTheseClassWasDeclared() throws NoSuchFieldException, NoSuchMethodException {
        assertThat(ExampleClasses.IAmPrivate.class.getDeclaringClass().toString())
                .as("Calling .getDeclaringClass() on a Class object should return the Class in which these members were declared.")
                .contains("ExampleClasses");


        Field field = ExampleClasses.IAmNotPublic.class.getDeclaredField("someIntField");
        assertThat(field.getDeclaringClass().toString())
                .contains("ExampleClasses");

        Method method = ExampleClasses.IAmNotPublic.class.getDeclaredMethod("someMethod");
        assertThat(method.getDeclaringClass().toString())
                .contains("ExampleClasses");
    }

    @Test
    void givenClass_whenGetEnclosingClass_thenReturnEnclosingClassOfClass() {

        assertThat(Thread.State.class.getEnclosingClass().toString())
                .as("Calling .getEnclosingClass() on a Class object should the immediately enclosing class of the class.")
                .contains("Thread");

    }

    enum SomeEnum {FIRST, SECOND}

    static class ExampleClassesSuper {
        public class IAmInherited {
        }
    }

    static class ExampleClasses extends ExampleClassesSuper {
        public enum NewEnum {}

        public interface NewInterface {
        }

        public static class NewCharacter {
        }

        class IAmNotPublic {
            int someIntField;

            IAmNotPublic() {
            }

            void someMethod() {
            }
        }

        private class IAmPrivate {

        }
    }
}

