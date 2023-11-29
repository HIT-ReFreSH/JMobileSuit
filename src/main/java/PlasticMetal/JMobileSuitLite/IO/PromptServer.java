package PlasticMetal.JMobileSuitLite.IO;

import PlasticMetal.JMobileSuitLite.ObjectModel.IOInteractive;
import PlasticMetal.JMobileSuitLite.TraceBack;

/**
 * represents a server provides prompt output.
 */
public interface PromptServer extends IOInteractive
{


    /**
     * get the default prompt server of Mobile Suit
     */
    static PromptServer getInstance(){ return new CommonPromptServer();}


    /**
     * update the prompt of this prompt server
     *
     * @param returnValue return value of last command
     * @param information information of current instance
     * @param traceBack   traceBack of last command
     */
    void Update(String returnValue, String information, TraceBack traceBack);


    /**
     * update the prompt of this prompt server
     *
     * @param returnValue       return value of last command
     * @param information       information of current instance
     * @param traceBack         traceBack of last command
     * @param promptInformation information shows when traceBack==TraceBack.Prompt
     */
    void Update(String returnValue, String information, TraceBack traceBack, String promptInformation);

    /**
     *  Output a prompt to IO.Output
     */
    void Print();
}
