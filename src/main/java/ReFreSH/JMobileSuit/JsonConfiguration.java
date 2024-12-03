package ReFreSH.JMobileSuit;

import ReFreSH.JMobileSuit.Demo.Client;
import ReFreSH.JMobileSuit.IO.ColorSetting;
import ReFreSH.JMobileSuit.IO.CommonPromptServer;
import ReFreSH.JMobileSuit.IO.ConsoleColor;
import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.NeuesProjekt.PowerLineThemedPromptServer;
import ReFreSH.JMobileSuit.ObjectModel.SuitConfigurator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.*;
import java.net.URL;

import static java.lang.System.exit;

/**
 * this class is for load configuration from the file which format is json
 */

public class JsonConfiguration implements IConfiguration {
    SuitConfiguration suitConfiguration;


    @Override
    public SuitConfiguration parse(String JsonFilePath) {

        Logger logger = LogManager.getLogger(JsonConfiguration.class);

        //接下来将会根据JSON文件中的配置来进行解析和对应的判断
        //讀取JSON字符串
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(JsonFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //獲取到JSON對象，格式出錯則直接退出
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = JSON.parseObject(jsonContent.toString());
        } catch (Exception e) {
            logger.error("JSON配置文件因格式問題解析錯誤，項目結束，請修改後重啟");
            exit(-1);
        }

        //開始配置文件

        //首先配置ColorSetting
        ColorSetting colorSetting = ReFreSH.JMobileSuit.IO.ColorSetting.getInstance();
        try {
            Class<ConsoleColor> enumClass = ConsoleColor.class;
            //獲取到ColorSetting下的所有顏色配置
            Object ODefaultColor =  JSONPath.read(jsonContent.toString(), "$.ColorSetting.DefaultColor");
            Object OAllOkColor =  JSONPath.read(jsonContent.toString(), "$.ColorSetting.AllOkColor");
            Object OPromptColor =  JSONPath.read(jsonContent.toString(), "$.ColorSetting.PromptColor");
            Object OErrorColor =  JSONPath.read(jsonContent.toString(), "$.ColorSetting.ErrorColor");
            Object OListTitleColor =  JSONPath.read(jsonContent.toString(), "$.ColorSetting.ListTitleColor");
            Object OCustomInformationColor =  JSONPath.read(jsonContent.toString(), "$.ColorSetting.CustomInformationColor");
            Object OInformationColor =  JSONPath.read(jsonContent.toString(), "$.ColorSetting.InfotmationColor");


            try {
                if (OAllOkColor != null) {
                    String enumName = (String) OAllOkColor;
                    // 获取枚举对象
                    ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                    colorSetting.AllOkColor = enumInstance;
                }
                if (ODefaultColor != null) {
                    String enumName = (String) ODefaultColor;
                    // 获取枚举对象
                    ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                    colorSetting.DefaultColor = enumInstance;
                }
                if (OErrorColor != null) {
                    String enumName = (String) OErrorColor;
                    // 获取枚举对象
                    ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                    colorSetting.ErrorColor = enumInstance;
                }
                if (OListTitleColor != null) {
                    String enumName = (String) OListTitleColor;
                    // 获取枚举对象
                    ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                    colorSetting.ListTitleColor = enumInstance;
                }
                if (OPromptColor != null) {
                    String enumName = (String) OPromptColor;
                    // 获取枚举对象
                    ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                    colorSetting.PromptColor = enumInstance;
                }
                if (OCustomInformationColor != null) {
                    String enumName = (String) OCustomInformationColor;
                    // 获取枚举对象
                    ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                    colorSetting.CustomInformationColor = enumInstance;
                }
                if (OInformationColor != null) {
                    String enumName = (String) OInformationColor;
                    // 获取枚举对象
                    ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                    colorSetting.InformationColor = enumInstance;
                }
            } catch (NullPointerException e) {
                logger.error("Json顏色配置中存在無效顏色名稱");

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        //將ColorSetting的配置放入到Configurator之中
        SuitConfigurator suitConfigurator = SuitConfigurator.ofDefault();
        suitConfigurator.ColorSetting = colorSetting;


        //現在開始進行logger的配置，但是需要目前logger疑似存在bug，即使是resource下的logger配置文件也無法讀取

        ConfigurationSource source;
        //第一種：绝对路径配置文件
        Object OAbsolutePath =  JSONPath.read(jsonContent.toString(), "$.Logger.AbsolutePath");
        if(OAbsolutePath != null) {
            try {
                //使用 public ConfigurationSource(InputStream stream, URL url) 构造函数
                String path = (String) OAbsolutePath;
                source = new ConfigurationSource(new FileInputStream(path), new File(path).toURL());
                Configurator.initialize(null, source);
            } catch (Exception e) {
                logger.error("logger絕對路徑配置文件地址解析錯誤");
                e.printStackTrace();
            }
        }
        //第二種 相對路徑
        Object ORelativePath =  JSONPath.read(jsonContent.toString(), "$.Logger.RelativePath");
        if(ORelativePath != null) {
            try {
                String path = (String) ORelativePath;
                //使用System.getProperty
                String config=System.getProperty("user.dir");
                source = new ConfigurationSource(new FileInputStream(config+path));
                Configurator.initialize(null, source);
            } catch (Exception e) {
                logger.error("logger相对路徑配置文件地址解析錯誤");
                e.printStackTrace();
            }
        }

        suitConfigurator.use(logger);


        //配置CommonPromptServer或者PowerLineThemedPromptServer
        IOServer ioServer = new IOServer();
        ioServer.ColorSetting = colorSetting;
        Object OPromptServer =  JSONPath.read(jsonContent.toString(), "$.PromptServer");
        CommonPromptServer commonPromptServer = new CommonPromptServer(suitConfigurator.getConfiguration());
        PowerLineThemedPromptServer powerLineThemedPromptServer = new PowerLineThemedPromptServer(suitConfigurator.getConfiguration());
        if(OPromptServer != null){
            String promptServer = (String) OPromptServer;
            if(promptServer.equals("Common")){
                ioServer.Prompt = commonPromptServer;
                suitConfiguration = new CommonSuitConfiguration(BuildInCommandServer.class, ioServer, commonPromptServer, colorSetting, logger);

            }else if(promptServer.equals("PowerLineThemed")){
                ioServer.Prompt = powerLineThemedPromptServer;

                suitConfiguration = new CommonSuitConfiguration(BuildInCommandServer.class, ioServer, powerLineThemedPromptServer, colorSetting, logger);
            }else{
                logger.error("暂不提供其他类型的PromptServer");
                ioServer.Prompt = commonPromptServer;
                suitConfiguration = new CommonSuitConfiguration(BuildInCommandServer.class, new IOServer(),new CommonPromptServer(), colorSetting, logger);
            }
        }else{

            suitConfiguration = new CommonSuitConfiguration(BuildInCommandServer.class, new IOServer(),new CommonPromptServer(), colorSetting, logger);
        }

        //最后修改一下IOServer的輸入輸出流
        //輸入流配置，不配置默認為系統輸入流
        Object Oinput =  JSONPath.read(jsonContent.toString(), "$.IOServer.input");
        if(Oinput != null) {
            try{
                String input = (String) Oinput;
                if(Oinput.equals("System.in")){
                    ioServer.ResetInput();
                }else{
                    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
                    ioServer.SetInput(inputStream);
                }
            }catch(Exception e){
                logger.error("解析IOServer的輸入流出錯");
                e.printStackTrace();
            }
        }
        //配置輸出流
        Object Ooutput =  JSONPath.read(jsonContent.toString(), "$.IOServer.output");
        if(Ooutput != null) {
            try{
                String output = (String) Ooutput;
                if(output.equals("System.out")){
                    ioServer.ResetOutput();
                }else{
                    PrintStream outputStream = new PrintStream(output);
                    ioServer.Output = outputStream;
                }
            }catch(Exception e){
                logger.error("解析IOServer的輸出流出錯");
                e.printStackTrace();
            }
        }

        //配置错误流
        Object Oerr =  JSONPath.read(jsonContent.toString(), "$.IOServer.err");
        if(Oerr != null) {
            try{
                String err = (String) Oerr;
                if(err.equals("System.err")){
                    ioServer.ResetError();
                }else{
                    PrintStream outputStream = new PrintStream(err);
                    ioServer.Error = outputStream;
                }
            }catch(Exception e){
                logger.error("解析IOServer的错误流出錯");
                e.printStackTrace();
            }
        }

        return suitConfiguration;
    }

    public static void main(String[] args) throws Exception {
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration configuration = jsonConfiguration.parse("src/main/resources/example.json");
        if(configuration.ColorSetting() == null){
            System.out.println("yes");
        }
        new SuitHost(Client.class,
                configuration).Run();
    }
}
