// src/test/java/ReFreSH/JMobileSuit/ObjectModel/Parsing/AutoDynamicParameterTest.java
package ReFreSH.JMobileSuit.ObjectModel.Parsing;

import ReFreSH.JMobileSuit.ObjectModel.Parsing.AsCollection;
import ReFreSH.JMobileSuit.ObjectModel.Parsing.AutoDynamicParameter;
import ReFreSH.JMobileSuit.ObjectModel.Parsing.Switch;
import ReFreSH.JMobileSuit.ObjectModel.Parsing.WithDefault;
import org.junit.Test;
import static org.junit.Assert.*;

public class AutoDynamicParameterTest {

    // Example subclass of AutoDynamicParameter for testing
    private static class TestDynamicParameter extends AutoDynamicParameter {
        @Option("name")
        public String name;

        @Option("age")
        public int age;

        @Switch("verbose")
        public boolean verbose;

        // Additional fields for testing collection parsing
        @Option("items")
        @AsCollection
        public java.util.List<String> items;

        // Additional fields for testing parsing with default values
        @Option("defaultParam")
        @WithDefault
        public String defaultParam = "defaultValue";
    }

    @Test
    public void testParseInvalidOptions() {
        TestDynamicParameter parameter = new TestDynamicParameter();
        String[] options = {"-invalidOption", "value"};

        assertFalse(parameter.parse(options));
    }

    @Test
    public void testParseMissingRequiredOptions() {
        TestDynamicParameter parameter = new TestDynamicParameter();
        String[] options = {"-name", "John"};

        assertFalse(parameter.parse(options));
    }

    @Test
    public void testParseWithInvalidCollectionOption() {
        TestDynamicParameter parameter = new TestDynamicParameter();
        String[] options = {"-items", "item1", "item2"};

        assertFalse(parameter.parse(options));
    }

    @Test
    public void testConnectStringArray() {
        String[] array = {"value1", "value2", "value3"};
        String result = AutoDynamicParameter.ConnectStringArray(array);

        assertEquals("value1 value2 value3", result);
    }
}
