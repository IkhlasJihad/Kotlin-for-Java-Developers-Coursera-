package games.gameOfFifteen

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val numbers = (1..15).toList()
        val randomPermutation = numbers.shuffled().toMutableList()
        if (!isEven(randomPermutation)) {
            swapTwoElements(randomPermutation)
        }
        randomPermutation
    }
    fun swapTwoElements(permutation: MutableList<Int>) {
        // Swap the first two elements
        val temp = permutation[0]
        permutation[0] = permutation[1]
        permutation[1] = temp
    }
}

