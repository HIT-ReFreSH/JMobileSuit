---
title: 快速开始 - PlasticMetal.JMobileSuitLite
date: 2020-04-11 12:45:11
---


PlasticMetal.MobileSuit 是一个用来快速构造 .NET Core控制台程序的强大工具。现在，它的轻量化版本被移植到Java平台上来了，这就是JMobileSuitLite.

## 创建项目

第一步，你需要在IDE中新建一个Java项目。


然后，添加 [PlasticMetal.JMobileSuitLite](https://search.maven.org/artifact/io.github.plastic-metal/JMobileSuitLite) 到你的项目。

## 编写客户端类

### 创建类

向项目添加一个新类, 名为 ***Client***. 它继承自 ***PlasticMetal.JMobileSuitLite.ObjectModel.SuitClient***.

### 为类添加自定义标记:

*PlasticMetal.JMobileSuitLite.ObjectModel.Annotions..SuitInfo* 使用参数 "Demo"

### 添加第一条指令

向 ***Client*** 类添加方法，名为 ***Hello***. 它没有参数，也没有返回值.

方法的内容可以是任意的，它将在你输入Hello命令时被执行。在客户端类中，你可以使用 *IO().WriteLine* 和 *IO().ReadLine* 来代替 *System.out.println* 和 *Scanner::nextLine* 来完成IO操作.

### 向命令添加说明和别名

向方法添加以下自定义标记:

1. *PlasticMetal.JMobileSuitLite.ObjectModel.Annotions..SuitInfo* 使用参数 "hello command."
2. *PlasticMetal.JMobileSuitLite.ObjectModel.Annotions..SuitAlias* 使用参数 "H"

### A添加另一条命令

向***Client*** 类添加方法，名为 ***Bye*** . 它有一个字符串参数, 名为 *name*. 它返回一个 *string*.

方法的内容可以是任意的，它将在你输入Hello命令时被执行。在客户端类中，你可以使用 *IO().WriteLine* 和 *IO().ReadLine* 来代替 *System.out.println* 和 *Scanner::nextLine* 来完成IO操作.

### 向应用添加主函数

添加主函数

``` java
public static void main(String[] args) throws Exception{

}
```

到这个类中, 然后添加如下代码到 ***main*** 中:

``` java
new SuitHost(Client.class).Run();
```

### 检查您在 Client.java 中的代码

它的内容大概是:

``` java
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitAlias;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitInfo;
import PlasticMetal.JMobileSuitLite.ObjectModel.SuitClient;
import PlasticMetal.JMobileSuitLite.SuitHost;

@SuitInfo("Demo")
public class Client extends SuitClient
{
    @SuitAlias("H")
    @SuitInfo("hello command")
    public void Hello(){
        IO().WriteLine("Hello! MobileSuit!");
    }

    public String Bye(String name)
    {
        IO().WriteLine("Bye!"+name);
        return "bye";
    }

    public static void main(String[] args) throws Exception
    {
        new SuitHost(Client.class).Run();
    }
}

```


## 运行并测试你的应用

编译并运行你的程序.

在控制台中，你可以输入:

1. **Help** 来查看MobileSuit帮助
2. **List** 或 **ls** 来查看对于客户端可用的指令.
3. **Hello** 或 **h** 来调用 *Client.Hello()*
4. **Bye** ***name*** 来调用 *Client.Bye(* ***name*** *)*
5. **Exit** 来退出程序

