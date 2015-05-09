package basic

import scala.util.Random

object InputCustomer {
  def random(): InputCustomer = {
    InputCustomer(s"FirstName${Random.nextInt(1000)} LastName${Random.nextInt(1000)}")
  }
}

case class InputCustomer(name: String)

case class OutputCustomer(firstName: String, lastName: String)