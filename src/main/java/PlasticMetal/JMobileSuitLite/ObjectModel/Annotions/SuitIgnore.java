package PlasticMetal.JMobileSuitLite.ObjectModel.Annotions;


import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents that this member should be ignored by Mobile Suit.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface SuitIgnore {

}
