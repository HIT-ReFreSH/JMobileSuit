package PlasticMetal.JMobileSuitLite;


/**
 * Status of the last Commandline. Return value type for Built-In-Commands and Host Functions.
 */
public enum TraceBack
{
    /**
     * The Progress is Exiting
     */
    OnExit{
        @Override
        public String toString()
        {
            return "OnExit";
        }
    },

    /**
     * Everything is OK
     */
    AllOk{
        @Override
        public String toString()
        {
            return "AllOK";
        }
    },

    /**
     * This is not a command
     */
    InvalidCommand{
        @Override
        public String toString()
        {
            return "InvalidCommand";
        }
    },

    /**
     * Cannot find the object referring to.
     */
    ObjectNotFound{
        @Override
        public String toString()
        {
            return "ObjectNotFound";
        }
    },

    /**
     * Cannot find the member in the object referring to.
     */
    MemberNotFound{
        @Override
        public String toString()
        {
            return "MemberNotFound";
        }
    }
}
