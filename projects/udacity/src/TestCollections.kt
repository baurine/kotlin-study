fun main(args: Array<String>) {
    TestCollections()
}

fun TestCollections() {
    val allBooks = setOf("Macbeth", "Romeo and Juliet", "Hamlet", "A Midsummer Night's Dream")
    val library = mapOf("Shakespeare" to allBooks)
    println(library.any { it.value.contains("Hamlet") })

    var moreBooks = mutableMapOf("Game of Throne" to "Matin")
    moreBooks.getOrPut("Hamlet") { "Shakespeare"}
    println(moreBooks)
}
