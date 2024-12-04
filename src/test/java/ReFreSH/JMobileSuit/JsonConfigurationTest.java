package ReFreSH.JMobileSuit;

import ReFreSH.JMobileSuit.Demo.Client;
import ReFreSH.JMobileSuit.IO.ColorSetting;
import ReFreSH.JMobileSuit.IO.CommonPromptServer;
import ReFreSH.JMobileSuit.IO.ConsoleColor;
import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.NeuesProjekt.PowerLineThemedPromptServer;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class JsonConfigurationTest {
    String JSON_PATH = "src/main/resources/testExample.json";

    /**
     * Testing Strategy
     * Test parse(String JsonFilePath)
     * Including the contents within the JSON file, the equivalent class divisions are as follows:
     * colorSetting :
     *   The Num of Setting : fully reset; not set at all; partially set.
     *   The Setting Value : within the ConsoleColor enumeration; not within the ConsoleColor enumeration; null
     * PromptServer : "Common"; "PowerLineThemed"; any other string; null
     * IOServer.input : "System.in"; custom input stream; null
     *
     */

    @Test
    public void colorTest_fully_reset() {
        // Create a JSON file for testing
        JSONObject colorSetting = new JSONObject();
        colorSetting.put("DefaultColor", "Red"); // originally White
        colorSetting.put("PromptColor", "Blue"); // originally Magenta
        colorSetting.put("ErrorColor", "White"); // originally Red
        colorSetting.put("AllOkColor", "Yellow"); // originally Green
        colorSetting.put("ListTitleColor", "Green"); // originally Yellow
        colorSetting.put("CustomInformationColor", "White"); // originally DarkCyan
        colorSetting.put("InformationColor", "Magenta"); // originally DarkBlue
        JSONObject root = new JSONObject();
        root.put("ColorSetting", colorSetting);
        Path path = Paths.get(JSON_PATH);
        try {
            Files.createDirectories(path.getParent());
            try (FileWriter file = new FileWriter(path.toFile())) {
                file.write(root.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse execute
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration suitConfiguration = jsonConfiguration.parse(JSON_PATH);
        // Verification Result
        assertEquals(ConsoleColor.Red, suitConfiguration.ColorSetting().DefaultColor);
        assertEquals(ConsoleColor.Blue, suitConfiguration.ColorSetting().PromptColor);
        assertEquals(ConsoleColor.White, suitConfiguration.ColorSetting().ErrorColor);
        assertEquals(ConsoleColor.Yellow, suitConfiguration.ColorSetting().AllOkColor);
        assertEquals(ConsoleColor.Green, suitConfiguration.ColorSetting().ListTitleColor);
        assertEquals(ConsoleColor.White, suitConfiguration.ColorSetting().CustomInformationColor);
        assertEquals(ConsoleColor.Magenta, suitConfiguration.ColorSetting().InformationColor);
    }

    @Test
    public void colorTest_partially_set() {
        // Create a JSON file for testing
        JSONObject colorSetting = new JSONObject();
        colorSetting.put("DefaultColor", "Red"); // originally White
        colorSetting.put("ErrorColor", "White"); // originally Red
        colorSetting.put("AllOkColor", "Yellow"); // originally Green
        colorSetting.put("InformationColor", "Magenta"); // originally DarkBlue
        JSONObject root = new JSONObject();
        root.put("ColorSetting", colorSetting);
        Path path = Paths.get(JSON_PATH);
        try {
            Files.createDirectories(path.getParent());
            try (FileWriter file = new FileWriter(path.toFile())) {
                file.write(root.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse execute
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration suitConfiguration = jsonConfiguration.parse(JSON_PATH);
        // Verification Result,if there is no input, fill in the default value
        assertEquals(ConsoleColor.Red, suitConfiguration.ColorSetting().DefaultColor);
        assertEquals(ConsoleColor.Magenta, suitConfiguration.ColorSetting().PromptColor);
        assertEquals(ConsoleColor.White, suitConfiguration.ColorSetting().ErrorColor);
        assertEquals(ConsoleColor.Yellow, suitConfiguration.ColorSetting().AllOkColor);
        assertEquals(ConsoleColor.Yellow, suitConfiguration.ColorSetting().ListTitleColor);
        assertEquals(ConsoleColor.DarkCyan, suitConfiguration.ColorSetting().CustomInformationColor);
        assertEquals(ConsoleColor.Magenta, suitConfiguration.ColorSetting().InformationColor);
    }

    @Test
    public void colorTest_no_set() {
        // Create a JSON file for testing
        JSONObject colorSetting = new JSONObject();
        JSONObject root = new JSONObject();
        root.put("ColorSetting", colorSetting);
        Path path = Paths.get(JSON_PATH);
        try {
            Files.createDirectories(path.getParent());
            try (FileWriter file = new FileWriter(path.toFile())) {
                file.write(root.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse execute
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration suitConfiguration = jsonConfiguration.parse(JSON_PATH);
        // Verification Result,if there is no input, fill in the default value
        assertEquals(ConsoleColor.White, suitConfiguration.ColorSetting().DefaultColor);
        assertEquals(ConsoleColor.Magenta, suitConfiguration.ColorSetting().PromptColor);
        assertEquals(ConsoleColor.Red, suitConfiguration.ColorSetting().ErrorColor);
        assertEquals(ConsoleColor.Green, suitConfiguration.ColorSetting().AllOkColor);
        assertEquals(ConsoleColor.Yellow, suitConfiguration.ColorSetting().ListTitleColor);
        assertEquals(ConsoleColor.DarkCyan, suitConfiguration.ColorSetting().CustomInformationColor);
        assertEquals(ConsoleColor.DarkBlue, suitConfiguration.ColorSetting().InformationColor);
    }

    @Test
    public void colorTest_no_within_enum() {
        // Create a JSON file for testing
        JSONObject colorSetting = new JSONObject();
        colorSetting.put("DefaultColor", "Ace"); // Orange is NOT within the ConsoleColor enumeration
        colorSetting.put("ErrorColor", "Thorn"); // Thorn is NOT within the ConsoleColor enumeration
        colorSetting.put("AllOkColor", "Ninja"); // Ninja is NOT within the ConsoleColor enumeration
        colorSetting.put("InformationColor", "HIT"); // HIT is NOT within the ConsoleColor enumeration
        JSONObject root = new JSONObject();
        root.put("ColorSetting", colorSetting);
        Path path = Paths.get(JSON_PATH);
        try {
            Files.createDirectories(path.getParent());
            try (FileWriter file = new FileWriter(path.toFile())) {
                file.write(root.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse execute
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration suitConfiguration = jsonConfiguration.parse(JSON_PATH);
        // Verification Result,if there is no input, fill in the default value
        assertEquals(ConsoleColor.White, suitConfiguration.ColorSetting().DefaultColor);
        assertEquals(ConsoleColor.Magenta, suitConfiguration.ColorSetting().PromptColor);
        assertEquals(ConsoleColor.Red, suitConfiguration.ColorSetting().ErrorColor);
        assertEquals(ConsoleColor.Green, suitConfiguration.ColorSetting().AllOkColor);
        assertEquals(ConsoleColor.Yellow, suitConfiguration.ColorSetting().ListTitleColor);
        assertEquals(ConsoleColor.DarkCyan, suitConfiguration.ColorSetting().CustomInformationColor);
        assertEquals(ConsoleColor.DarkBlue, suitConfiguration.ColorSetting().InformationColor);
    }

    @Test
    public void promptTest_common(){
        // Create a JSON file for testing
        JSONObject root = new JSONObject();
        root.put("PromptServer", "Common");
        Path path = Paths.get(JSON_PATH);
        try {
            Files.createDirectories(path.getParent());
            try (FileWriter file = new FileWriter(path.toFile())) {
                file.write(root.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse execute
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration suitConfiguration = jsonConfiguration.parse(JSON_PATH);
        // Verification Result
        assertEquals(CommonPromptServer.class,suitConfiguration.Prompt().getClass());
    }

    @Test
    public void promptTest_powerLineThemed(){
        // Create a JSON file for testing
        JSONObject root = new JSONObject();
        root.put("PromptServer", "PowerLineThemed");
        Path path = Paths.get(JSON_PATH);
        try {
            Files.createDirectories(path.getParent());
            try (FileWriter file = new FileWriter(path.toFile())) {
                file.write(root.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse execute
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration suitConfiguration = jsonConfiguration.parse(JSON_PATH);
        // Verification Result
        assertEquals(PowerLineThemedPromptServer.class,suitConfiguration.Prompt().getClass());
    }

    @Test
    public void promptTest_other(){
        // Create a JSON file for testing
        JSONObject root = new JSONObject();
        root.put("PromptServer", "Terror");
        Path path = Paths.get(JSON_PATH);
        try {
            Files.createDirectories(path.getParent());
            try (FileWriter file = new FileWriter(path.toFile())) {
                file.write(root.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse execute
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration suitConfiguration = jsonConfiguration.parse(JSON_PATH);
        // Verification Result
        assertEquals(CommonPromptServer.class,suitConfiguration.Prompt().getClass());
    }

    @Test
    public void promptTest_null(){
        // Create a JSON file for testing
        JSONObject root = new JSONObject();
        Path path = Paths.get(JSON_PATH);
        try {
            Files.createDirectories(path.getParent());
            try (FileWriter file = new FileWriter(path.toFile())) {
                file.write(root.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse execute
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration suitConfiguration = jsonConfiguration.parse(JSON_PATH);
        // Verification Result
        assertEquals(CommonPromptServer.class,suitConfiguration.Prompt().getClass());
    }


    @Test
    public void IOTest_standard(){
        // Create a JSON file for testing
        JSONObject IOServer = new JSONObject();
        IOServer.put("input","System.in");
        JSONObject root = new JSONObject();
        root.put("IOServer",IOServer);
        Path path = Paths.get(JSON_PATH);
        try {
            Files.createDirectories(path.getParent());
            try (FileWriter file = new FileWriter(path.toFile())) {
                file.write(root.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse execute
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration suitConfiguration = jsonConfiguration.parse(JSON_PATH);
        // Verification Result
        IOServer ioServer = suitConfiguration.IO();
        assertEquals(System.in, ioServer.GetInput());
    }

    @Test
    public void IOTest_custom(){
        // Create a JSON file for testing
        JSONObject IOServer = new JSONObject();
        String testStr = "Folding was never an option";
        IOServer.put("input",testStr);
        JSONObject root = new JSONObject();
        root.put("IOServer",IOServer);
        Path path = Paths.get(JSON_PATH);
        try {
            Files.createDirectories(path.getParent());
            try (FileWriter file = new FileWriter(path.toFile())) {
                file.write(root.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse execute
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration suitConfiguration = jsonConfiguration.parse(JSON_PATH);
        // Verification Result
        IOServer ioServer = suitConfiguration.IO();
        assertNotEquals(System.in, ioServer.GetInput());
        String getStr = ioServer.ReadLine();
        assertEquals(testStr,getStr);
    }

    @Test
    public void IOTest_null(){
        // Create a JSON file for testing
        JSONObject IOServer = new JSONObject();
        JSONObject root = new JSONObject();
        root.put("IOServer",IOServer);
        Path path = Paths.get(JSON_PATH);
        try {
            Files.createDirectories(path.getParent());
            try (FileWriter file = new FileWriter(path.toFile())) {
                file.write(root.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse execute
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration suitConfiguration = jsonConfiguration.parse(JSON_PATH);
        // Verification Result
        IOServer ioServer = suitConfiguration.IO();
        assertEquals(System.in, ioServer.GetInput());
    }

    @Test
    public void HybridTest(){
        // Create a JSON file for testing
        JSONObject root = new JSONObject();
        JSONObject colorSetting = new JSONObject();
        colorSetting.put("DefaultColor", "Red"); // originally White
        colorSetting.put("PromptColor", "Blue"); // originally Magenta
        colorSetting.put("ErrorColor", "White"); // originally Red
        colorSetting.put("AllOkColor", "Yellow"); // originally Green
        colorSetting.put("ListTitleColor", "Green"); // originally Yellow
        colorSetting.put("CustomInformationColor", "White"); // originally DarkCyan
        colorSetting.put("InformationColor", "Magenta"); // originally DarkBlue
        root.put("ColorSetting", colorSetting);
        root.put("PromptServer", "PowerLineThemed");
        JSONObject IOServer = new JSONObject();
        IOServer.put("input","System.in");
        root.put("IOServer",IOServer);
        Path path = Paths.get(JSON_PATH);
        try {
            Files.createDirectories(path.getParent());
            try (FileWriter file = new FileWriter(path.toFile())) {
                file.write(root.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse execute
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        SuitConfiguration suitConfiguration = jsonConfiguration.parse(JSON_PATH);
        // Verification Result, ConsoleColor Part
        assertEquals(ConsoleColor.Red, suitConfiguration.ColorSetting().DefaultColor);
        assertEquals(ConsoleColor.Blue, suitConfiguration.ColorSetting().PromptColor);
        assertEquals(ConsoleColor.White, suitConfiguration.ColorSetting().ErrorColor);
        assertEquals(ConsoleColor.Yellow, suitConfiguration.ColorSetting().AllOkColor);
        assertEquals(ConsoleColor.Green, suitConfiguration.ColorSetting().ListTitleColor);
        assertEquals(ConsoleColor.White, suitConfiguration.ColorSetting().CustomInformationColor);
        assertEquals(ConsoleColor.Magenta, suitConfiguration.ColorSetting().InformationColor);
        // Prompt Part
        assertEquals(PowerLineThemedPromptServer.class,suitConfiguration.Prompt().getClass());
        // Input Stream Part
        IOServer ioServer = suitConfiguration.IO();
        assertEquals(System.in, ioServer.GetInput());
    }



    // 测试用例 3：验证当 ColorSetting 不为 null 时不会输出 "yes"
//    @Test
//    public void testMainWithNonNullColorSetting() throws Exception {
//        // 创建一个临时的 JSON 文件（模拟有 ColorSetting 配置）
//        String jsonContent = "{ \"ColorSetting\": { \"AllOkColor\": \"Blue\" } }";
//        File tempFile = File.createTempFile("test_", ".json");
//        tempFile.deleteOnExit();
//        FileUtils.writeStringToFile(tempFile, jsonContent, "UTF-8");
//
//        // 使用 System.setOut() 捕获控制台输出
//        PrintStream originalOut = System.out;
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//        // 调用 main 方法
//        JsonConfiguration.main(new String[]{tempFile.getAbsolutePath()});
//
//        // 验证控制台输出中不包含 "yes"
//        assertFalse(outContent.toString().contains("yes"));
//
//        // 恢复控制台输出
//        System.setOut(originalOut);
//    }

}