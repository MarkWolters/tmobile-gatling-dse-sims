package feeds.tmobile.payment.cql

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

class Wallet_tmoid_feed
  extends BaseFeed with LazyLogging{

  def getWalletTmoidStgFeed = {
    def rowData= this.getRowData

    Iterator.continually(rowData)
  }

  def getRowData = {

    val bigint_size = 12

    Map(
      "account_number" -> getRandomNumber(bigint_size).toString,
      "tmoid" -> getRandomNumber(bigint_size).toString,
      "account_status" -> getRandomNumber(bigint_size).toString,
      "account_type" -> getRandomNumber(bigint_size).toString,
      "created_by" -> getRandomNumber(bigint_size).toString,
      "created_date" -> getCurrentTimestamp,
      "dps_status_code" -> getRandomNumber(bigint_size).toString,
      "error_description" -> getRandomNumber(bigint_size).toString,
      "file_id" -> getRandomNumber(bigint_size).toString,
      "modified_by" -> getRandomNumber(bigint_size).toString,
      "modified_date" ->  getCurrentTimestamp,
      "retry_counter" -> getRandomNumber(bigint_size).toInt,
      "status" -> getRandomNumber(bigint_size).toString,
      "wallet_id" -> getRandomNumber(bigint_size).toString
    )
  }

  def getRandomNumber(digits:Int) = {
    System.nanoTime()
  }

}
