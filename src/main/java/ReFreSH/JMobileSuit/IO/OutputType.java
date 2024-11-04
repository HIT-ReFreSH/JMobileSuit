package ReFreSH.JMobileSuit.IO;

/**
 * 写入输出流的内容类型。
 */
public enum OutputType {

    /**
     * 正常内容。
     */
    Default,
    /**
     * 提示内容。
     */
    Prompt,
    /**
     * 错误内容。
     */
    Error,
    /**
     * 全部正常内容。
     */
    AllOk,
    /**
     * 列表的标题。
     */
    ListTitle,
    /**
     * 正常信息
     */
    CustomInfo,
    /**
     * 信息由 MobileSuit 提供。
     */
    MobileSuitInfo

}
