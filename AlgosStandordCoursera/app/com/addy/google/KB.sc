package com.addy.google

object KB {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  //val coords = OnScrKB.coords
  
  //val map = coords.toMap
  
  OnScrKB.getKeyStrokes("ADITYAGOYAL")            //> res0: String = *RRR*LLLDD*RRRDD*LLLDD*UUUUUU*RRD*DD*LLDDD*UUUUUU*RRRDD*
  OnScrKB.devideAndConquer("ADITYAGOYAL")         //> res1: String = *RRR*LLLDD*RRRDD*LLLDD*UUUUUU*RRD*DD*LLDDD*UUUUUU*RRRDD*
}