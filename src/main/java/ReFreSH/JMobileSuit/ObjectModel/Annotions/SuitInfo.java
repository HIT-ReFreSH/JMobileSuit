package ReFreSH.JMobileSuit.ObjectModel.Annotions;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Stores the information of a member to be displayed.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface SuitInfo {

    /**
     * The information.
     *
     * @return The information.
     */
    String value();

    /**
     * Name of resource bundle
     *
     * @return Name of resource bundle
     */
    String resourceBundleName() default "";
}
