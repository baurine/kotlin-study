fun main(args: Array<String>) {
    testLambdas()
}

fun testLambdas() {
    {dirty: Int -> println("${dirty/2}")}(10)

    val waterFilter = {dirty: Int -> dirty/2}
    println(waterFilter(20))

    val myFish = listOf(Fish("Flipper"), Fish("Moby Dick"), Fish("Dory"))
    val names = myFish.filter { it.name.contains("i") }.joinToString(" ") { it.name }
    println(names)
}

data class Fish(val name: String)