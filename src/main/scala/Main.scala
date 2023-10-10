import module1.{hof, type_system}

object Main {

  def main(args: Array[String]): Unit = {
    println("Hello world ")

    val f: String => Unit =  hof.logRunningTime(hof.doomy)

    f("foo")
  }
}