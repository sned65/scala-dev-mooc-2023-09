import module1.opt.{None, Option, Some}
import module1.list.List
import module1.{hof, type_system}

object Main {

  def main(args: Array[String]): Unit = {
    println("Hello world ")

    //val f: String => Unit =  hof.logRunningTime(hof.doomy)

    //f("foo")

    /////////////////////////
    // Tests for exercises
    ////////////////////////
    
    // Option

    val something1 = Some(10)
    val something2 = Some("qq")
    val something3 = Some(20)
    val nothing: Option[Int] = None

    println("I expect 10")
    something1.printIfAny()
    println("I expect nothing")
    nothing.printIfAny()

    val z1 = something1 zip something2
    assert(z1 == Some(10, "qq"))
    val z2 = something1 zip nothing
    assert(z2 == None)
    val z3 = nothing zip something2
    assert(z3 == None)
    
    assert(something1.filter(_ > 15) == None)
    assert(something3.filter(_ > 15) == something3)
    
    // List
    
    val list1 = List(1,2,3)
    val sl1 = list1.mkString("|")
    assert(sl1 == "1|2|3")
    val list2 = 4 :: list1
    val sl2 = list2.mkString(", ")
    assert(sl2 == "4, 1, 2, 3")

    val even = list2.filter(_%2 == 0)
    assert(even.mkString(",") == "4,2")
    
    val rlist2 = list2.reverse
    assert(rlist2.mkString(",") == "3,2,1,4")

    /**
     *
     * Написать функцию incList котрая будет принимать список Int и возвращать список,
     * где каждый элемент будет увеличен на 1
     */
    def incList(list: List[Int]): List[Int] = list.map(_ + 1)
    
    val list3 = incList(list2)
    assert(list3.mkString(",") == "5,2,3,4")

    /**
     *
     * Написать функцию shoutString котрая будет принимать список String и возвращать список,
     * где к каждому элементу будет добавлен префикс в виде '!'
     */
    def shoutString(list: List[String]): List[String] = list.map("!"+_)

    val list4 = List("Hello", "Scala")
    assert(shoutString(list4).mkString("") == "!Hello!Scala")
  }
}