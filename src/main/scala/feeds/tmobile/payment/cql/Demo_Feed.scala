package feeds.tmobile.payment.cql

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

import scala.io.Source
import scala.util.Random

class Demo_Feed
  extends BaseFeed with LazyLogging{

  var walletIds = scala.collection.mutable.ArrayBuffer.empty[String]
  val random = new Random

  for (line <- Source.fromFile("ids.csv").getLines) {
    val tokens = line.split(",")
    if (tokens.length > 0) walletIds += {tokens(0)}
  }


  def getWalletTmoid = {
    def rowData = this.getRowData


    Iterator.continually(rowData)
  }

  def getRowData = {

    val bigint_size = 12
//val dps_payment_id = dpsIds(random.nextInt(dpsIds.length))
    Map(
      "account_number" -> getRandomNumber(bigint_size).toString,
      "tmoid" -> getRandomNumber(bigint_size).toString,
      "wallet_id" -> walletIds(random.nextInt(walletIds.length))
    )
  }

  def getRandomNumber(digits:Int) = {
    System.nanoTime()
  }

  def getWalletId() = {

  }

  def getStoredPayment = {
    def rowData = this.getStoredPaymentRowData


    Iterator.continually(rowData)
  }

  def getStoredPaymentRowData = {

    val bigint_size = 12

    Map(
      "wallet_id" -> walletIds(random.nextInt(walletIds.length)),
      "tmotoken" -> getRandomNumber(bigint_size).toString,
      "payment_device_id" -> getRandomNumber(bigint_size).toString
    )
  }
  
}
