package ReFreSH.JMobileSuit;


/**
 * Status of the last Commandline. Return value type for Built-In-Commands and Host Functions.
 */
public enum TraceBack {
    /**
     * a prompt
     */
    Prompt(2),
    /**
     * The Progress is Exiting
     */
    OnExit(1) {
        @Override
        public String toString() {
            return "OnExit";
        }
    },

    /**
     * Everything is OK
     */
    AllOk(0) {
        @Override
        public String toString() {
            return "AllOK";
        }
    },

    /**
     * This is not a command
     */
    InvalidCommand(-1) {
        @Override
        public String toString() {
            return "InvalidCommand";
        }
    },

    /**
     * Cannot find the object referring to.
     */
    ObjectNotFound(-2) {
        @Override
        public String toString() {
            return "ObjectNotFound";
        }
    },

    /**
     * Cannot find the member in the object referring to.
     */
    MemberNotFound(-3) {
        @Override
        public String toString() {
            return "MemberNotFound";
        }
    },

    /**
     * Cannot find the member in the object referring to.
     */
    AppException(-4) {
        @Override
        public String toString() {
            return "ApplicationException";
        }
    };

    public final int Value;

    TraceBack(int code) {
        Value = code;
    }

}
