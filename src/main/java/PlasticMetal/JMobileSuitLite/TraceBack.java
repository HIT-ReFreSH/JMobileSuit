package PlasticMetal.JMobileSuitLite;


/**
 * Status of the last Commandline. Return value type for Built-In-Commands and Host Functions.
 */
public enum TraceBack
{
    /**
     * The Progress is Exiting
     */
    OnExit,

    /**
     * Everything is OK
     */
    AllOk,

    /**
     * This is not a command
     */
    InvalidCommand,

    /**
     * Cannot find the object referring to.
     */
    ObjectNotFound,

    /**
     * Cannot find the member in the object referring to.
     */
    MemberNotFound
}
