package scala3_2

import scala.annotation.targetName

/*
 написать расширение Int чтобы реалтзовать поведение "56" + "3" = 563
*/
object homework1 {
  extension (x: Int)
    @targetName("StringLikePlus")
    def <+>(y: Int): Int = (x.toString + y.toString).toInt
    
  @main def part1Ex(): Unit ={
    assert(1 <+> 33 == 133)
    println(1 <+> 33)
  }
}

/*
  https://docs.scala-lang.org/scala3/reference/contextual/conversions.html
    реализуйте паттерн "магнит" из ссылки выше для String, Float, Int
  тоесть передаются параметры, а далее в зависимости от того что передалось вызывается функция
  given fromString : Conversion[String, CompletionArg] = ShowItIsString()
  given fromInt : Conversion[Int, CompletionArg] = ShowItIsInt()
  given fromFloat: Conversion[Float, CompletionArg] = ShowItIsFloat(_)
*/
object homework2 {
  enum CompletionArg {
    case I(value: Int)
    case F(value: Float)
    case S(value: String)
  }

  object CompletionArg {
    given fromString: Conversion[String, CompletionArg] = S(_)

    given fromInt: Conversion[Int, CompletionArg] = I(_)

    given fromFloat: Conversion[Float, CompletionArg] = F(_)
  }
  
  object Completions:
    def complete[T](arg: CompletionArg): String = arg match {
      case CompletionArg.I(value) => s"Integer $value"
      case CompletionArg.F(value) => s"Float $value"
      case CompletionArg.S(value) => s"String \"$value\""
    }
    
    import CompletionArg.*
    import scala.language.implicitConversions

    @main def part2Ex(): Unit = {
      println(Completions.complete("String"))
      assert(Completions.complete("String") == "String \"String\"")
      
      println(Completions.complete(1))
      assert(Completions.complete(1) == "Integer 1")
      
      println(Completions.complete(7f))
      assert(Completions.complete(7f) == "Float 7.0")
    }
}

/*
  повторить пример с логарифмами и непрозрачными типами
*/
object homework3 {
  opaque type Logarithm = Double

  object Logarithm {
    def apply(d: Double): Logarithm = math.log(d)
  }

  extension (x: Logarithm)
    def toDouble = math.exp(x)
    def + (y: Logarithm) = Logarithm(math.exp(x) + math.exp(y))
    def * (y: Logarithm) = x + y


  @main def part3Ex(): Unit ={
    val l1 = Logarithm(1.0)
    val l2 = Logarithm(2.0)
    println(s"l1 = $l1")
    println(s"l2 = $l2")
    val l3 = l1 * l2
    val l4 = l1 + l2
    println(s"l3 = $l3")
    println(s"l4 = $l4")
  }
}