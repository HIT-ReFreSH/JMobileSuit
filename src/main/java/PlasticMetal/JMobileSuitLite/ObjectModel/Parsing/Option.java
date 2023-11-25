package PlasticMetal.JMobileSuitLite.ObjectModel.Parsing;

import java.lang.annotation.*;

/**
 * A option used in a dynamic parameter
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Option {
    /**
     * The name of option, for '-a', it's 'a'.
     *
     * @return The name of option, for '-a', it's 'a'.
     */
    String value();

    /**
     * The length of String[] used to parse this arg
     *
     * @return The length of String[] used to parse this arg
     */
    int length() default 1;
}
