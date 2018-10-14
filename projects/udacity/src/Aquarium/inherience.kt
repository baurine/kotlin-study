package Aquarium

fun main(args: Array<String>) {
    delegate()
}

fun delegate() {
    val pleco = Plecostomus(RedColor)
    println("Fish has color ${pleco.color}")
    pleco.eat()
}

interface FishAction {
    fun eat()
}

interface FishColor {
    val color: String
}

class Plecostomus(fishColor: FishColor = GoldColor):
        FishAction by PrinttingFishAction("a lot of algae"),
        FishColor by fishColor

object GoldColor: FishColor {
    override var color = "gold"
}

object RedColor: FishColor {
    override var color = "red"
}

class PrinttingFishAction(val food: String): FishAction {
    override fun eat() {
        println(food)
    }
}
