package module2

import scala.language.implicitConversions

object higher_kinded_types{

  def tuple[A, B](a: List[A], b: List[B]): List[(A, B)] =
    a.flatMap{ a => b.map((a, _))}

  def tuple[A, B](a: Option[A], b: Option[B]): Option[(A, B)] =
    a.flatMap{ a => b.map((a, _))}

  def tuple[E, A, B](a: Either[E, A], b: Either[E, B]): Either[E, (A, B)] =
    a.flatMap{ a => b.map((a, _))}


  def tuplef[F[_], A, B](fa: Bindable[F, A], fb: Bindable[F, B]): F[(A, B)] = {
    tupleBindable(fa, fb)
  }


  trait Bindable[F[_], A] {
    def map[B](f: A => B): F[B]
    def flatMap[B](f: A => F[B]): F[B]
  }

  def tupleBindable[F[_], A, B](fa: Bindable[F, A], fb: Bindable[F, B]): F[(A, B)] =
    fa.flatMap(a => fb.map(b => (a, b)))

  implicit def optBindable[A](opt: Option[A]): Bindable[Option, A] = new Bindable[Option, A] {
    override def map[B](f: A => B): Option[B] = opt.map(f)

    override def flatMap[B](f: A => Option[B]): Option[B] = opt.flatMap(f)
  }

  implicit def listBindable[A](list: List[A]): Bindable[List, A] = new Bindable[List, A] {
    override def map[B](f: A => B): List[B] = list.map(f)
    override def flatMap[B](f: A => List[B]): List[B] = list.flatMap(f)
  }


  def main(args: Array[String]): Unit = {
    val optA: Option[Int] = Some(1)
    val optB: Option[Int] = Some(2)

    val list1 = List(1, 2, 3)
    val list2 = List(4, 5, 6)

    //val r3: Option[(Int, Int)] = tupleBindable(optBindable(optA), optBindable(optB))
    //val r4 = println(tupleBindable(listBindable(list1), listBindable(list2)))
    
    val r1 = println(s"r1 = ${tuplef(optA, optB)}")      // r1 = Some((1,2))
    val r2 = println(s"r2 = ${tuplef(list1, list2)}")    // r2 = List((1,4), (1,5), (1,6), (2,4), (2,5), (2,6), (3,4), (3,5), (3,6))
    
    // Первое впечатление было, когда прочитал условие, что нужно реализовать zip.
    // Позже, по лекционному примеру, осознал, что нужно декартово произведение.
    // Но код уже наваял. См. ниже.
    
    trait Zipable[F[_], A] {
      def z[B](fb: F[B]): F[(A, B)]
    }
    
    def tupleZ[F[_], A, B](fa: Zipable[F, A], fb: F[B]): F[(A, B)] = fa z fb
    implicit def opt2zipable[T](opt: Option[T]): Zipable[Option, T] = new Zipable[Option, T] {
      override def z[B](fb: Option[B]): Option[(T, B)] = opt zip fb
    }
    implicit def list2zipable[T](list: List[T]): Zipable[List, T] = new Zipable[List, T] {
      override def z[B](fb: List[B]): List[(T, B)] = list zip fb
    }

    val r1z = println(s"r1z = ${tupleZ(optA, optB)}")    // r1z = Some((1,2))
    val r2z = println(s"r2z = ${tupleZ(list1, list2)}")  // r2z = List((1,4), (2,5), (3,6))
  }
}