package ReFreSH.JMobileSuit.ObjectModel.Members;

/**
 * Represents type of the last parameter of a method
 */
public enum TailParameterType {
    /**
     * Last parameter exists, and is quite normal.
     */
    Normal,

    /**
     * Last parameter is an array.
     */
    Array,
    /**
     * Last parameter implements DynamicParameter.
     */
    DynamicParameter,
    /**
     * Last parameter does not exist
     */
    NoParameter
}
