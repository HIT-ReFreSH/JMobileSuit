package PlasticMetal.JMobileSuitLite.ObjectModel.Interfaces;


import PlasticMetal.JMobileSuitLite.TraceBack;

import java.lang.reflect.InvocationTargetException;

/**
 * Represents an entity which can be executed.
 */
@SuppressWarnings("ALL")
public interface Executable
{
    /**
     * Execute this object.
     * @param args The arguments for execution.
     * @return TraceBack result of this object.
     */
    TraceBack Execute(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException;
}
