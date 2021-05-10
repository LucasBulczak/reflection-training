package com.alisarrian.reflection.tasks;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.List;

@Test(groups = "Task: Person")
class PersonT3Test {

    @Test
    void givenPersonClass_whenWantToTakePublicMethods_thenReturnCorrectNames() {

        List<String> publicMethods = null;

        List<String> expected = List.of("wait", "wait", "wait", "equals", "toString", "hashCode", "getClass", "notify", "notifyAll");

        Assertions.assertThat(publicMethods)
                .as("")
                .containsExactlyInAnyOrderElementsOf(expected);

    }
}
