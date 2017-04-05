package com.coursera.stanford.algorithms.wk1

object KaratsubaMultiplication {

  /**
   * @param base
   * @param exp
   * @return floors the value of Math.pow(base, exp) to a BigInt
   */
  def pow(base: Int, exp: Int): BigInt = {
    BigDecimal(Math.pow(base, exp)).setScale(0, BigDecimal.RoundingMode.FLOOR).toBigInt()
  }

  /** returns (a,b) where a and b satisfy the equation: input = a*(base^exp) + b */
  def calculateCoeff(base: Int, exp: Int)(input: BigInt): (BigInt, BigInt) = (input / pow(base, exp), input % pow(base, exp))

  def recursive_multiply(x: BigInt, y: BigInt): BigInt = {
    /** predicate that defines end of recursive call. */
    def endRecursion(): Boolean = x < Integer.MAX_VALUE && y < Integer.MAX_VALUE
    if (endRecursion()) {
      //TODO: use only int multiplication. ensure that x and y are smaller than int max. 
      x * y
    } else {
      val (base, n) = (10, 5) 
      val (a, b) = calculateCoeff(base, n)(x)
      val (c, d) = calculateCoeff(base, n)(y)
      (recursive_multiply(a, c) * pow(base, 2*n)) + ((recursive_multiply(a, d) + recursive_multiply(b, c)) * pow(base, n)) + recursive_multiply(b, d)
    }
  }
  
  def karatsuba_multiply(x: BigInt, y: BigInt): BigInt = {
    /** predicate that defines end of recursive call. */
    def endRecursion(): Boolean = x < Integer.MAX_VALUE && y < Integer.MAX_VALUE
    if (endRecursion()) {
      //TODO: use only int multiplication. ensure that x and y are smaller than int max. 
      x * y
    } else {
      val (base, n) = (10, 5) 
      lazy val (a, b) = calculateCoeff(base, n)(x)
      lazy val (c, d) = calculateCoeff(base, n)(y)
      lazy val ac  = karatsuba_multiply(a, c)
      lazy val bd = karatsuba_multiply(b, d)
      //thirdTerm = (a+b)*(c+d) - ac - bd
      lazy val thirdTerm =  karatsuba_multiply(a+b, c+d) - ac - bd 
      
      (ac * pow(base, 2*n)) + (thirdTerm * pow(base, n)) + bd
    }
    
    
  }
}