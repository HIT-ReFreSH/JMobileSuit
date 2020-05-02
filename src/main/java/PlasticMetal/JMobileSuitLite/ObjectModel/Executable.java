package PlasticMetal.JMobileSuitLite.ObjectModel;


import PlasticMetal.JMobileSuitLite.ObjectModel.Tuple;
import PlasticMetal.JMobileSuitLite.TraceBack;

import java.lang.reflect.InvocationTargetException;

/**
 * Represents an entity which can be executed.
 */
public interface Executable
{
    /**
     * Execute this object.
     * @param args The arguments for execution.
     * @return TraceBack result of this object.
     */
    Tuple<TraceBack,Object>  Execute(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException;
}
