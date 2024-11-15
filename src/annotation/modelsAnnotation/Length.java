package mg.ituprom16.annotation.modelsAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
    int min() default 0;
    int max() default 1000000;
}