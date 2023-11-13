package scala3_2


trait Show[A] {
  def show(a:A) : String
}

object Show {
  def apply[A](implicit instance: Show[A]): Show[A] = instance

  given Show[Int] with {
    def show(a:Int): String = (a*10).toString
  }

  given Show[String] with {
    def show(a: String): String = a
  }
}

object Main extends  App {
  def printShow[A](a:A)(using show: Show[A]): Unit = {
    val str = show.show(a)
    println(str)
  }

  printShow(42)
  printShow("sdfsdfsdf")
}
// def combine[A](a1:A, a2:A) (using ev: A => Numeric[A]):A