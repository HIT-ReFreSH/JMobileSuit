package ReFreSH.JMobileSuit;


/**
 * 最后命令行的状态。内置命令和主机功能的返回值类型。
 */
public enum TraceBack {
    /**
     * 提示符
     */
    Prompt(2),
    /**
     * 进程正在退出
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
     * 一切正常
     */
    InvalidCommand(-1) {
        @Override
        public String toString() {
            return "InvalidCommand";
        }
    },

    /**
     * 这不是一个命令
     */
    ObjectNotFound(-2) {
        @Override
        public String toString() {
            return "ObjectNotFound";
        }
    },

    /**
     * 找不到引用的对象。
     */
    MemberNotFound(-3) {
        @Override
        public String toString() {
            return "MemberNotFound";
        }
    },

    /**
     * 找不到对象中引用的成员。
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
