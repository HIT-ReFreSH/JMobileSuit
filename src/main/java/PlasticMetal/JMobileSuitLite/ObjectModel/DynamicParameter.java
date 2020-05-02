package PlasticMetal.JMobileSuitLite.ObjectModel;

/**
 * Represents a Parameter which can be parsed from a String[].
 */
public interface DynamicParameter
{
    /**
     * Parse this Parameter from String[].
     * @param options String[] to parse from.
     * @return Whether the parsing is successful
     */
    boolean Parse(String[] options);
}
