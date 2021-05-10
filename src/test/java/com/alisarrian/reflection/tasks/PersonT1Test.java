package com.alisarrian.reflection.tasks;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Test(groups = "Task: Person")
class PersonT1Test {

    @Test
    void givenPersonClass_whenWantToTakeFields_thenReturnNamesForAllFields() {

        List<String> fieldNames = null;

        Assertions.assertThat(fieldNames)
                .as("This class has three fields - collection with all their names should be returned.")
                .containsExactlyInAnyOrderElementsOf(Arrays.asList("name", "address", "age"));
    }
}
