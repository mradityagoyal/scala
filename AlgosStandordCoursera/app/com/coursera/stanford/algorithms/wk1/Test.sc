package com.coursera.stanford.algorithms.wk1

object Test {
  //println("Welcome to the Scala worksheet")
  //val ac = 10
  // (1 << 4) * ac

  // ac << 4

  //Multiplication.recursive_multiply(1234, 5678)
  //val res = Multiplication.recursive_multiply_base10(1001, 20000)
  val res = KaratsubaMultiplication.recursive_multiply(123400, 5678)
                                                  //> res  : BigInt = 700665200


  val ans = BigInt("3141592653589793238462643383279502884197169399375105820974944592") * BigInt("2718281828459045235360287471352662497757247093699959574966967627")
                                                  //> ans  : scala.math.BigInt = 8539734222673567065463550869546574495034888535765
                                                  //| 1149618796011270677430448932048486178750722162490730133748958719528065827231
                                                  //| 84
 // println(s"result is : $ans")
    
  val rec_ans = KaratsubaMultiplication.recursive_multiply(BigInt("3141592653589793238462643383279502884197169399375105820974944592"),
                                                       BigInt("2718281828459045235360287471352662497757247093699959574966967627"))
                                                  //> rec_ans  : BigInt = 85397342226735670654635508695465744950348885357651149618
                                                  //| 79601127067743044893204848617875072216249073013374895871952806582723184
  // println(s"karatsuba answer is : $rec_ans")
  
  
   val kar_ans = KaratsubaMultiplication.karatsuba_multiply(BigInt("3141592653589793238462643383279502884197169399375105820974944592"),
                                                       BigInt("2718281828459045235360287471352662497757247093699959574966967627"))
                                                  //> kar_ans  : BigInt = 8539734222673567065463550869546574495034888535765114961
                                                  //| 879601127067743044893204848617875072216249073013374895871952806582723184
 // println(s"karatsuba answer is : $kar_ans")

}