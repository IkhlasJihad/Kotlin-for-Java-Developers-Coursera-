package nicestring
// extension function
fun String.isNice(): Boolean {
    fun isVowel(c: Char): Boolean = c in setOf('a','o','u','e','i')
    
    fun hasThreeVowelsOrMore(): Boolean = toCharArray().count(::isVowel) >= 3
    
    fun containSubstring(): Boolean {
        val subStringsList: List<String> = listOf("bu", "ba", "be")
        subStringsList.forEach { it -> if (contains(it)) return true }
        return false // s has none of the subStrings
    } 
    
    fun hasConsecutiveLetter(): Boolean{
        toCharArray().forEachIndexed { index, value ->
            if (index + 1 == length ) // last element
                return false
            if (value == this[index + 1]) 
                return true
        }
        return false // empty string
    }
    
    return listOf(
        !containSubstring(), 
        hasThreeVowelsOrMore(), 
        hasConsecutiveLetter()
    ).map { it -> if (it) 1 else 0 }.sum() >= 2 // at least two are satisfied
}