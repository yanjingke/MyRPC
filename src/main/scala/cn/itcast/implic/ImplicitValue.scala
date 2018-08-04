package cn.itcast.implic
import Context._
object Context{
  implicit val aaaaa = "laozhao"
  implicit val i = 1
}
object ImplicitValue {
  def sayHi()(implicit name:String="SB")={
    println(s"hi-$name")

  }

  def main(args: Array[String]): Unit = {
    sayHi()
    println(1 to 10)
  }
}
