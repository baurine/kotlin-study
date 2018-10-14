package Aquarium

fun main(args: Array<String>) {
    buildAquarium()
}

fun buildAquarium() {
    val myAquarium = Aquarium()
    println("volume: ${myAquarium.volume}")

    val myAquarium2 = Aquarium(20, 15, 30)
    println("volume: ${myAquarium2.volume}")

    val myAquarium3 = Aquarium(numberOfFish = 9)
    println("volume: ${myAquarium3.volume}")
}
