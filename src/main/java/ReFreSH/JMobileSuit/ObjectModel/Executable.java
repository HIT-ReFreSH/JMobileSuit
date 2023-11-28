package ReFreSH.JMobileSuit.ObjectModel;


import ReFreSH.JMobileSuit.TraceBack;
import ReFreSH.Jarvis.ObjectModel.Tuple;

import java.lang.reflect.InvocationTargetException;

/**
 * Represents an entity which can be executed.
 */
public interface Executable {
    /**
     * execute this object.
     *
     * @param args The arguments for execution.
     * @return TraceBack result of this object.
     */
    Tuple<TraceBack, Object> execute(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException;
}
