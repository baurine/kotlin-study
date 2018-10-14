package Aquarium

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

fun fishExample() {
    val fish = Fish(false, 20)
    println("Is the fish friendly? ${fish.friendly}. It needs volume ${fish.size}")

    val fish2 = Fish()
    println("Is the fish friendly? ${fish2.friendly}. It needs volume ${fish2.size}")

    val fish3 = makeDefaultFish()
    println("Is the fish friendly? ${fish3.friendly}. It needs volume ${fish3.size}")
}
