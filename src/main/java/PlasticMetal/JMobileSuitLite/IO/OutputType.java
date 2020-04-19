package PlasticMetal.JMobileSuitLite.IO;

/**
 * Type of content that writes to the output stream.
 */
public enum OutputType
{

    /**
     * Normal content.
     */
    Default,
    /**
     * Prompt content.
     */
    Prompt,
    /**
     * Error content.
     */
    Error,
    /**
     * All-Ok content.
     */
    AllOk,
    /**
     * Title of a list.
     */
    ListTitle,
    /**
     * Normal information.
     */
    CustomInfo,
    /**
     * Information provided by MobileSuit.
     */
    MobileSuitInfo

}
