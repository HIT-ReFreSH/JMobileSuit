package ReFreSH.JMobileSuit;

import ReFreSH.JMobileSuit.Demo.Client;
import ReFreSH.JMobileSuit.IO.ColorSetting;
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
import java.lang.module.Configuration;
import java.lang.reflect.Field;

import static java.lang.System.exit;

//this class is for load configuration from the file which format is json
public class JsonConfiguration implements IConfiguration{
    SuitConfiguration suitConfiguration;


    @Override
    public SuitConfiguration parse(String JsonFilePath){

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
        System.out.println(jsonContent);
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject = JSON.parseObject(jsonContent.toString());
        }catch(Exception e){
            logger.error("JSON配置文件因格式問題解析錯誤，項目結束，請修改後重啟");
            exit(-1);
        }

        //開始配置文件

        //首先配置ColorSetting
        ColorSetting colorSetting = new ColorSetting();
        try{
            Class<ConsoleColor> enumClass = ConsoleColor.class;
            Object OAllOkColor = (Object) JSONPath.read(jsonContent.toString(), "$.ColorSetting.AllOkColor");

            if(OAllOkColor == null){
                System.out.println("為空");
            }
            String enumName = (String) o;


            System.out.println(enumName);
//            String enumName = "Blue";
            // 获取枚举对象
            ConsoleColor enumInstance = Enum.valueOf(enumClass, enumName);

            colorSetting.AllOkColor = enumInstance;
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println(colorSetting.AllOkColor);
        System.out.println(ConsoleColor.Blue == colorSetting.AllOkColor);
        SuitConfigurator suitConfigurator = SuitConfigurator.ofDefault();
        suitConfigurator.ColorSetting = colorSetting;
        PowerLineThemedPromptServer powerLineThemedPromptServer = new PowerLineThemedPromptServer(suitConfigurator.getConfiguration());

        //單獨配置prompt,
        suitConfiguration = new CommonSuitConfiguration(BuildInCommandServer.class,new IOServer(),powerLineThemedPromptServer,colorSetting,logger);



        suitConfigurator.PromptServerType = powerLineThemedPromptServer.getClass();

        SuitConfiguration configuration = suitConfigurator.getConfiguration();












        //我们将log4j2.xml放在D盘下
             //这是需要手动的加载
             //绝对路径配置文件
             ConfigurationSource source;
             try {
//               //方法1  使用  public ConfigurationSource(InputStream stream) throws IOException 构造函数
//               source = new ConfigurationSource(new FileInputStream("E:\\\\桌面\\\\book\\\\开源软件实践\\\\lab3\\\\JMobileSuit\\\\src\\\\main\\\\resources\\\\log4j.properties"));
                 //方法2 使用 public ConfigurationSource(InputStream stream, File file)构造函数
//                 File config = new File("D:\\log4j.properties");
//                 source = new ConfigurationSource(new FileInputStream(config), config);
//               //方法3 使用 public ConfigurationSource(InputStream stream, URL url) 构造函数
//               String path = "E:\\桌面\\book\\开源软件实践\\lab3\\JMobileSuit\\src\\main\\resources\\log4j.properties";
//               source = new ConfigurationSource(new FileInputStream(path), new File(path).toURL());
                 //source.setFile(new File("D:\log4j2.xml"));
                 //source.setInputStream(new FileInputStream("D:\log4j2.xml"));
//                 Configurator.initialize(null, source);

                 System.out.println("hello1");
                 logger.trace("trace...");
                 logger.debug("debug...");
                 logger.info("info...");
                 logger.warn("warn...");
                 logger.error("error...");
                 logger.fatal("fatal...");
             }catch(Exception e){
            e.printStackTrace();
        }



            return suitConfiguration;
    }

    public static void main(String[] args) throws Exception {
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration configuration = jsonConfiguration.parse("src/main/resources/example.json");
        System.out.println(configuration.Prompt());
        new SuitHost(Client.class,
                configuration).Run();
    }
}
