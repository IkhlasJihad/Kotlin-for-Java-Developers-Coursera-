package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    if(secret == guess)
        return Evaluation(rightPosition = 4, wrongPosition = 0)
    var right: Int = 0
    var wrong: Int = 0
    val unMatchedSecret = secret.toCharArray().toMutableList()
    // count right positions
    for (i in 0 until CODE_LENGTH){
        if(guess[i] == secret[i]) {
            right++
            unMatchedSecret[i] = '-'
        }
    }
    // count guess-letters in wrong positions
    for (i in 0 until CODE_LENGTH){
        val guessLetter : Char = guess[i]
        if (guessLetter != secret[i]){
            val indexInSecret = unMatchedSecret.indexOf(guessLetter)
            // if the letter exists in secret but in different location, add it to wrong counter
            if (indexInSecret != -1 && guessLetter != guess[indexInSecret]){
                wrong++
                // clear it to prevent repeated consideration
                unMatchedSecret[indexInSecret] = '-'
            }
        }
    }
    return Evaluation(right, wrong)
}
