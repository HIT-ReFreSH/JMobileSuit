package PlasticMetal.JMobileSuitLite.ObjectModel;

/**
 * represents a Tuple. mutable.
 * @param <T1> type of the first item.
 * @param <T2> type of the second item.
 */
@SuppressWarnings("ALL")
public class Tuple<T1,T2>
{
    /**
     * First Item.
     */
    public T1 First;
    /**
     * Second Item.
     */
    public T2 Second;


    /**
     * Initialize a Traid with its items.
     * @param first First Item.
     * @param second Second Item.
     */
    public Tuple(T1 first, T2 second){
        First=first;
        Second=second;
    }
}
