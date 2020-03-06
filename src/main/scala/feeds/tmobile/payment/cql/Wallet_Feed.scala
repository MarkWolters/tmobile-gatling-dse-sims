package feeds.tmobile.payment.cql

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

class Wallet_Feed
  extends BaseFeed with LazyLogging{


  def getWallet = {
    def rowData = this.getRowData


    Iterator.continually(rowData)
  }

  def getRowData = {

    val bigint_size = 12

    Map(
      "billing_account_number" -> getRandomNumber(bigint_size).toString,
      "common_customer_id" -> getRandomNumber(bigint_size).toString,
      "wallet_id" -> getRandomNumber(bigint_size).toString,
      "created_by" -> getRandomNumber(bigint_size).toString,
      "created_date" -> getCurrentTimestamp,
      "modified_by" -> getRandomNumber(bigint_size).toString,
      "modified_date" -> getCurrentTimestamp
    )
  }

  def getRandomNumber(digits:Int) = {
    System.nanoTime()
  }
  
}
