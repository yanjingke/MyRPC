package cn.itcast.implic

class Boy(val name:String,var faceValue:Int) extends Comparable[Boy]{
  override def compareTo(o: Boy): Int = {
    this.faceValue-o.faceValue
  }
}
