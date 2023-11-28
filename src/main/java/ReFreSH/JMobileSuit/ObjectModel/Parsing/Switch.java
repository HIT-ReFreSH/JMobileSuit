package ReFreSH.JMobileSuit.ObjectModel.Parsing;

import java.lang.annotation.*;

/**
 * A switch used in a dynamic parameter
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Switch {
    /**
     * The name of option, for '-a', it's 'a'.
     *
     * @return The name of option, for '-a', it's 'a'.
     */
    String value();
}
