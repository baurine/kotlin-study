import java.util.*

fun main(args: Array<String>) {
    println("hello ${args[0]}!")
    feedFishFood()
}

fun feedFishFood() {
    val day = randomDay()
    val food = fishFood(day)
    println("Today is $day and the fish eat $food")

    if (shouldChangeWater(day)) {
        println("please change the water!")
    }
}

fun randomDay(): String {
    val weekDays = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    return weekDays[Random().nextInt(7)]
}

fun fishFood(day: String): String {
    return when(day) {
        "Monday" -> "flakes"
        "Wednesday" -> "red-worms"
        "Thursday" -> "granules"
        else -> "fasting"
    }
}

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
