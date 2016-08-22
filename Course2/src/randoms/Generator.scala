package randoms

trait Generator[+T] {
  self =>
  def generate: T
  
  def map[S](f: T=> S): Generator[S] = new Generator[S]{
    def generate = f(self.generate)
  }
}