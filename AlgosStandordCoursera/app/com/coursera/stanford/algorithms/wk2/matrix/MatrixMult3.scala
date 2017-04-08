package com.coursera.stanford.algorithms.wk2.matrix


trait MatrixLike [A]{
  def columns: List[List[A]]
  def transpose: MatrixLike[A]
  def rows : List[List[A]] 
}

trait SqMatrixLike[A] extends MatrixLike[A]{
  require(columns.length == rows.length)
}

class SqMatrix[A](cols: List[List[A]]) extends SqMatrixLike[A]{
  def columns: List[List[A]] = cols
  def transpose: SqMatrixLike[A] = ???
  columns.map(_.head) 
  def rows = transpose.columns
}


