package streams

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Bloxorz._

@RunWith(classOf[JUnitRunner])
class BloxorzSuite extends FunSuite {

  trait SolutionChecker extends GameDef with Solver with StringParserTerrain {
    /**
     * This method applies a list of moves `ls` to the block at position
     * `startPos`. This can be used to verify if a certain list of moves
     * is a valid solution, i.e. leads to the goal.
     */
    def solve(ls: List[Move]): Block =
      ls.foldLeft(startBlock) { case (block, move) => move match {
        case Left => block.left
        case Right => block.right
        case Up => block.up
        case Down => block.down
      }
    }
  }

  trait Level1 extends SolutionChecker {
      /* terrain for level 1*/

    val level =
    """ooo-------
      |oSoooo----
      |ooooooooo-
      |-ooooooooo
      |-----ooToo
      |------ooo-""".stripMargin

    val optsolution = List(Right, Right, Down, Right, Right, Right, Down)
  }


	test("terrain function level 1") {
    new Level1 {
      assert(terrain(Pos(0,0)), "0,0")
      assert(terrain(Pos(1,1)), "1,1") // start
      assert(terrain(Pos(4,7)), "4,7") // goal
      assert(terrain(Pos(5,8)), "5,8")
      assert(!terrain(Pos(5,9)), "5,9")
      assert(terrain(Pos(4,9)), "4,9")
      assert(!terrain(Pos(6,8)), "6,8")
      assert(!terrain(Pos(4,11)), "4,11")
      assert(!terrain(Pos(-1,0)), "-1,0")
      assert(!terrain(Pos(0,-1)), "0,-1")
    }
  }

	test("findChar level 1") {
    new Level1 {
      assert(startPos == Pos(1,1))
    }
  }


	test("optimal solution for level 1") {
    new Level1 {
      assert(solve(solution) == Block(goal, goal))
    }
  }
	
	test("test isStanding"){
	  new Level1 {
      assert(Block(Pos(0,0), Pos(0,0)).isStanding, "(0,0),(0,0) should be standing")
      assert(!Block(Pos(0,0), Pos(0,1)).isStanding, "(0,0),(0,1) should not standing")
    }
	}
	
	test("test isLegal"){
	  new Level1 {
      assert(!Block(Pos(0,2), Pos(0,3)).isLegal, "(0,2),(0,3) should not be in terrain")
      assert(Block(Pos(0,2), Pos(0,2)).isLegal, "(0,2),(0,2) should be in terrain")
      assert(Block(Pos(1,0), Pos(2,0)).isLegal, "(1,0),(2,0) should be in terrain")
    }
	}
	
	test("start block"){
	  new Level1 {
      assert(startBlock.b1.x == 1 && startBlock.b1.y ==1 && startBlock.b2.x == 1 && startBlock.b2.y ==1  , "start block is (1,1),(1,1)")
    }
	}


	test("optimal solution length for level 1") {
    new Level1 {
      assert(solution.length == optsolution.length)
    }
  }

}
