package com.alisarrian.reflection.tasks;

import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Test(groups = "Task: Person")
class PersonT4Test {

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
    void givenPersonClass_whenWantToPrintInfoAboutSpecificPerson_ThenPrintCorrect() throws Exception {

        Assertions.assertThat(outputStream.toString())
                .as("printInfoAbout call should print proper sentence.")
                .contains(TestPerson.ABOUT_ME);

    }
}
