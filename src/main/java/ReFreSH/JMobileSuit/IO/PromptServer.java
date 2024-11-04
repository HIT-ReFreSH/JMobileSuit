package ReFreSH.JMobileSuit.IO;

import ReFreSH.JMobileSuit.ObjectModel.IOInteractive;
import ReFreSH.JMobileSuit.TraceBack;

/**
 * 表示服务器提供提示输出。
 */
public interface PromptServer extends IOInteractive {


    /**
     * 获取 Mobile Suit 的默认提示服务器
     */
    static PromptServer getInstance() {
        return new CommonPromptServer();
    }


    /**
     * 更新此提示服务器的提示符
     *
     * @param returnValue 返回 last 命令的值
     * @param information 当前实例信息
     * @param traceBack   traceLast 命令的返回
     */
    void Update(String returnValue, String information, TraceBack traceBack);


    /**
     * 更新此提示服务器的提示符
     *
     * @param returnValue      返回 last 命令的值
     * @param information       当前实例信息
     * @param traceBack        traceLast 命令的返回
     * @param promptInformation 信息显示 traceBack==TraceBack.Prompt
     */
    void Update(String returnValue, String information, TraceBack traceBack, String promptInformation);

    /**
     *将提示输出到 IO。输出
     */
    void Print();
}
