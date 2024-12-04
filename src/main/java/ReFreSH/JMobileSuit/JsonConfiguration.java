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
import static java.lang.System.in;

/**
 * this class is for load configuration from the file which format is json
 */

public class JsonConfiguration implements IConfiguration {
    SuitConfiguration suitConfiguration;

    /**
     * This method is used to read the JSON file in the corresponding directory
     * and generate the corresponding SuitConfiguration.
     * @param JsonFilePath the path of the Json File,must be valid
     * @return The SuitConfiguration derived from the transformation.
     */
    @Override
    public SuitConfiguration parse(String JsonFilePath) {
        Logger logger = LogManager.getLogger(JsonConfiguration.class);
        // Retrieve the JSON string from the file.
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(JsonFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Transform the JSON string into a JSONObject.
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = JSON.parseObject(jsonContent.toString());
        } catch (Exception e) {
            logger.error("JSON配置文件因格式問題解析錯誤，項目結束，請修改後重啟");
            exit(-1);
        }
        // colorSetting Configuration
        ColorSetting colorSetting = ReFreSH.JMobileSuit.IO.ColorSetting.getInstance();
        Class<ConsoleColor> enumClass = ConsoleColor.class;
        // Retrieve all the color configurations under ColorSetting
        Object ODefaultColor = JSONPath.read(jsonContent.toString(), "$.ColorSetting.DefaultColor");
        Object OAllOkColor = JSONPath.read(jsonContent.toString(), "$.ColorSetting.AllOkColor");
        Object OPromptColor = JSONPath.read(jsonContent.toString(), "$.ColorSetting.PromptColor");
        Object OErrorColor = JSONPath.read(jsonContent.toString(), "$.ColorSetting.ErrorColor");
        Object OListTitleColor = JSONPath.read(jsonContent.toString(), "$.ColorSetting.ListTitleColor");
        Object OCustomInformationColor = JSONPath.read(jsonContent.toString(), "$.ColorSetting.CustomInformationColor");
        Object OInformationColor = JSONPath.read(jsonContent.toString(), "$.ColorSetting.InformationColor");
        // Modify the content of the colorSetting configuration based on the extracted results
        try {
            if (OAllOkColor != null) {
                String enumName = (String) OAllOkColor;
                ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                colorSetting.AllOkColor = enumInstance;
            }
            if (ODefaultColor != null) {
                String enumName = (String) ODefaultColor;
                ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                colorSetting.DefaultColor = enumInstance;
            }
            if (OErrorColor != null) {
                String enumName = (String) OErrorColor;
                ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                colorSetting.ErrorColor = enumInstance;
            }
            if (OListTitleColor != null) {
                String enumName = (String) OListTitleColor;
                ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                colorSetting.ListTitleColor = enumInstance;
            }
            if (OPromptColor != null) {
                String enumName = (String) OPromptColor;
                ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                colorSetting.PromptColor = enumInstance;
            }
            if (OCustomInformationColor != null) {
                String enumName = (String) OCustomInformationColor;
                ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                colorSetting.CustomInformationColor = enumInstance;
            }
            if (OInformationColor != null) {
                String enumName = (String) OInformationColor;
                ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);
                colorSetting.InformationColor = enumInstance;
            }
        } catch (Exception e) {
            // Once any illegal input is detected, set all to default values
            logger.error("Json顏色配置中存在無效顏色名稱");
            colorSetting = new ColorSetting();
        }
        SuitConfigurator suitConfigurator = SuitConfigurator.ofDefault();
        suitConfigurator.use(colorSetting);
        // logger Configuration
        ConfigurationSource source;
        // Configure the file using an absolute path
        Object OAbsolutePath = JSONPath.read(jsonContent.toString(), "$.Logger.AbsolutePath");
        if (OAbsolutePath != null) {
            try {
                String path = (String) OAbsolutePath;
                source = new ConfigurationSource(new FileInputStream(path), new File(path).toURL());
                Configurator.initialize(null, source);
            } catch (Exception e) {
                logger.error("logger絕對路徑配置文件地址解析錯誤");
                e.printStackTrace();
            }
        }
        // Configure the file using a relative path
        Object ORelativePath = JSONPath.read(jsonContent.toString(), "$.Logger.RelativePath");
        if (ORelativePath != null) {
            try {
                String path = (String) ORelativePath;
                //使用System.getProperty
                String config = System.getProperty("user.dir");
                source = new ConfigurationSource(new FileInputStream(config + path));
                Configurator.initialize(null, source);
            } catch (Exception e) {
                logger.error("logger相对路徑配置文件地址解析錯誤");
                e.printStackTrace();
            }
        }
        suitConfigurator.use(logger);
        // Prompt Configuration
        IOServer ioServer = new IOServer();
        Object Oinput = JSONPath.read(jsonContent.toString(), "$.IOServer.input");
        if (Oinput != null) {
            try {
                String input = (String) Oinput;
                if (Oinput.equals("System.in")) {
                    ioServer.ResetInput();
                } else {
                    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
                    ioServer.SetInput(inputStream);
                }
            } catch (Exception e) {
                logger.error("解析IOServer的輸入流出錯");
                e.printStackTrace();
            }
        }
        ioServer.ColorSetting = colorSetting;
        Object OPromptServer = JSONPath.read(jsonContent.toString(), "$.PromptServer");
        CommonPromptServer commonPromptServer = new CommonPromptServer(suitConfigurator.getConfiguration());
        PowerLineThemedPromptServer powerLineThemedPromptServer = new PowerLineThemedPromptServer(suitConfigurator.getConfiguration());
        // After all the above configurations have been completed, a new suitConfiguration can be generated
        if (OPromptServer != null) {
            String promptServer = (String) OPromptServer;
            if (promptServer.equals("Common")) {
                ioServer.Prompt = commonPromptServer;
                suitConfiguration = new CommonSuitConfiguration(BuildInCommandServer.class, ioServer, commonPromptServer, colorSetting, logger);

            } else if (promptServer.equals("PowerLineThemed")) {
                ioServer.Prompt = powerLineThemedPromptServer;
                suitConfiguration = new CommonSuitConfiguration(BuildInCommandServer.class, ioServer, powerLineThemedPromptServer, colorSetting, logger);
            } else {
                logger.error("暂不提供其他类型的PromptServer");
                ioServer.Prompt = commonPromptServer;
                suitConfiguration = new CommonSuitConfiguration(BuildInCommandServer.class, ioServer, new CommonPromptServer(), colorSetting, logger);
            }
        } else {
            suitConfiguration = new CommonSuitConfiguration(BuildInCommandServer.class, ioServer, new CommonPromptServer(), colorSetting, logger);
        }
        return suitConfiguration;
    }

    public static void main(String[] args) throws Exception {
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration configuration = jsonConfiguration.parse("src/main/resources/example.json");
        if (configuration.ColorSetting() == null) {
            System.out.println("yes");
        }
        new SuitHost(Client.class,
                configuration).Run();
    }
}
