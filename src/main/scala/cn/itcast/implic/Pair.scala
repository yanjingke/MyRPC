package cn.itcast.implic

class Pair [T<:Comparable[T]]{
    def bigger(firsr:T,second:T)={
      if(firsr.compareTo(second)>0 )firsr else second

    }
}
object Pair{
  def main(args: Array[String]): Unit = {
    val p=new Pair[String]()
  // print( p.bigger("hadoop","spark"))
    val p1=new Pair[Integer]()
    print( p1.bigger(1,2))
  }
}