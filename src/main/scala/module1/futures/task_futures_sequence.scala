package module1.futures

import module1.futures.HomeworksUtils.TaskSyntax

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object task_futures_sequence {

  /**
   * В данном задании Вам предлагается реализовать функцию fullSequence,
   * похожую на Future.sequence, но в отличии от нее,
   * возвращающую все успешные и не успешные результаты.
   * Возвращаемое тип функции - кортеж из двух списков,
   * в левом хранятся результаты успешных выполнений,
   * в правово результаты неуспешных выполнений.
   * Не допускается использование методов объекта Await и мутабельных переменных var
   */
  /**
   * @param futures список асинхронных задач
   * @return асинхронную задачу с кортежом из двух списков
   */
  def fullSequence[A](futures: List[Future[A]])
                     (implicit ex: ExecutionContext): Future[(List[A], List[Throwable])] = {

    // Вспомогательный class. Чисто для удобства.
    class Result(good: List[A], bad: List[Throwable]) {
      def add(x : Try[A]): Result = x match {
        //case Failure(e) => new Result(good, e +: bad) //more efficient, но потом нужен будет reverse
        case Failure(e) => new Result(good, bad :+ e)
        //case Success(v) => new Result(v +: good, bad)
        case Success(v) => new Result(good :+ v, bad)
      }
      
      def toTuple: (List[A], List[Throwable]) = (good, bad)
    }
    object Result {
      val empty = new Result(List.empty[A], List.empty[Throwable])
    }
    
    val start: Future[Result] = Future.successful(Result.empty)
    futures.foldLeft(start){ case (r, f) =>
      f.transform { t => Success(r.map(_.add(t))) }.flatten
    }.map(_.toTuple)
  }

}
