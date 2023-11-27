package module3

import sbt.testing.Task
import zio.{ExitCode, IO, UIO, URIO, ZIO}

object App {
  def main(args: Array[String]): Unit = {
    println("Hello world")

    val r: UIO[Unit] = zioConstructors.z12.provide("Hello from ZIO")
    zio.Runtime.default.unsafeRun(r)

  }
}

object AppZIO extends zio.App{
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    ZIO.effect(println("Hello from zio")).exitCode
}
