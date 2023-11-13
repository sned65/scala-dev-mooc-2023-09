package scala3_2

type MyTry = [X] =>> Either[Throwable, X]


@main def mainLambda(): Unit = {

  val myTryInt : MyTry[Int] = Right(10)
  val myTryString : MyTry[String] = Right("bla bla")
  val myTryLeft : MyTry[Int] = Left(Exception("bla"))

  println(myTryInt)
  println(myTryString)
  println(myTryLeft)

}