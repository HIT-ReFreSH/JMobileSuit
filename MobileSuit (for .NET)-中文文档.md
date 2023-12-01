欢迎来到PlasticMetal.MobileSuit使用文档

在本页，您将了解如何使用JMobileSuitLite来快速构建一个.NET Core控制台应用程序。

在开始前，请确保您已经了解C#或是VB语言的基本内容。

[创建你的项目](###创建你的项目)

[编写 MobileSuit 客户端类](###编写 MobileSuit 客户端类)

[更新 Program 类 ](###更新 Program 类)

[运行并测试你的应用](###运行并测试你的应用)



### 创建你的项目

首先，在[Visual Studio 中创建一个新的控制台应用程序](https://learn.microsoft.com/en-us/visualstudio/get-started/csharp/tutorial-console?view=vs-2019)。语言（ Visual Basic或C# ）并不重要，但以下示例将使用 C# 编写。

然后将 [PlasticMetal.MobileSuit](https://www.nuget.org/packages/PlasticMetal.MobileSuit/) 添加到你的项目中（[如何添加？](https://learn.microsoft.com/en-us/nuget/quickstart/install-and-use-a-package-in-visual-studio)）。



### 编写 MobileSuit 客户端类

#### 创建类

向项目添加一个新类，命名为 **_Client_** 。它继承 **_PlasticMetal.MobileSuit.ObjectModel.SuitClient_** 。 

#### 添加第一条命令

向 **_Client_** 类添加一个名为 **_Hello_** 的方法。它没有参数，也没有返回值。

方法的内容可以是任意的，在客户端类中你可以使用 _IO.WriteLine_ 和 _IO.ReadLine_ 代替_Console.WriteLine_和 _Console.ReadLine_ 来完成 IO 操作。

#### 为第一条命令添加信息和别名

为该方法添加以下自定义属性：

1. _PlasticMetal.MobileSuit.ObjectModel.Attributes.SuitInfo_ ，参数为 “hello command.”
2. _PlasticMetal.MobileSuit.ObjectModel.Attributes.SuitAlias_ ，参数为 “H”

#### 添加另一条命令

向 **_Client_** 类添加一个名为 **_Bye_** 的方法。它有一个字符串参数，命名为 _name_ 。它返回一个字符串。

方法的内容可以是任意的，你可以使用 _Io.WriteLine_ 和 _Io.ReadLine_ 代替 _Console.WriteLine_ 和 _Console.ReadLine_ 来完成IO操作。

#### 检查你在 Client.cs 中的代码

它的内容大概是：

```c#
using PlasticMetal.MobileSuit.ObjectModel;
using PlasticMetal.MobileSuit.ObjectModel.Attributes;

namespace MobileSuitDemo
{
	public class Client : SuitClient
	{
		[SuitAlias("H")]
		[SuitInfo("hello command.")]
		public void Hello()
		{
			IO.WriteLine("Hello! MobileSuit!");
		}
		
		public string Bye(string name)
		{
			IO.WriteLine($"Bye! {name}");
			return "bye";
		}
	}
}
```



### 更新 **_Program_** 类

将以下代码添加到 **_Main()_** 方法中：

```c#
new PlasticMetal.MobileSuit.SuitHost(new Client()).Run();
```



### 运行并测试你的应用

构建并运行你的应用程序。

在控制台中，你可以输入：
1. **Help** 查看 **MobileSuit** 的帮助
2. **List** 或 **ls** 查看当前客户端可用的所有命令
3. **Hello** 或 **h** 运行 Client.Hello()
4. **Bye name** 运行 Client.Bye( **name** )
5. **Exit** 退出程序

构建这样一个应用的过程非常简单，你只需要编写一个类。