package ReFreSH.JMobileSuit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LangResourceBundleTest {

    private LangResourceBundle langResourceBundle;

    @Before
    public void setUp() {
        // Initialize an instance of LangResourceBundle
        langResourceBundle = new LangResourceBundle();
    }

    @Test
    public void testStringsAreLoaded() {
        // Verify that each string property is not null
        assertNotNull(langResourceBundle.Bic);
        assertNotNull(langResourceBundle.BicExp1);
        assertNotNull(langResourceBundle.BicExp2);
        assertNotNull(langResourceBundle.Done);
        assertNotNull(langResourceBundle.Error);
        assertNotNull(langResourceBundle.InvalidCommand);
        assertNotNull(langResourceBundle.MemberNotFound);
        assertNotNull(langResourceBundle.Members);
        assertNotNull(langResourceBundle.ObjectNotFound);
        assertNotNull(langResourceBundle.Unknown);
        assertNotNull(langResourceBundle.ViewBic);
        assertNotNull(langResourceBundle.WorkInstance);
        assertNotNull(langResourceBundle.ReturnValue);
        assertNotNull(langResourceBundle.Default);
        assertNotNull(langResourceBundle.AllOK);
        assertNotNull(langResourceBundle.ApplicationException);
    }

    // More test methods can be added to verify specific string values
    // For example:
    @Test
    public void testSpecificStringValue() {
        assertEquals("就绪.", langResourceBundle.Done); // Check if Done equals "就绪."
    }

    @Test
    public void testBicStringValue() {
        assertEquals("内置指令:", langResourceBundle.Bic); // 假设 Bic 应该等于 "BIC字符串."
    }

    @Test
    public void testBicExp1StringValue() {
        assertEquals("所有的内置指令都可以不使用特有前缀\"", langResourceBundle.BicExp1); // 假设 BicExp1 应该等于 "BIC示例1."
    }

    @Test
    public void testBicExp2StringValue() {
        assertEquals("\"而被调用; 然而, 如果当前的对象含有与内置指令有命名冲突的成员, 调用内置指令时使用前缀是必要的.", langResourceBundle.BicExp2); // 假设 BicExp2 应该等于 "BIC示例2."
    }

    @Test
    public void testErrorStringValue() {
        assertEquals("错误:", langResourceBundle.Error); // 假设 Error 应该等于 "发生错误."
    }

    @Test
    public void testInvalidCommandStringValue() {
        assertEquals("非法的命令", langResourceBundle.InvalidCommand); // 假设 InvalidCommand 应该等于 "无效命令."
    }

    @Test
    public void testMemberNotFoundStringValue() {
        assertEquals("没有找到这种成员", langResourceBundle.MemberNotFound); // 假设 MemberNotFound 应该等于 "未找到成员."
    }

    @Test
    public void testObjectNotFoundStringValue() {
        assertEquals("找不到对象", langResourceBundle.ObjectNotFound); // 假设 ObjectNotFound 应该等于 "对象未找到."
    }

    @Test
    public void testUnknownStringValue() {
        assertEquals("未知", langResourceBundle.Unknown); // 假设 Unknown 应该等于 "未知."
    }

    @Test
    public void testViewBicStringValue() {
        assertEquals("要显示内置指令, 请使用命令 '", langResourceBundle.ViewBic); // 假设 ViewBic 应该等于 "查看BIC."
    }

    @Test
    public void testWorkInstanceStringValue() {
        assertEquals("当前对象:", langResourceBundle.WorkInstance); // 假设 WorkInstance 应该等于 "工作实例."
    }

    @Test
    public void testReturnValueStringValue() {
        assertEquals("返回值", langResourceBundle.ReturnValue); // 假设 ReturnValue 应该等于 "返回值."
    }

    @Test
    public void testDefaultStringValue() {
        assertEquals("默认", langResourceBundle.Default); // 假设 Default 应该等于 "默认值."
    }

    @Test
    public void testAllOKStringValue() {
        assertEquals("一切正常", langResourceBundle.AllOK); // 假设 AllOK 应该等于 "一切正常."
    }

    @Test
    public void testApplicationExceptionStringValue() {
        assertEquals("应用内部错误", langResourceBundle.ApplicationException); // 假设 ApplicationException 应该等于 "应用程序异常."
    }

}
