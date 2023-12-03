---
title: 将PlasticMetal.JMobileSuitLite命令打包为脚本文件来实现工作流程自动化
date: 2020-04-11 12:45:11
---

**该特性在 JMobileSuitLite 0.1.2 或更高版本可用**

在本节, 你不再需要编写Java代码， 而是需要编写一个包含你应用里各种命令的脚本文件.

## 创建脚本文件

创建一个文本文件. 文件名可以是任意的, 但是我们建议使用 ".mss" 作为扩展名，意为MobileSuit脚本(MobileSuit Script).

使用你应用里的命令填充脚本。**对于 JMobileSuitLite 0.1.2.2 或更高版本**, 你在 **行首** 添加 *'#'* 来创建行注释，它们不会被JMobileSuit解析. 

这些命令可能包含非法命令，或者是不存在的命令。然而, 当JMobileSuit执行到包含这种命令的行时，它会停下来并返回错误。该行之后的命令 **不会** 被执行.

例如:

``` MobileSuitScript
#不执行
#hello
hello
bye foo
#在下一行停止：不存在的命令
@hello
#尽管下一行合法，但是不会被执行
@ls
```

## 在应用中运行脚本文件

使用 *RunScript* 或者 *Rs* 命令来运行脚本文件。参数是脚本文件的(绝对或者相对)路径.

脚本文件可以包含 *RunScript* 或 *Rs* 命令, 所以请小心堆栈溢出的发生。.

**This feature only works on JMobileSuitLite 0.1.2 or later version**

Pack-up your commands into a script file can make workflow automatically.

In this part, you need no Java codes to write. In stead, you should write a script file contains commands of your application.

