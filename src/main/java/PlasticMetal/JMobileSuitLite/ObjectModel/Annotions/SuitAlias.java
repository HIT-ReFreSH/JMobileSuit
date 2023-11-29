package PlasticMetal.JMobileSuitLite.ObjectModel.Annotions;


import java.lang.annotation.*;

/**
 * Alias for a SuitObject's member
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SuitAliases.class)
public @interface SuitAlias
{
    /**
     * @return The alias.
     */
    String value();

}

