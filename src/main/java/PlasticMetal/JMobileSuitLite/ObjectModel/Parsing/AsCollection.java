package PlasticMetal.JMobileSuitLite.ObjectModel.Parsing;

import java.lang.annotation.*;

/**
 * A DynamicParameter member which should be seen as a collection
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AsCollection {
}
