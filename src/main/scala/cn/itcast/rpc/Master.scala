package cn.itcast.rpc

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.duration._
import scala.collection.mutable

class Master(val host:String,val port:Int) extends  Actor{
  val idToWorker=new mutable.HashMap[String,WorkerInfo]()
  val workers=new mutable.HashSet[WorkerInfo]()//使用set删除快, 也可用linkList
  //超时检查的间隔
  val CHECK_INTERVAL = 15000
  override def preStart(): Unit = {
    println("preStart invoked")
    //导入隐式转换
    import context.dispatcher //使用timer太low了, 可以使用akka的, 使用定时器, 要导入这个包
    context.system.scheduler.schedule(0 millis, CHECK_INTERVAL millis, self, CheckTimeOutWorker)
    }
  override def receive: Receive = {
//    case "connect"=>{
//      println("a client connected")
//      sender ! "reply"
//
//    }
//    case "hello" => {
//      println("hello")
//    }

    case RegisterWorker(id,memory,core)=>{
      if(!idToWorker.contains(id)){
        val wokerInfo=new WorkerInfo(id,memory,core)
        idToWorker(id)=wokerInfo
        workers+=wokerInfo
        sender ! RegisteredWorker(s"akka.tcp://MasterSystem@$host:$port/user/Master")
      }
    }
    case Heartbeat(id)=>{
      if(idToWorker.contains(id)){
        val workerInfo=idToWorker(id)
        //报活
        val currentTime = System.currentTimeMillis()
        workerInfo.lastHeartbeatTime = currentTime
      }
    }
    case CheckTimeOutWorker=>{
    val currentTime = System.currentTimeMillis()
    val toRemove=workers.filter(x=>currentTime-x.lastHeartbeatTime>CHECK_INTERVAL)
      for(w<-toRemove){
        workers-=w
        idToWorker -= w.id
      }
      println(workers.size)
  }
  }
}
object Master{
  def main(args: Array[String]): Unit = {
    val host=args(0)
    val port=args(1).toInt
    val configSt=s"""
                 |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
                 |akka.remote.netty.tcp.hostname = "$host"
                 |akka.remote.netty.tcp.port = "$port"
                 """.stripMargin
    val config=ConfigFactory.parseString(configSt);
    val actorSystem=ActorSystem("MasterSystem",config)

    val master = actorSystem.actorOf(Props(new Master(host,port)),"Master")
   // master ! "hello"  //发送信息
    actorSystem.awaitTermination()
  }
}