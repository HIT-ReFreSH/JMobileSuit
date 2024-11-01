package ReFreSH.JMobileSuit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LangResourceBundleTest {

    private LangResourceBundle langResourceBundle;

    @Before
    public void setUp() {
        // 初始化 LangResourceBundle 实例
        langResourceBundle = new LangResourceBundle();
    }

    @Test
    public void testStringsAreLoaded() {
        // 验证每个字符串属性是否不为 null
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

    // 可以添加更多的测试方法来验证特定的字符串值
    // 例如：
    @Test
    public void testSpecificStringValue() {
        assertEquals("就绪.", langResourceBundle.Done);
    }
}