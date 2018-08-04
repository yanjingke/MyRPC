package cn.itcast.implic

import java.io.File

import scala.io.Source
import MyPredef._
class RichFile (val f:File){
    def  read()=Source.fromFile(f).mkString
}
object RichFile{
  def main(args: Array[String]): Unit = {
    val f = new File("c://words.txt")
    val contents =     f.read();
    println(contents)
  }
}