package rationals

import java.math.BigInteger

data class Rational(val n: BigInteger, val d: BigInteger): Comparable<Rational>{
    
    operator fun plus(that: Rational): Rational =
        (n.times(that.d).plus(d.times(that.n))).divBy(d.times(that.d))    

    operator fun minus(that: Rational): Rational =
         (n.times(that.d).minus(d.times(that.n))).divBy(d.times(that.d))
    
    operator fun times(that: Rational): Rational =
        (n.times(that.n)).divBy(d.times(that.d))
    
    operator fun div(that: Rational): Rational =
        (n.times(that.d)).divBy(d.times(that.n))
    
    operator fun unaryMinus(): Rational = Rational(n.negate(), d)
    
    override fun compareTo(other: Rational): Int =
        n.times(other.d).compareTo(d.times(other.n))

    override fun toString(): String {
        return when{
            d == BigInteger.ONE -> n.toString()
            n.rem(d) == BigInteger.ZERO -> n.div(d).toString()
            else -> {
                val r = simplifyRational(this)
                //consider the negative (if d, or both are negatives)
                if (r.d < BigInteger.ZERO ||
                    (r.n < BigInteger.ZERO && r.d < BigInteger.ZERO) ){
                    stringifyRational(Rational(r.n.negate(), r.d.negate()))
                } else
                    stringifyRational(r)
            }
        }
    }
    
    private fun stringifyRational(r: Rational): String = 
        "${r.n.toString()}/${r.d.toString()}"
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        other as Rational
        
        val thisSimplified = simplifyRational(this)
        val thatSimplified = simplifyRational(other)
        return thisSimplified.n.toDouble().div(thisSimplified.d.toDouble()) ==
                thatSimplified.n.toDouble().div(thatSimplified.d.toDouble())
    }
    
}

// Extension functions 
infix fun Int.divBy(that: Int): Rational =
    Rational(toBigInteger(), that.toBigInteger())

infix fun Long.divBy(that: Long) : Rational =
    Rational(toBigInteger(), that.toBigInteger())

infix fun BigInteger.divBy(that: BigInteger) : Rational =
    Rational(this, that)

fun String.toRational(): Rational {
    val splittedNum = split("/")
    return when{
        splittedNum.size == 1 -> Rational(splittedNum[0].toBigInteger(), BigInteger.ONE)
        else -> Rational(splittedNum[0].toBigInteger(), splittedNum[1].toBigInteger())
    }
}

// GCD function
fun gcd(x: BigInteger, y: BigInteger): BigInteger {
    if(y == BigInteger.ZERO) 
        return x
    return gcd(y, x % y)
}
// write rational in this simplest form using GCD(n,d)
fun simplifyRational(r: Rational): Rational{
    val gcd = gcd(r.n, r.d)
    return Rational(r.n.div(gcd), r.d.div(gcd))
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}