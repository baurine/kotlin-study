package Aquarium

fun main(args: Array<String>) {
    makeDecorations()
}

fun makeDecorations() {
    val d1 = Decorations("granite")
    println(d1)

    val d2 = Decorations("slate")
    println(d2)

    val d3 = Decorations("slate")
    println(d3)

    println(d2.equals(d3))

    val d4 = d3.copy()
    println(d4)

    val d5 = Decorations2("crystal", "wood", "diver")
    println(d5)

    val (rocks, diver) = d5
    println(rocks)
    println(diver)  // value is "wood", not "diver"
}

data class Decorations(val rocks: String) {}

data class Decorations2(val rocks: String, val wood: String, val diver: String) {}
