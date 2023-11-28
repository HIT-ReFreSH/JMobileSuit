package ReFreSH.JMobileSuit.ObjectModel.Annotions;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface SuitDefaultArgument {
    String value();
}
