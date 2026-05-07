package by.sapra.orderHub.orders.infrastructure.data.metrics.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface BusinessMetrics {
    String name();
    String[] tags() default {};
}
