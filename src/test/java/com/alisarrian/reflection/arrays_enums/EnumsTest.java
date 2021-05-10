package com.alisarrian.reflection.arrays_enums;

import com.alisarrian.reflection.util.Level;
import com.alisarrian.reflection.util.SuperType;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "Arrays and enums")
class EnumsTest {

    @Test
    void givenEnum_whenGetEnumConstants_thenReturnArray() {

        List<Level> levels = Arrays.asList(Level.class.getEnumConstants());

        assertThat(levels)
                .as("The resulted list should contain all of the enum constants.")
                .contains(Level.HIGH, Level.LOW, Level.MEDIUM);

    }

    @Test
    void givenClassWithEnumField_whenGetDeclaredField_thenReturnProperField() throws Exception {

        SuperType INSTANCE = new SuperType();

        Field field = SuperType.class.getDeclaredField("lvl");
        Level level = (Level) field.get(INSTANCE);

        assertThat(level)
                .as("The field should have the old values.")
                .isEqualTo(Level.LOW);

        field.set(INSTANCE, Level.HIGH);

        assertThat((Level) field.get(INSTANCE))
                .as("The field should have the old values.")
                .isEqualTo(Level.HIGH);

    }
}
