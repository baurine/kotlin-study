# Kotlin Offical Document Note - Part 1

Note for [Kotlin Offical Document](https://www.kotlincn.net/docs/reference/) Part 1.

(失策，不应该一开始看参考的，这个应该是写代码时不知该怎么用时才看的...)

Kotlin 基于 Java，语法和 Swift 又那么相似，所以，快速了解就行，等到用的时候自然会上手。

各种语言的语法都大同小异，重要是去了解它们与其它语言所与众不同的地方。

此笔记对应的 kotlin 是一个早期版本，现在可能有一些变化了，所以笔记中的内容有可能 outdate 了。

## 基础

### 数据类型

**整数**

毫无疑问，包括整数与浮点数。

- Double (64)
- Float (32)
- Long (64)
- Int (32)
- Short (16)
- Byte (8)

> 在 Java 平台数字是物理存储为 JVM 的原生类型，除非我们需要一个可空的引用 (如 Int?) 或泛型。后者情况下会把数字装箱。

用 `==` 判断两个对象的值是否相等，用 `===` 判断两个对象是不是同一个对象。

    val a: Int = 10000
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    print(boxedA == anotherBoxedA)  // true
    print(boxedA === anotherBoxedA) // false

用 var 声明变量，用 val 声明只读的变量。

**显式转换**

较小的类型不能隐式地转换成较大的类型，此时，要进行显式转换。

    val b: Byte = 1        // ok
    val i: Int = b         // wrong!
    val i: Int = b.toInt() // ok

类似的转换还有，toByte(), toShort() ... 详略。

**运算**

位运算居然没有特殊字符来表示，神奇，为什么不能用 Java 的 `>>` `<<` 等?

位运算：shl(bits), shr(bits), ushr(bits), and(bits), or(bits), xor(bits), inv()

**字符**

单个字符用 Char 类型表示，用单引号 '' 表示。

多个字符用 String 类型表示，用双引号 "" 表示。

Char 类型有 toInt() 方法，可以转成整数。

**布尔值**

true/false

**数组**

`Array<T>` 类型，具有 get() 和 set() 方法。

初始化：

    val arr = arrayOf(1,2,3) // [1,2,3]
    val asc = Array(3, {i -> (i*i).toString()})  // ["0", "1", "2"]

Kotlin 还有无装箱开销的数组类来表示原生类型数组：ByteArray, ShortArray, IntArray 等，它们并非继承自 `Array<T>`，但有相同的方法集。

    val x: IntArray = intArrayOf(1,2,3)
    x[0] = x[1] + x[2]

**字符串**

String 类型，不可变，元素可以用下标访问 - s[i]，可以用 for...in 循环访问。

    for (c in str) { ... }

字面值，两种表达方式，双引号，里面有转义字符，三引号，原生字符串，内部没有转义。和其它语言类似，不详述了。

字符串模板，即在字符串在插值，和 JavaScript / Ruby 类似。

    val i = 10
    val s = "i = $i"  // i = 10

    val s = "abc"
    val str = "$s.length is ${s.length}"  // abc.length is 3

### 包

    package foo.bar

声明此源文件的包名，其实就是命名空间啦。然后这个源文件中的所有内容都包含在这个包名之内。

用 import 导入其它包。有多个 Kotlin 的包会默认导入到每个 Kotlin 文件中。

### 控制流 - if / when / for / while

**if**

Kotlin 的 if 有点特别呀，吸收了 Ruby 的特性，比如表达式可以不用 {}，最后一行做为返回值，可以写在一行。if 可以替代三目表达式。

    // 传统用法
    var max = a
    if (a<b) max = b

    // with else
    var max: Int
    if (a>b) {
      max = a
    } else {
      max = b
    }

    // 作用表达式
    val max = if (a>b) a else b
    // 还是觉得三目表达式好用，那 Kotlin 还支持三目表达式吗?

if 的分支可以是代码块，最后的表达式作为该块的值。

    val max = if (a>b) {
      print("Choose a")
      a
    } else {
      print("Choose b")
      b
    }

**when**

用来取代 switch...case，很多种灵活的用法，详略。

    when (x) {
      1 -> print("x == 1")
      2 -> print("x == 2")
      else -> {
        print("x is neither 1 nor 2")
      }
    }

when 也可以用来取代 if...else if...else 链，此时 when 后面没有参数。(Go 的 switch 也可以)

    when {
      x.isOdd() -> print("x is odd")
      x.isEven() -> print("x is even")
      else -> print("x is funny")
    }

**for**

for...in 对集合进行迭代访问。

**while**

while, do...while，略。

### 返回与跳转

return / break / continue

Kotlin 还支持类似 goto 的用法，即在 return / break / continue 后面加上标签值。(为啥要加这种功能呢，好丑，先跳过吧，看到有人用再看)

## 类和对象

### 类和继承

用 class 声明类。

**构造函数**

在 Kotlin 中一个类可以有一个主构造函数和多个次构造函数 (和 Swift 一样)。

主构造函数是类头的一部分，它跟在类名后。

    class Person constructor(firstName: String) {
      ...
    }

如果主构造函数没有任何注解或可见性修饰符，constructor 这个关键词可以省略。

    class Person(firstName: String) {
      ...
    }

主构造函数不能包含任何的逻辑代码，只能有赋值表达式 (嗯，有点特别，但也很好理解)，初始化的代码放到以 init 关键字开始的初始化块 (initializer block) 中，可以用多个 init block，与属性初始化器交织在一起。

    class InitOrderDemo(name: String) {
      val firstProperty = "First property: $name".also(::println)
      init {
        println("First initializer block that prints ${name}")
      }
      val secondProperty = "Second property: ${name.length}".also(::println)
      init {
        println("Second initializer block that prints ${name.length}")
      }
    }

(shit，难怪上面的用法不常见) 事实上，声明属性以及从主构造函数初始化属性，Kotlin 有简洁的语法：

    class Person(val firstName: String, val lastName: String, var age: Int) {
      ...
    }

如果构造函数有注解或可⻅性修饰符，这个 constructor 关键字是必需的，并且这些修饰符在它前面:

    class Customer public @Inject constructor(name: String) { ... }

**次构造函数**

类也可以声明前缀有 constructor 的次构造函数。

    class Person {
      constructor(parent: Person) {
        parent.children.add(this)
      }
    }

如果类有一个主构造函数，每个次构造函数需要委托给主构造函数，可以直接委托或者通过别的次构造函数间接委托。委托到同一个类的另一个构造函数用 this 关键字即可。

    class Person(val name: String) {
      constructor(name: String, parent: Person) : this(name) {
        parent.children.add(this)
      }
    }

这样设计的好处在于，相当于强制在任何构造函数中，首先调用最顶层的构造函数，在 Java 中，你需要手动去完成这个操作，有时你会忘记或者没有把它放在第一个操作。比如：

    class AJavaClassSample extends SampleBase {
        private int c = 0;
        private int d = 0;

        public AJavaClassSample(int a) {
            c = 1;
            super(a);
        }

        public AJavaClassSample(int a, int b) {
            d = 1;
            this(a);
        }
    }

上面的 Java 代码在写法上是没有错的，在这个例子中逻辑也是没有错的，但却是不好的写法，如果 c 和 d 的值依赖于 a 和 b，那逻辑就会出错，而 Kotlin 的写法可以帮助你避免这个逻辑错误，Swift 中如果你没有把 `super()` 或 `this()` 写在第一步，也是会编译错误的。

另外，Kotlin 构造函数的写法相比 Java 也简洁很多，其实可以理解成 Kotlin 把类的声明和主构造函数结合在一起了。

**创建类的实例**

    val customer = Customer("Joe")

Kotlin 不需要 new，也没有 new 关键字。

**类成员**

- 构造函数和初始化块
- 函数
- 属性
- 嵌套类和内部类
- 对象声明

**继承**

共同的超类，Any，Any 不是 java.lang.Object。

    class Any {
      fun equals()
      fun hashCode()
      fun toString()
    }

声明继承的父类：

    open class Base(p: Int)
    class Drived(p: Int) : Base(p)  // 注意 Base 中的参数

如果该类有一个主构造函数，其基类型可以 (且必须) 用基类型的主构造函数就地初始化。

如果没有主构造函数，则在次构造函数中用 super() 初始化其基类型，或用 this() 委托给另一个构造函数。

    class MyView : View {
      constructor(ctx: Context) : super(ctx)
      constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    }

open 与 Java 中的 final 相反，表示允许其它类继承自它，Kotlin 默认所有的类都是 final，与 Java 正好相反。

**方法重载**

Kotlin 是必须用 override 显式声明重载的方法，否则编译通不过。Java 是可选的，且用注解 @override 声明。

如果一个方法用 final 声明，则这个方法禁止被重载。

**属性重载**

也必须用 override 显式声明。可以用 var 来重载父类中的 val 属性，但反之不行。因为 val 属性相当于 get() 方法，用 var 重写相当于增加了 一个 set() 方法。

**访问父类**

在子类中用 super 访问父类。

在内部类中访问外部类的超类，用 `super@Outer`。

    class Bar : Foo() {
      override fun f() { /* ...... */ }
      override val x: Int get() = 0

      inner class Baz {
        fun g() {
          super@Bar.f() // 调用 Foo 实现的 f()
          println(super@Bar.x) // 使用 Foo 实现的 x 的 getter
        }
      }
    }

**覆盖规则**

如果继承自多个父类 (包括接口)，父类中有多个同名的方法，那么子类必须显式地重写此方法，用 `super<A>` `super<B>` 来区分不同的父类。

**抽象类**

abstract 声明。

**伴生对象**

与 Java 或 C# 不同，Kotlin 没有静态方法。(What!?) 它建议简单地使用包级函数。(原来如此)

### 属性和字段

属性的简单声明：

    class Address {
      var name: String = ...
    }

复杂声明：

    var stringRepresentation: String
      get() = this.toString()
      set(value) {
        setDataFromString(value)
      }

在 set() 中用 filed 表示该属性自身：

    var counter = 0 // 此初始器值直接写入到幕后字段
      set(value) {
          if (value >= 0) field = value
      }

或者使用幕后属性 (backing property，另一个私有的内部变量)

    private var _table: Map<String, Int>? = null
    public val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap() // 类型参数已推断出
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }

**编译期常量**

用 const 修饰。

    const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"

**延迟初始化属性与变量**

用 lateinit 修饰符。先跳过。

### 接口

Kotlin 的接口与 Java 8 类似，既包含抽象方法的声明，也包含实现。与抽象类不同的是，接口无法保存状态。它可以有属性但必须声明为抽象或提供访问器实现。

    interface MyInterface {
        val prop: Int  // 抽象的

        val propertyWithImplementation: String
            get() = "foo"

        fun foo() {
            print(prop)
        }
    }

    class Child : MyInterface {
        override val prop: Int = 29
    }

### 可见性修饰符

private, protected, internal, public，默认 public。

internal，相同模块内可见。

### 扩展

为已存在的类扩展方法或属性，Kotlin 采用了 C# 的语法。

扩展函数：

    fun MutableList<Int>.swap(index1: Int, index2: Int) {
        val tmp = this[index1] // this 对应该列表
        this[index1] = this[index2]
        this[index2] = tmp
    }

`MutableList<Int>` 为接收者类型，也就是被扩展的类型。

注意，扩展是静态解析的，扩展的函数，并没有成为类的成员方法，上例中，实际它不过是声明了一个 swap() 函数，它隐含的第一个参数是一个 `MutableList<Int>` 的对象。它没有多态的功能。

(有点明白了 Go 的为结构体扩展方法的设计理念了，它实际是统一把成员方法也作为扩展方法处理了)

**可空接收者**

(Go 也可以)

    fun Any?.toString(): String {
        if (this == null) return "null"
        // 空检测之后，"this" 会自动转换为非空类型，所以下面的 toString()
        // 解析为 Any 类的成员函数
        return toString()
    }

**扩展属性**

    val <T> List<T>.lastIndex: Int
        get() = size - 1

**伴生对象的扩展**

    class MyClass {
        companion object { }  // 将被称为 "Companion"
    }

    fun MyClass.Companion.foo() {
        // ...
    }

使用：

    MyClass.foo()

**扩展声明为成员**

可以把一个类 (A) 的扩展方法声明在另一个类 (B) 中，并成为这个类 (B) 的成员方法，并且这个成员方法可以被子类继承，重写。

    class D {
        fun bar() { ... }
    }

    class C {
        fun baz() { ... }

        // D.foo() 可以被 C 的子类继承，重写
        fun D.foo() {
            bar()   // 调用 D.bar
            baz()   // 调用 C.baz
        }

        fun caller(d: D) {
            d.foo()   // 调用扩展函数
        }
    }

### 数据类

(我觉得是对应 Java 中的 JaveBean)

    data class User(val name: String, val age: Int)

详略，需要时再看。

### 密封类

没明白，先跳过。

### 泛型

`Type<T>`，通过在 T 前使用 in 或 out，替代在 Java 中使用 `<? super T>` 和 `<? extends T>`。

没有完全理解，先跳过。

### 嵌套类和内部类

嵌套类，不可以访问外部类，相当于是 Java 中的内部静态类。

内部类，用 inner 修饰，可以访问外部类。

匿名内部类，和 Java 中一样，略。

### 枚举类

枚举不是一个单独的类型，而是用 enum 修饰的类。

    enum class Direction {
        NORTH, SOUTH, WEST, EAST
    }

    enum class Color(val rgb: Int) {
        RED(0xFF0000),
        GREEN(0x00FF00),
        BLUE(0x0000FF)
    }

详略。
