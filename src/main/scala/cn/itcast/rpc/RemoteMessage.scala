package cn.itcast.rpc

trait RemoteMessage extends Serializable
case class RegisterWorker(id:String,memrroy:Int,core:Int)extends Serializable

//Master -> Worker
case class RegisteredWorker(masterUrl: String) extends RemoteMessage
case object SendHearbeat
case object CheckTimeOutWorker
case class Heartbeat(id: String) extends RemoteMessage