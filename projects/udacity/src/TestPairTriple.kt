fun main(args: Array<String>) {
    testPairTriple()
}

fun testPairTriple() {
    val book = Book("Game of Throne", "Matin", "1995")
    println(book.titleAndAuthor)
    println(book.titleAuthorYear)
    val (title, author, year) = book.titleAuthorYear
    println("Here is your book $title written by $author in $year")

}

class Book(val title: String, val author: String, val year: String) {
//    val titleAndAuthor = "$title" to "$author"
    val titleAndAuthor = (title to author)
    val titleAuthorYear = Triple(title, author, year)
}
