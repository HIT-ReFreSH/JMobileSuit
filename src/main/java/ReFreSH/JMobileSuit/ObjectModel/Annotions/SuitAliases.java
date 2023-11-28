package ReFreSH.JMobileSuit.ObjectModel.Annotions;


import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Container for SuitAliases
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface SuitAliases {
    /**
     * Container.
     *
     * @return SuitAliases
     */
    SuitAlias[] value();
}
