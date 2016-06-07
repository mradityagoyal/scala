package funsets

import FunSets._

object SetsWkst {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  println(contains(singletonSet(1), 2))           //> false
  
  val s5 = singletonSet(5)                        //> s5  : funsets.FunSets.Set = <function1>
  val s6 = singletonSet(6)                        //> s6  : funsets.FunSets.Set = <function1>
  contains(s5, 6)                                 //> res0: Boolean = false
  
  val s56 = union(s5, s6)                         //> s56  : funsets.FunSets.Set = <function1>
  contains(s56, 6)                                //> res1: Boolean = true
  contains(s56, 4)                                //> res2: Boolean = false
  contains(s56, 5)                                //> res3: Boolean = true
  
  
  val setOfEven: Set = x => x%2 == 0              //> setOfEven  : funsets.FunSets.Set = <function1>
  val divBy4 : Set = x => x%4 == 0                //> divBy4  : funsets.FunSets.Set = <function1>
  
  forall(divBy4, x => x%2 ==0)                    //> res4: Boolean = true
  
  val s12 = map(s6, x => 2*x )                    //> s12  : funsets.FunSets.Set = <function1>
  contains(s12, 3)                                //> res5: Boolean = true
  
  printSet(s12)                                   //> {3}
  
}