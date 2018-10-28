# Kotlin Offical Document Note - Part 2

Note for [Kotlin Offical Document](https://www.kotlincn.net/docs/reference/) Part 2.

## 类和对象

### 对象表达式和对象声明

对象表达式，匿名内部类的进一步使用说明。

    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            // ...
        }

        override fun mouseEntered(e: MouseEvent) {
            // ...
        }
    })

**对象声明**

直接声明一个单例对象，用 object 修饰符。

    object DataProviderManager {
        fun registerDataProvider(provider: DataProvider) {
            // ...
        }

        val allDataProviders: Collection<DataProvider>
            get() = // ...
    }

使用：

    DataProviderManager.registerDataProvider(...)

**伴生对象**

类内部的对象声明可以用 companion 关键字标记：

    class MyClass {
        companion object Factory {
            fun create(): MyClass = MyClass()
        }
    }

伴生对象的成员可以通过类名来调用：

    val instance = MyClass.create()

可以省略伴生对象的名字，此时它的名字默认为 Companion (那就只能有一个省略名字的伴生对象喽?)

    class MyClass {
        companion object {
        }
    }

    val x = MyClass.Companion

虽然看着很像是静态成员，但实际在运行时它们仍然是真实对象的实例成员。它们还可以实现接口。详略。

对象表达式和对象声明之间有一个重要的语义差别：

- 对象表达式是在使用他们的地方立即执行 (及初始化) 的；
- 对象声明是在第一次被访问到时延迟初始化的；
- 伴生对象的初始化是在相应的类被加载 (解析) 时，与 Java 静态初始化器的语义相匹配。

### 委托

(明白)

委托，用组合来替代继承，将实际功能转发给另一个对象来完成。在其它语言中实现委托会产生很多样板代码，而 Kotlin 用一个关键字 by 来消除这些样析代码。

示例： 类 Derived 可以继承一个接口 Base，并将其所有共有的方法委托给一个指定的对象。

    interface Base {
        fun print()
    }

    class BaseImpl(val x: Int) : Base {
        override fun print() { print(x) }
    }

    class Derived(b: Base) : Base by b

    fun main(args: Array<String>) {
        val b = BaseImpl(10)
        Derived(b).print() // 输出 10
    }

Derived 的超类型列表中的 by-子句表示 b 将会在 Derived 中内部存储。 并且编译器将生成转发给 b 的所有 Base 的方法。

(实际是 Kotlin 帮你自动在 Drived 类中生成了 `override fun print() { b.print() }` 方法。)

### 委托属性

呃，有一点复杂。先跳过吧。

总的思想是说，可以把一个属性的 get() 和 set() 方法委托由另一个对象来实现。并且库提供了一些默认的属性委托对象，比如 lazy，observable ... lazy 委托可以让你的初始化延迟到第一次访问，observable 可以观察一个属性旧值和新值的对比。

以及讲了如何自己实现一个类似 lazy, observable 之类的委托。

## 函数与 lambda 表达式

### 函数

函数声明：

    fun double(x: Int): Int {
        return 2*2
    }

用 fun 关键字声明，参数和类型采用 Pascal 定义法。

默认参数，命名参数，略。

单表达式函数：

    fun double(x: Int): Int = x*2

当函数返回值可以推导时，可以省略：

    fun double(x: Int) = x*2

可变数量的参数，用 vararg 修饰，只能是最后一个，用 Array 接收。

将一个数组对象展开，用展开操作符 `*`。

**中缀表示法**

    // 给 Int 定义扩展
    infix fun Int.shl(x: Int): Int {
        ...
    }

    // 用中缀表示法调用扩展函数
    1 shl 2

    // 等同于这样
    1.shl(2)

- 成员函数或扩展函数
- 只有一个参数
- 用 infix 标注

**函数作用域**

Java / C# 中所有函数必须定义在类中，而 Kotlin 可以把函数定义在文件顶层，类之外。(所以，这就是为什么 Kotlin 不需要静态成员方法)

局部函数，闭包，略。成员函数，略。泛型函数，略。内联函数，略。扩展函数，略。

高阶函数和 lambda 表达式，后面会讲。

尾递归函数，用 tailrec 声明，JVM 会做优化。

### 高阶函数和 lambda 表达式

高阶函数是将函数用作参数或返回值的函数。

map 的例子：

    fun <T, R> List<T>.map(transform: (T) -> R): List<R> {
        val result = arrayListOf<R>()
        for (item in this)
            result.add(transform(item))
        return result
    }

调用：

    val doubled = ints.map { value -> value * 2 }

如果 lambda 只有一个参数，则可以省略 `->` 和前面的内容，变量的隐式名称为 it。

    val doubled = ints.map { it*2 }
    strings.filter { it.length == 5 }.sortedBy { it }.map { it.toUpperCase() }

(Swift 中隐式使用 $0, $1 ...)

未使用的变量用 `_` 声明 (在种语言中已形成共识)

**匿名函数**

?? 呃，lambda 不是匿名函数呀... 有点矇了。

所以，从这里的意思是说，不能在 lambda 中表达复杂的逻辑，只能有一个表达式，不能用 return，如果要在其中使用 return，那么其实就变成了匿名函数... (和 Python 一样)

先跳过吧，需要时再回来看。

### 内联函数

> 使用高阶函数会带来一些运行时的效率损失：每一个函数都是一个对象，并且会捕获一个闭包。即那些在函数体内会访问到的变量。内存分配 (对于函数对象和类) 和虚拟调用会引入运行时间开销。

> 但是在许多情况下通过内联化 lambda 表达式可以消除这类的开销。

理解，就跟 C++ 的内联一个道理。通过内联后，不再把 lambda 生成对象，而是直接把它的实现插入到方法中。增大了代码体积，提高了性能...

用 inline 声明一个高阶函数，使参数中的函数内联。

详略。

### 协程 (coroutine)

跟其它语方 (JavaScript, Go, Python) 的协程理念差不多，先略过吧。

## 其它

### 解构声明

    val (name, age) = person

略。ES6 有相同的用法。

### 集合 - List / Set / Map

略。有可变与不可变集合。

### 区间

`..` 与 `...`

一些函数：rangeTo(), downTo(), reversed(), step()

判断是否在区间：in, !in

区间的迭代：for...in

### 类型检查与转换

- is, !is
- as, as?

### This 表达式

略。在不同的作用域表示不同的对象

### 相等性

- 引用相等，两个引用指向同一对象，用 `===`
- 结构相等，用 equals() 判断，用 `==`

### 操作符重载

略。

### 空安全

和 Swift 差不多，略过了。Kotlin 用 `?:` 替代 Swift 中的 `??`

### 异常

try...catch...finally, try...catch 可以作为表达式返回值。

throw

Nothing 表示永远不会返回的函数。

其余略。

### 注解

略，Java 的注解还没完全搞懂呢。

### 反射

用 `::` 符号，取得类引用、函数引用、属性引用。

    fun isOdd(x: Int) = x % 2 != 0
    val numbers = listOf(1, 2, 3)
    println(numbers.filter(::isOdd)) // 输出 [1, 3]

### 类型安全的构建器

    import com.example.html.* // 参见下文声明

    fun result(args: Array<String>) =
        html {
            head {
                title {+"XML encoding with Kotlin"}
            }
            body {
                h1 {+"XML encoding with Kotlin"}
                p  {+"this format can be used as an alternative markup to XML"}

                // 一个具有属性和文本内容的元素
                a(href = "http://kotlinlang.org") {+"Kotlin"}

                // 混合的内容
                p {
                    +"This is some"
                    b {+"mixed"}
                    +"text. For more see the"
                    a(href = "http://kotlinlang.org") {+"Kotlin"}
                    +"project"
                }
                p {+"some text"}

                // 以下代码生成的内容
                p {
                    for (arg in args)
                        +arg
                }
            }
        }

html / head / body 其实就是函数。(和 React JSX 的写法差不多，实际在 JSX 中，每个标签就是一个函数)

### 类型别名

typealias

### 多平台项目

略。

## Java 互操作

略。
