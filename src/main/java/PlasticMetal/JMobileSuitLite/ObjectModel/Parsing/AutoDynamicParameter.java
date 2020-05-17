package PlasticMetal.JMobileSuitLite.ObjectModel.Parsing;

import PlasticMetal.JMobileSuitLite.ObjectModel.DynamicParameter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A DynamicParameter which can parse itself automatically
 */
public abstract class AutoDynamicParameter implements DynamicParameter
{
    /**
     * Members of this AutoDynamicParameter
     */
    private final Map<String, ParsingMember> Members = new HashMap<>();

    /**
     * Initialize a AutoDynamicParameter
     */
    protected AutoDynamicParameter()
    {
        Class<?> myType = this.getClass();
        for(Field property:myType.getFields() )
        {
            Option option=property.getAnnotation(Option.class);
            Switch theSwitch=property.getAnnotation(Switch.class);
            if(option==null&&theSwitch==null)continue;

            Method parser= ParsingAPIs.getParser( property.getAnnotation(SuitParser.class));

            boolean withDefault= property.getAnnotation(WithDefault.class)!=null;
            if(option!=null){
                Members.put(option.value(),new ParsingMember(property,parser,option.value(),option.length(),withDefault));
            }else {
                Members.put(theSwitch.value(),new ParsingMember(property,parser,theSwitch.value(),0,withDefault));
            }

        }
    }

    private static String ConnectStringArray(String[] array)
    {
        if (array.length == 0) return "";
        StringBuilder r = new StringBuilder(array[0]);
        if (array.length <= 1) return r.toString();
        for (int i = 1; i < array.length; i++)
            r.append(' ').append(array[i]);
        return r.toString();
    }

    /**
     * Parse this Parameter from String[].
     *
     * @param options String[] to parse from.
     * @return Whether the parsing is successful
     */
    @Override
    public boolean Parse(String[] options)
    {
        if (options != null && options.length > 0)
        {
            for (int i = 0; i < options.length; )
            {
                if (!ParseMemberRegex.matcher(options[i]).find())
                    return false;
                String name = options[i].substring(1);
                if (!Members.containsKey(name))
                    return false;
                ParsingMember parseMember = Members.get(name);
                i++;
                int j = i + parseMember.ParseLength;
                if (j > options.length)
                    return false;
                String[] parseArg =Arrays.copyOfRange(options,i,j);
                parseMember.Set(this,
                        ConnectStringArray(parseArg));
                i = j;
            }
        }
        for (ParsingMember m:Members.values()){
            if(!m.Assigned){
                System.out.println(m.Name+" is Not assigned");
                return false;}
        }
        return true;
    }


    private static class ParsingMember
    {
        private final Field Setter;
        private final Method Converter;

        public boolean Assigned;

        public final int ParseLength;

        public final String Name;

        public void Set(AutoDynamicParameter instance, String value)
        {
            try
            {
                Object newValue=Converter==null ?value:Converter.invoke(null,value);
                Setter.set(instance,ParseLength == 0 ? true : newValue);
                Assigned = true;
            }
            catch (Exception e)
            {
                Assigned=false;
            }


        }

        public ParsingMember(Field setter,
                             Method converter, String name, int parseLength, boolean withDefault
        )
        {
            Setter = setter;
            Converter = converter;
            Name = name;
            ParseLength = parseLength;
            Assigned = withDefault || parseLength == 0;
        }
    }

    private static final Pattern ParseMemberRegex = Pattern.compile("^-\\w+$");




}
