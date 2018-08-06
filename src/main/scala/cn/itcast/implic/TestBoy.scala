package cn.itcast.implic

object TestBoy {

  def main(args: Array[String]): Unit = {
     val b1=new Boy("laoduan",99)
     val b2=new Boy("aAOZHAO",999)
      val arr=Array(b1,b2)
      var sorted=arr.sortBy(x=>x)
    for(b<-sorted){
      println(b.name)
    }

  }
}
