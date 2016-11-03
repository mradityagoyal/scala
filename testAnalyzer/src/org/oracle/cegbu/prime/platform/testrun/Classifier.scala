package org.oracle.cegbu.prime.platform.testrun

import scala.collection.Map

class Classifier {
  
  //used in jenkins json api to control what we receive
  val treeParameter = "tree=duration,empty,failCount,passCount,skipCount,suites[cases[className,status,skipped,name]]"
  
  /**
   * returns the union of two sets. 
   */
  def union(current: Set[String], base: Set[String]): Set[String] = current union base
  
  def intersection(current: Set[String], base: Set[String]): Set[String] = current intersect base 
  
  
  /**
   * takes in two sets current and base and returns 
   *  "union"  <- a union b
   *  "intersect" <- a intersect b
   *  "a - b" <- a minus b
   *  "b - a" <- b minus a 
   */
  def classify(a: Set[String], b: Set[String]) = {
   Map(
       "union"-> (a union b), 
       "intersect" -> (a intersect b), 
       "a-b" -> (a -- b), 
       "b-a" -> (b -- a) 
      )
  }
  
  
  
}