package com.alisarrian.reflection.members;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Not all annotations are available via reflection.
 * Only those which have a java.lang.annotation.RetentionPolicy of RUNTIME are accessible.
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/reflect/class/classModifiers.html">https://docs.oracle.com/javase/tutorial/reflect/class/classModifiers.html</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@interface CustomAnnotationRuntime {
}
