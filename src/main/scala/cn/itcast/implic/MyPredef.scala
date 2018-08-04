package cn.itcast.implic

import java.io.File

object MyPredef {
  implicit def fileToRichFile(f:File)=new RichFile(f)
}
