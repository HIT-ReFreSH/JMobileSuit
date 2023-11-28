package ReFreSH.JMobileSuit.ObjectModel.Parsing;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Stores a parser which convert string argument to certain type.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface SuitParser {

    /**
     * The class which contains the parser method
     *
     * @return The class which contains the parser method
     */
    Class<?> ParserClass();

    /**
     * The parser method, which MUST BE public static
     *
     * @return The parser method, which MUST BE public static
     */
    String MethodName();

    /// <summary>
    ///     Initialize with a parser.
    /// </summary>
    /// <param name="parserClass">The class which contains the parser method</param>
    /// <param name="methodName">The parser method, which MUST BE public static</param>
   /* public SuitParserAttribute(Class<?> parserClass, String methodName)
    {
        if (parserClass == null || methodName == null) return;
        try
        {
            Method method= parserClass.getMethod(methodName);
            if ((method.getModifiers() & Modifier.STATIC)!=0){

            }
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }*/

    /// <summary>
    ///     The parser which convert string argument to certain type.
    /// </summary>
    //public Converter<string, object>? Converter { get; }
}
