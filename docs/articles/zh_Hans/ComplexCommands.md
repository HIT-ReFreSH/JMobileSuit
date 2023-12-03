---
title: 创建更复杂的 PlasticMetal.JMobileSuitLite 命令
date: 2020-04-11 12:45:11
---

**由于JVM和CLR的区别, 这些特性在 0.1.1.4 或更晚版本才被完整移植**

代码样例在本节末尾。

## 可解析类型的参数

**这些特性在 JMobileSuitLite 0.1.6 或更晚版本被移植**

MobileSuit 的参数类型是任意的，只要你能提供一个解析器;
JMobileSuitLite 如今也支持这一功能。

### 创建，或者选取一个解析器

一个解析器是一个以字符串为输入，输出特定对象的方法;
比如: Integer::parseInt, Long::parseLong, 等等.
解析器必须使用 **public static** 修饰. 

### 创建使用可解析类型的命令

添加一个 ***SuitParser*** 注解到参数前, 它会告知解析器方法的名字，以及包含它的类。

例如:

``` java
    @SuitAlias("Sn")
    public void ShowNumber(
            @SuitParser(ParserClass = Integer.class, MethodName = "parseInt")
            int i
    ){
        IO().WriteLine(String.valueOf(i));
    }
```

如果用户输入了不可解析的参数, ***TraceBack*** 将为 **非法指令**.



## 使用数组参数的命令

### 创建只使用一个数组参数的命令

添加一个方法叫做 ***GoodEvening*** 到类 ***Client*** 中. 它有一个 ***String[]*** 类型的参数. 你可以为这个方法增加别名 'GE' . 方法的内容是任意的.

编译并运行你的应用.

在控制台中, 你可以输入:

**GoodEvening** 接着 0, 1, 2 ... 个参数, 它们会被解析为一个数组. 

### 创建使用一个数组和其它参数的命令

添加一个方法叫做 ***GoodEvening2*** 到类 ***Client*** 中. 它有一个 ***String[]*** 类型的参数, 和一个 ***String*** 类型的参数. 类型为 ***String[]*** 的参数 **必须是** 这个方法的最后一个参数.  你可以为这个方法增加别名 'GE2'. 方法的内容是任意的.

编译并运行你的应用.

在控制台中, 你可以输入:

**GoodEvening2** 接着 1, 2 ... 个参数, 第一个参数会被映射到 ***String*** 类型的参数, 其它的(可能是0个)会被视为数组，映射到 ***String[]*** 类型的参数. 

编写这种命令最重要的是把使用 ***String[]*** 类型的参数 **必须放在** 方法参数列表的最后一位，否则JMobileSuitLite将无法正确解析它。

### 可解析类型的数组参数

**这些特性在 JMobileSuitLite 0.1.6 或更晚版本被移植**

数组参数不仅可以是 ***String[]***, 还可以是其它类型的数组. 你只需要添加 ***SuitParser*** 注解在参数前.

例如:

```java
    @SuitAlias("Sn")
    public void ShowNumber(
            @SuitParser(ParserClass = Integer.class, MethodName = "parseInt")
            int i,
            @SuitParser(ParserClass = Integer.class, MethodName = "parseInt")
            int[] j
    ){
        IO().WriteLine(String.valueOf(i));
        IO().WriteLine(j.length>=1?String.valueOf(j[0]):"");
    }
```

**注意!** 解析器解析字符串为某一类型, **而不是** 解析字符串数组为某一类型！


## 创建使用动态参数的命令

### 创建一个实现DynamicParameter的类

添加一个类到类 ***Client*** 中, 叫做 ***GoodMorningParameter***, 它实现 ***PlasticMetal.JMobileSuitLite.ObjectModel.Interfaces.DynamicParameter*** 接口. 这个类应该是 `public static`的:

``` java
    public static class GoodMorningParameter implements DynamicParameter{

        /**
         * Parse this Parameter from String[].
         *
         * @param options String[] to parse from.
         * @return Whether the parsing is successful
         */
        @Override
        public Boolean Parse(String[] options)
        {


        }
    }
```

向 ***GoodMorningParameter*** 添加内容, 填充 ***Parse(String[] options)***. 这个函数返回true **当且仅当** 解析成功。

例如:

``` java
    public static class GoodMorningParameter implements DynamicParameter{
        public String name="foo";

        /**
         * Parse this Parameter from String[].
         *
         * @param options String[] to parse from.
         * @return Whether the parsing is successful
         */
        @Override
        public Boolean Parse(String[] options)
        {
            if(options.length==1){
                name=options[0];
                return true;
            }else return options.length==0;

        }
    }
```

### 创建一个只使用动态参数的命令

添加一个方法叫做 ***GoodMorning*** 到类 ***Client*** . 它有一个 ***GoodMorningParameter*** 类型的参数. 你可以为这个方法增加别名 'GM'. 方法的内容是任意的.

编译并运行你的应用.

在控制台中, 你可以输入:

**GoodMorning** 跟着 0, 1, 2 ... 个参数, 他们会被视为一个数组, 然后被 ***GoodMorningParameter::Parse*** 解析. 

### 创建一个使用动态参数和其它参数的命令

添加一个方法叫做 ***GoodMorning2*** 到类 ***Client*** . 它有一个 ***GoodMorningParameter*** 类型的参数, 和一个 ***String*** 类型的参数. 类型为 ***GoodMorningParameter*** 的参数 **必须放在** 方法参数列表的最后一位.  你可以为这个方法增加别名 'GE2'. 方法的内容是任意的.

编译并运行你的应用.

在控制台中, 你可以输入:

**GoodMorning2** 跟着 1, 2 ... 个参数, 第一个参数会被映射到 ***String*** 类型的参数, 其它的会被视为数组并被解析为 ***GoodMorningParameter*** 类型的参数. 

编写这种命令最重要的是 ***? extends DynamicParameter*** 类型的参数 **必须放在** 方法参数列表的最后一位，否则JMobileSuitLite将无法正确解析它。

### 自动解析的动态参数

**这些特性在 JMobileSuitLite 0.1.6 或更晚版本被移植**

现在，你不再需要自己编写DynamicParameter::Parse.

你可以让你的类继承自 ***AutoDynamicParameter***, 然后添加一些带注解的字段，来完成一个动态参数类型.

JMobileSuit支持四种用于动态参数类型的字段的注解:

1. ***Option*** 说明这是一个选项字段，被解析自"-xxx value". 如果你希望解析 "-xxx value_part1 value_part2", 可以改变length，例如: `@Option(value="xxx",length=2)`, 如此, 解析器的输入将会为 "value_part1 value_part2"
2. ***Switch*** 说明这是一个开关字段, 字段类型必须为 ***boolean*** ；该字段将被解析自形如 "-sw" 的参数. 如果 "-sw" 参数存在, 字段被填写为true; 否则该字段的值 **不会改变**, 而不是自动填写为false. 一个字段只能为 ***Switch*** 或 ***Option*** 之一.
3. ***WithDefault*** 说明这个字段带有默认值, 因此可以不被填写. ***Switch*** 字段不需要这个注解.
4. ***SuitParser*** 如果一个 ***Option*** 字段不是字符串类型，它将需要解析器.

如果一个字段既不是 ***Switch*** 也不是 ***Option***, 它将被忽略.

如果一个 ***Option*** 不含 ***WithDefault*** 注解，并且没有被填写，解析会失败。

例如:

``` java
    public static class SleepArgument extends AutoDynamicParameter{
        @Option("n")
        public String Name;

        @Option("t")
        @SuitParser(ParserClass = Integer.class, MethodName = "parseInt")
        @WithDefault
        public int SleepTime=0;
        @Switch("s")
        public boolean isSleeping;
    }
```


## 代码示例

在本节之后，你 **Client.java** 中的代码大概像:

``` java
package PlasticMetal.JMobileSuit.Demo;

import PlasticMetal.JMobileSuitLite.NeuesProjekt.PowerLineThemedPromptServer;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitAlias;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitInfo;
import PlasticMetal.JMobileSuitLite.ObjectModel.DynamicParameter;
import PlasticMetal.JMobileSuitLite.ObjectModel.Parsing.*;
import PlasticMetal.JMobileSuitLite.ObjectModel.SuitClient;
import PlasticMetal.JMobileSuitLite.SuitHost;

@SuitInfo("Demo")
public class Client extends SuitClient
{
    @SuitAlias("H")
    @SuitInfo("hello command")
    public void Hello()
    {

        IO().WriteLine("Hello! MobileSuit!");
    }

    public String Bye(String name)
    {
        IO().WriteLine("Bye!" + name);
        return "bye";
    }

    public static void main(String[] args) throws Exception
    {
        new SuitHost(Client.class,
                PowerLineThemedPromptServer.getPowerLineThemeConfiguration()).Run();
    }
    @SuitAlias("GM")
    public void GoodMorning(GoodMorningParameter arg){
        IO().WriteLine("Good morning,"+arg.name);
    }

    @SuitAlias("GM2")
    public void GoodMorning2(String arg, GoodMorningParameter arg1){
        IO().WriteLine("Good morning, "+arg+" and "+ arg1.name);
    }

    @SuitAlias("GE")
    public void GoodEvening(String[] arg){

        IO().WriteLine("Good Evening, "+(arg.length>=1?arg[0]:""));
    }

    @SuitAlias("Sn")
    public void ShowNumber(
            @SuitParser(ParserClass = Integer.class, MethodName = "parseInt")
            int i,
            @SuitParser(ParserClass = Integer.class, MethodName = "parseInt")
            int[] j
    ){
        IO().WriteLine(String.valueOf(i));
        IO().WriteLine(j.length>=1?String.valueOf(j[0]):"");
    }

    @SuitAlias("GE2")
    public void GoodEvening2(String arg0,String[] arg){

        IO().WriteLine("Good Evening, "+arg0+(arg.length>=1?" and "+arg[0]:""));
    }

    @SuitAlias("Sl")
    @SuitInfo("Sleep {-n name (, -t hours, -s)}")
    public void Sleep(SleepArgument argument){
        if(argument.isSleeping){
            IO().WriteLine(argument.Name+" has been sleeping for "+ argument.SleepTime +" hour(s)." );
        }else {
            IO().WriteLine(argument.Name+" is not sleeping." );
        }
    }

    public static class SleepArgument extends AutoDynamicParameter{
        @Option("n")
        public String Name;

        @SuppressWarnings("CanBeFinal")
        @Option("t")
        @SuitParser(ParserClass = Integer.class, MethodName = "parseInt")
        @WithDefault
        public int SleepTime=0;
        @Switch("s")
        public boolean isSleeping;
    }

    public static class GoodMorningParameter implements DynamicParameter{
        public String name="foo";

        /**
         * Parse this Parameter from String[].
         *
         * @param options String[] to parse from.
         * @return Whether the parsing is successful
         */
        @Override
        public boolean Parse(String[] options)
        {
            if(options.length==1){
                name=options[0];
                return true;
            }else return options.length==0;

        }
    }
}
```