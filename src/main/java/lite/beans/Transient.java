package lite.beans;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD})
@Retention(RUNTIME)
public @interface Transient {

    /**
     * Returns whether or not the {@code Introspector} should construct artifacts for the annotated method.
     *
     * @return whether or not the {@code Introspector} should construct artifacts for the annotated method
     */
    boolean value() default true;
}

