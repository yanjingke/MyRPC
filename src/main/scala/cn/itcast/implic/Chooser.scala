package cn.itcast.implic
////视图界定
//view bound必须传进去一个隐式转换函数
//class Chooser[T<%Ordered[T]] {
//  def choose(first:T,second:T):T={
//      if( first<second ) first else second
//  }
//}
//上下文界定
//他必须传进去一个隐式转换的值
class Chooser[T : Ordering]{
  def choose(first:T,second:T):T={
    val ord=implicitly[Ordering[T]]
    if(ord.gt(first,second))first else second
  }
}
object Chooser{
  def main(args: Array[String]): Unit = {
    import MyPredef._
    val c=new Chooser[Girl]
    val g1=new Girl("ZHANGSAHN",90)
    val g2=new Girl("Patalni",99)

    val g=c.choose(g1,g2)
    println(g.name)
  }
}