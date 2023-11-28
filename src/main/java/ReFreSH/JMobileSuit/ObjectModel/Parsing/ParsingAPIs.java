package ReFreSH.JMobileSuit.ObjectModel.Parsing;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ParsingAPIs {
    public static Method getParser(SuitParser parser) {
        if (parser == null) return null;
        try {
            Method m = parser.ParserClass().getMethod(parser.MethodName(), String.class);
            if ((m.getModifiers() & Modifier.STATIC) == 0) return null;
            return m;
        } catch (Exception e) {
            return null;
        }
    }
}
