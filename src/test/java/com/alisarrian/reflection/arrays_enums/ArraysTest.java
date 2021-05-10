package com.alisarrian.reflection.arrays_enums;

import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "Arrays and enums")
class ArraysTest {

    @Test
    void givenClassWithFields_whenGetDeclaredFields_thenReturnAllFields() {

        Field[] fields = SubType.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.getType().isArray()) {

                assertThat(f.getName())
                        .as("The name of the array type field should be 'strings'.")
                        .isEqualTo("strings");

                assertThat(f.getType())
                        .as("The array type field should contain an array od Strings.")
                        .isEqualTo(String[].class);

                assertThat(f.getType().getComponentType())
                        .as("Each component held in this array is of type String.")
                        .isEqualTo(String.class);
            }
        }
    }

    @Test
    void givenClassWithArrayField_whenArraySet_thenChange() throws Exception {

        Field field = SubType.class.getDeclaredField("strings");
        String[] strings = (String[]) field.get(new SubType());

        assertThat(Arrays.toString(strings))
                .as("The array should have the old values.")
                .contains("[you, have, no power, here]");

        Array.set(strings, 0, "Are you sure...? I");
        Array.set(strings, 2, "a lot of power");

        assertThat(Arrays.toString(strings))
                .as("The array should have the new values.")
                .contains("[Are you sure...? I, have, a lot of power, here]");

    }

    @Test
    void givenClassWithMultiDimensionalArrayField_whenArraySet_thenChange() throws Exception {

        Field field = SuperType.class.getDeclaredField("ints");
        int[][] ints = (int[][]) field.get(new SuperType());

        assertThat(ints)
                .as("The array should have the old values.")
                .isDeepEqualTo(new int[][]{{1, 2}, {3, 4}});

        Array.set(ints, 0 ,new int[]{11, 12});

        assertThat(ints)
                .as("The array should have the old values.")
                .isDeepEqualTo(new int[][]{{11, 12}, {3, 4}});

    }

    class SuperType {

        public String publicSuperTypeField = "The public super type variable";
        String superTypeField = "The super type variable";
        int[][] ints = new int[][]{{1, 2}, {3, 4}};

    }

    class SubType extends SuperType {

        public String publicField = "some value";
        String[] strings = new String[]{"you", "have", "no power", "here"};
        private int privateField = 10;

    }
}
