package basic

import scala.collection.immutable
import akka.actor.ActorSystem
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Source

object CustomersExample extends App {
  implicit val actorSystem = ActorSystem("demo")
  import actorSystem.dispatcher
  implicit val flowMaterializer = ActorFlowMaterializer()

  val inputCustomers = Source((1 to 100).map(_ => InputCustomer.random()))

  val normalize = Flow[InputCustomer].mapConcat { input =>
    input.name.split(" ").toList match {
      case firstName :: lastName :: Nil => immutable.Seq(OutputCustomer(firstName, lastName))
      case _ => immutable.Seq[OutputCustomer]()
    }
  }

  val writeCustomers = Sink.foreach[OutputCustomer] { customer => println(customer) }

  inputCustomers.via(normalize).runWith(writeCustomers).andThen {
    case _ =>
      actorSystem.shutdown()
      actorSystem.awaitTermination()
  }
}