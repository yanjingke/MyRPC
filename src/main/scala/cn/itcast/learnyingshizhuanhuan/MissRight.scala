package cn.itcast.learnyingshizhuanhuan



class MissRight[T] {
  //相当于viewBound
  def choose  (first:T,second:T)(implicit ord:T=>Ordered[T]):T={
    if(first>second) first else second
  }
  //相当于上下文界定
  def select (first:T,second:T)(implicit ord:Ordering[T]):T={
    if(ord.gt(first,second))first else second
  }
  def random(first:T,second:T)(implicit ord:Ordering[T]):T={
      import Ordered.orderingToOrdered
    if(first>second) first else second
  }
}

object MissRight{
  def main(args: Array[String]): Unit = {
   val mr= new MissRight[Girl]
    val g1=new Girl("gahio",98,28)
    val g2=new Girl("soro",98,30)
    import MyPreDef._
    val g=mr.choose(g1,g2)
    print(g.name)
    val g3=mr.random(g1,g2)
    print(g3.name)
  }
}