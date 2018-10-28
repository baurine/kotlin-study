# Udacity Course Note

## 资源

- [Kotlin Bootcamp for Programmers](https://classroom.udacity.com/courses/ud9011)

## Note

### lesson 1 - Introduction

安装 JDK

- JRE (java runtime environment)
- JDK (java develop kit)

JDK 包含 JRE。

APP 执行只需要 JRE，即普通用户只需要 JRE。但开发人员需要调用 API 来实现功能，而 API 都在 JDK 中。

### lesson 2~3 - Kotlin Basics/Functions

when 可以替代 if / switch case

    fun fishFood(day: String): String {
        return when(day) {
            "Monday" -> "flakes"
            "Wednesday" -> "red-worms"
            "Thursday" -> "granules"
            else -> "fasting"
        }
    }

compact function: `fun isDirty(dirty: Int) = dirty > 50`

    fun shouldChangeWater(day: String, temperature: Int = 22, dirty: Int = 50): Boolean {
        return when {
            isHot(temperature) -> true
            isDirty(dirty) -> true
            isSunday(day) -> true
            else -> false
        }
    }

    fun isHot(temperature: Int) = temperature > 30
    fun isDirty(dirty: Int) = dirty > 50
    fun isSunday(day: String) = day == "Sunday"

### lesson 4 - Classes

    open class Aquarium(var width: Int = 20, var length: Int = 40, var height: Int = 100) {
        open var volume: Int
            get() = width*length*height/1000
            set(value) {
                height = (value*1000)/(width*length)
            }

        open var water = volume * 0.9

        constructor(numberOfFish: Int) : this() {
            val water = numberOfFish * 2000
            val tank = water + water * 0.1
            height = (tank / (length*width)).toInt()
        }
    }

    class TowerTank() : Aquarium() {
        override var water = volume * 0.8

        override var volume: Int
            get() = (width*height*length/1000* PI).toInt()
            set(value) {
                height = (value*1000)/(width*length)
            }
    }

构造函数中的参数如果没有用 val 或 var 声明，则不会创建 class 的 属性，比如上例中，`constructor(numberOfFish: Int)` 中的这个 numberOfFish 就不会成为 Aquarium 的属性，而 `Aquarium(var width: Int = 20, var length: Int = 40, var height: Int = 100)` 中的 width, length, height 会成为 Aquarium 的属性。

kotlin 中生成实例不需要 new 关键字：

    val myAquarium = Aquarium()

init / help function

一般情况下只需要一个 construtor，辅以多个 init block (此例中有两个 constructor)。`makeDefaultFish` 是 help function。

    class Fish(val friendly:Boolean = true, volumeNeeded: Int) {
        val size: Int

        init {
            println("first init block")
        }

        constructor():this(true, 9){
            println("running secondary constructor")
        }

        init {
            if (friendly) {
                size = volumeNeeded
            } else {
                size = volumeNeeded * 2
            }
        }

        init {
            println("constructed fish of size $volumeNeeded final size $size")
        }

        init {
            println("last init block")
        }
    }

    fun makeDefaultFish() = Fish(true, 50)

- 抽象类：可以有构造函数
- 接口：没有构造函数，可以有方法的默认实现

多用组合，DI

object，实例是单例对象

data class，类似 c++ 中的结构体

enum class，枚举类

sealed class，只能在同一文件中被继承

### lesson 5 - Kotlin Essentials: Beyond the Basics

pair，用 "to" 定义。triple，用 Triple() 定义。

    fun testPairTriple() {
        val book = Book("Game of Throne", "Matin", "1995")
        println(book.titleAndAuthor)
        println(book.titleAuthorYear)
        val (title, author, year) = book.titleAuthorYear
        println("Here is your book $title written by $author in $year")

    }

    class Book(val title: String, val author: String, val year: String) {
        //val titleAndAuthor = "$title" to "$author"
        val titleAndAuthor = (title to author)
        val titleAuthorYear = Triple(title, author, year)
    }

collections:

- 不可变：setOf, listOf, mapOf ...
- 可变：mutableListOf ...

const 与 val

- const 编译期
- val 运行时

top object 与 companion object

- top object，常规 object，初次使用时初始化
- companion object，与对象一起初始化

extension，awesome!

generic class

generic in and out, in as parameter, out as return (这一点不是特明白)

generic function

flow, @loop 的使用

### lesson 6 - Functional Manipulation

Lambda：`{println("hello")}()`，包围在 `{}` 中

    {dirty: Int -> println("${dirty/2}")}(10)

相比 JS 的写法还简单：

    (function(dirty) {console.log(dirty/2)})(10)

可以将 lambda 赋值给变量：

    val waterFilter = { dirty: Int -> dirty / 2}
    val waterFilter: (Int) -> Int = {dirty -> dirty /2 }

注意，lambda 和 fun 不一样，下面的定义是 compact fun：

    fun waterFilterFun(dirty: Int) = drity / 2

在 fun 前面加上 `::` 转换成 lambda：

    fun updateFilter(dirty: Int, operation: (Int) -> Int): Int {
      return operation(dirty)
    }

    updateFilter(dirty, waterFilter)
    updateFilter(dirty, ::waterFilterFun)

with()，改变 this：

    with(fish.name) {
      capitalize()
    }

还有几个类似的用法：run, apply, let ... 有待进一步探索。

inline

SAM: Single Abstract Method，只有一个方法的接口，可以转换成 Lambda。

    interface Runnable {
      fun run()
    }
