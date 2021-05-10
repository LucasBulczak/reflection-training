package com.alisarrian.reflection.tasks;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Test(groups = "Task: Person")
class PersonT2Test {

    @Test
    void givenPersonClass_whenWantToTakeModifiers_thenReturnCorrectPersonModifiers() {

        String modifiers = null;

        Assertions.assertThat(modifiers)
                .as("")
                .contains("public")
                .doesNotContain("static", "final", "abstract");

    }
}
