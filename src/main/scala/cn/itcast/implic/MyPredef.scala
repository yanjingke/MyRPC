package cn.itcast.implic

import java.io.File

object MyPredef {
  implicit def fileToRichFile(f:File)=new RichFile(f)
//  implicit def girl2Oreder(g:Girl)=new Ordered[Girl]{
//    override def compare(that: Girl): Int = {
//      g.faceValue-that.faceValue
//    }
//  }
  implicit val girl2Oreder=(g:Girl)=>new Ordered[Girl]{
    override def compare(that: Girl): Int = {
      g.faceValue-that.faceValue
    }
  }
}
