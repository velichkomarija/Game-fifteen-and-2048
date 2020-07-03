package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val reordering = permutation.toMutableList()
    var numberOfPermutations = 0
    val ordered = (permutation.min()!!..permutation.max()!!).toList()
    for (expectedIndex in 0 until ordered.size) {
        val searchedValue = ordered[expectedIndex]
        if (searchedValue != reordering[expectedIndex]) {
            val currentIndex = reordering.indexOf(searchedValue)
            numberOfPermutations++
            swap(reordering, expectedIndex, currentIndex)
        }
    }
    return numberOfPermutations % 2 == 0
}

fun swap(arrays: MutableList<Int>, i: Int, j: Int) {
    val old = arrays[i]
    arrays[i] = arrays[j]
    arrays[j] = old
}