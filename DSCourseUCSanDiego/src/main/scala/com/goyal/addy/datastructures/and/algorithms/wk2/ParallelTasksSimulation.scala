package com.goyal.addy.datastructures.and.algorithms.wk2

import scala.io.StdIn

object ParallelTasksSimulation extends App {

  case class ThreadInfo(id: Int, workload: Int) extends Ordered[ThreadInfo] {
    override def compare(that: ThreadInfo) = {
      if (this.workload == that.workload) this.id.compare(that.id)
      else this.workload.compare(that.workload)
    }

  }

  val nm = StdIn.readLine().split(" ").map(_.toInt)
  val (n: Int, m: Int) = (nm(0), nm(1))

  val seed: Array[ThreadInfo] = (0 until n).map(ThreadInfo(_, 0)).toArray
  val q = new PriorityQueue(seed)
  
  val jobWorkloads = StdIn.readLine().split(" ").map(_.toInt)
  
  for(load <- jobWorkloads){
    val minThread: ThreadInfo = q.getMin
    println(s"${minThread.id} ${minThread.workload}")
    q.change(0, ThreadInfo(minThread.id, minThread.workload + load))
  }

}

