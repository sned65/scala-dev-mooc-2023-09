package scala


//1. extension methods
object Extensionmethods{

  case class Circle(x: Double, y: Double, radius: Double)

  extension (c: Circle)
    def circlelength: Double = c.radius*math.Pi*2

  @main def ExtensionmethodsEx(): Unit = {
    val circle = Circle(0,0,1)
    println(circle.circlelength)
  }
}

//2. Operators
object  Operators {
  extension (x: String)
    def < (y: String): Boolean = x.length < y.length

  @main def OperatorsEx() = {
    println("a" < "aa")
  }
}

//3. collective extensions
object  CollectiveExtension{
  extension (s: Seq[String])
    def longestStrings: Seq[String] =
      val maxLength = s.map(_.length).max
      s.filter(_.length == maxLength)

    def longestString: String = longestStrings.head

  /*
  extension (s: Seq[String])
    def longestStrings ...
  extension (s: Seq[String])
    def longestString ...
  */

  @main def CollectiveExtensionEx() = {
    println(("a" :: "aa" :: "ccc" :: Nil).longestString)
  }
}

trait IntOps:
  extension (i:Int) def isZero: Boolean = i == 0
  extension (i:Int) def safeMode(x:Int): Option[Int] =
    if x.isZero then None else Some(i*x)

object IntOpsEx extends IntOps:
  extension (i:Int) def safeDiv(x:Int): Option[Int] =
    if x.isZero then None else Some(i/x)

trait SafeDiv:
  import  IntOpsEx.*

  extension  (i:Int) def divide(d:Int): Option[(Int, Int)] =
    (i.safeDiv(d), i.safeMode(d)) match
      case (Some(d), Some(r)) => Some((d,r))
      case _ => None

object scala3extensions {
  given ops1: IntOps() with {}

  @main def scala3extensionsEx() ={
    println(2.safeMode(2))

  }

}

