package module1

import java.io.{Closeable, File}
import scala.io.{BufferedSource, Source}
import scala.util.{Try, Using}


object type_system {

  /**
   * Scala type system
   *
   */



  def absurd(v: Nothing) = ???


  // Generics


//  lazy val file: File = ???
//  lazy val source: BufferedSource = Source.fromFile(file)
//
//  lazy val lines: List[String] = source.getLines().toList

  // source.close()

//  lazy val lines2: List[String] = try{
//    source.getLines().toList
//  } finally {
//    source.close()
//  }

  def foo(x: Int, y: Int) = x + y
  def foo2(x: Int)(y: Int) = x + y


  def ensureClose[S <: {def close(): Unit}, R](source: S)(f: S => R): R = try{
    f(source)
  } finally {
    source.close()
  }

//  val lines3: Iterator[String] = ensureClose(source){ s =>
//    s.getLines()
//  }


  // ограничения связанные с дженериками


  /**
   *
   * class
   *
   * конструкторы / поля / методы / компаньоны
   */

   class User private(val email: String = "foo@t.com", val password: String = "123"){


   }

   val user: User = User.from(password = "345")
   val user2: User = User.from("foo@q.com")

   user.password
   user.email

   object User{
     def from(email: String = "foo@t.com", password: String = "123"): User =
       new User(email, password)
   }



  /**
   * Задание 1: Создать класс "Прямоугольник"(Rectangle),
   * мы должны иметь возможность создавать прямоугольник с заданной
   * длиной(length) и шириной(width), а также вычислять его периметр и площадь
   *
   */


  /**
   * object
   *
   * 1. Паттерн одиночка
   * 2. Ленивая инициализация
   * 3. Могут быть компаньоны
   */


  /**
   * case class
   *
   */



    // создать case класс кредитная карта с двумя полями номер и cvc

  case class CreditCard(number: String, cvc: Int)

  val cc = CreditCard("12344t545", 321)

  val cc2 = cc.copy(cvc = 567)


  /**
   * case object
   *
   * Используются для создания перечислений или же в качестве сообщений для Акторов
   */

   case object One


  /**
   * trait
   *
   */

  trait WithId{
    def id: String
  }

  trait UserService{
    def get(email: String): User
    def insert(user: User): Unit
  }

  class UserServiceImpl extends UserService{
    override def get(email: String): User = ???

    override def insert(user: User): Unit = ???
  }





  class A {
    def foo() = "A"
  }

  trait B extends A {
    override def foo() = "B" + super.foo()
  }

  trait C extends B {
    override def foo() = "C" + super.foo()
  }

  trait D extends A {
    override def foo() = "D" + super.foo()
  }

  trait E extends C {
    override def foo(): String = "E" + super.foo()
  }

  // A -> D -> B -> C
  // CBDA
  val v = new A with D with C with B

  // A -> B -> C -> E -> D
  // DECBA
  val v1 = new A with E with D with C with B


  /**
   * Value classes и Universal traits
   */


}