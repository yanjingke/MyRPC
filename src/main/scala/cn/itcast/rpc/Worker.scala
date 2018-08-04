package cn.itcast.rpc

import java.util.UUID
import scala.concurrent.duration._
import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class Worker (val masterHost:String,val masterPort:Int,val memory:Int,val core:Int)extends Actor {
  var master: ActorSelection = _
  val workerId=UUID.randomUUID().toString
  val HEART_INTERVAL = 10000

  override def preStart(): Unit = {
    master = context.actorSelection(s"akka.tcp://MasterSystem@$masterHost:$masterPort/user/Master")
    master ! RegisterWorker(workerId,memory,core)
  }

  override def receive: Receive = {
    case "reply" => {
      println("a reply form master")
    }
    case RegisteredWorker(masterUrl)=> {
      println(masterUrl)
      //启动定时器发送心跳
      import context.dispatcher
      //多长时间后执行 单位,多长时间执行一次 单位, 消息的接受者(直接给master发不好, 先给自己发送消息, 以后可以做下判断, 什么情况下再发送消息), 信息
      context.system.scheduler.schedule(0 millis,HEART_INTERVAL millis,self,SendHearbeat)
  }
    case SendHearbeat=>{
      println("send heartbeat to master")
      master ! Heartbeat(workerId)
    }
  }
}
object Worker{
  def main(args: Array[String]): Unit = {
    val host=args(0)
    val port=args(1).toInt
    val masterHost = args(2)
    val masterPort = args(3).toInt
    val memory=args(4).toInt
    val core=args(5).toInt
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    //ActorSystem老大，辅助创建和监控下面的Actor，他是单例的
    val actorSystem = ActorSystem("WorkerSystem", config)
    actorSystem.actorOf(Props(new Worker(masterHost, masterPort,memory,core)), "Worker")
    actorSystem.awaitTermination()
  }
}
