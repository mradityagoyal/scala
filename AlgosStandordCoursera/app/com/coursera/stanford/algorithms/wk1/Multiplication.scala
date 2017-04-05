package com.coursera.stanford.algorithms.wk1

object Multiplication {

  def karatsuba_multiply(x: String, y: String): Double = {
    //pad the strings to get equal lengths. 
    val (first, second) = pad(x, y)

    if (first.length() == 1) {
      first.toInt * second.toInt
    } else {
      val (a, b) = split_in_two(first)
      val (c, d) = split_in_two(second)
      val ac = karatsuba_multiply(a, c)
      val bd = karatsuba_multiply(b, d)
      //(a+b)(c+d) 
      val aPlusB = (a.toInt + b.toInt).toString()
      val cPlusD = (c.toInt + d.toInt).toString()
      val abcd = karatsuba_multiply(aPlusB, cPlusD)
      //(a+b)(c+d) - ac - bd 
      val stp4 = abcd - ac - bd
      abcd * Math.pow(10, first.length()) + bd + stp4 * Math.pow(10, first.length() / 2)
    }
  }

  /**
   * @param x
   * @param y
   * @return left pads the smaller string with 0 and returns the tuple.
   */
  def pad(x: String, y: String): (String, String) = {
    var maxLen = Math.max(x.length(), y.length())
    if (maxLen % 2 == 1) {
      maxLen = maxLen + 1
    }
    ("0".*(maxLen - x.length()) + x, "0".*(maxLen - y.length()) + y)
  }

  /**
   * @param x
   * @return splits the string in two equal parts and returns a tuple
   */
  def split_in_two(x: String): (String, String) = (x.substring(0, x.length() / 2), x.substring(x.length() / 2))

  def recursive_multiply(x: Int, y: Int): Int = {

    /** returns (a,b) where input = a*(2^n) + b */
    def calculateCoeff(input: Int, n: Int): (Int, Int) = (input >> n, input % (1 << n))

    if (x < 256 | y < 256) {
      x * y
    } else {
      val n = 8
      val (a, b) = calculateCoeff(x, n)
      val (c, d) = calculateCoeff(y, n)
      (recursive_multiply(a, c) << (2 * n)) + ((recursive_multiply(a, d) + recursive_multiply(b, c)) << n) + recursive_multiply(b, d)
    }
  }

  def recursive_multiply_base10(x: Int, y: Int): Double = {

    /** returns (a,b) where a and b satisfy the equation: input = a*(base^exp) + b */
    def calculateCoeff(base: Int, exp: Int)(input: Int): (Int, Int) = (input / Math.pow(base, exp).toInt , input % Math.pow(base, exp).toInt)

    if (x < 100 | y < 100) {
      x * y
    } else {
      val (a, b) = calculateCoeff(10, 2)(x)
      val (c, d) = calculateCoeff(10, 2)(y)
      (recursive_multiply_base10(a, c) * Math.pow(10, 4)) + ((recursive_multiply_base10(a, d) + recursive_multiply_base10(b, c)) * Math.pow(10, 2)) + recursive_multiply_base10(b, d)
    }
  }

}