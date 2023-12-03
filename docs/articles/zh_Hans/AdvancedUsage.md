---
title: PlasticMetal.JMobileSuitLite的常用特性
date: 2020-04-19 12:45:11
---

## 避免一些公有成员的显示

客户端类里，总会有一些公有成员, 但是如果你不希望其中的一些在MobileSuit中被显示，可以向它们添加 ***@SuitIgnore*** 注解.

## 多行输入

MobileSuit的语句可以以多行被输入，但是解析为一行。只需要在非末行的行尾添加 *%*
例如:

``` MobileSuitScript
he%
llo
```

等价于

``` MobileSuitScript
hello
```

## 自定义Prompt Server和Powerline主题的 Prompt Server

你可以自定义作为 Prompt Server的类, 他们需要实现接口 ***PlasticMetal.JMobileSuitLite.IO.PromptServer***. 当然, 也可以直接继承类 ***PlasticMetal.JMobileSuitLite.IO.CommonPromptServer***.

使用你的Prompt Server来初始化 ***SuitConfiguration***, 然后使用这个配置去初始化 ***SuitHost***.

一个自定义Prompt Server主题: Powerline 被内构于包 *PlasticMetal.JMobileSuitLite* .

你可以像下面这样使用它:

``` java
new SuitHost(Client.class, 
                PowerLineThemedPromptServer.getPowerLineThemeConfiguration()).Run();
```

如果出现乱码, 请确保你已经安装并启用[Powerline字体](https://github.com/powerline/fonts).