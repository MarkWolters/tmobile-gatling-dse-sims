package feeds.tmobile.payment.cql

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

class Cust_pymt_profile_by_tmotoken_feed
  extends BaseFeed with LazyLogging{

  def getCustPymtProfileByTmotokenFeed = {
    def rowData= this.getRowData

    Iterator.continually(rowData)
  }

  def getRowData = {

    val bigint_size = 12

    Map(
      "tmotoken" -> getRandomNumber(bigint_size).toString,
      "id_type" -> getRandomNumber(bigint_size).toString,
      "id" -> getRandomNumber(bigint_size).toString,
      "autopay_indicator" -> true,
      "billing_account_number" -> getRandomNumber(bigint_size).toString,
      "card_brand" -> getRandomNumber(bigint_size).toString,
      "card_first_6" -> getRandomNumber(bigint_size).toString,
      "card_last_4" -> getRandomNumber(bigint_size).toString,
      "created_by" -> getRandomNumber(bigint_size).toString,
      "created_date" -> getCurrentTimestamp,
      "customer_mobile_phone" ->  getRandomNumber(bigint_size).toString,
      "dps_payment_id" -> getRandomNumber(bigint_size).toInt,
      "expiration_date" -> getRandomNumber(bigint_size).toString,
      "legacytoken" -> getRandomNumber(bigint_size).toString,
      "member_id" -> getRandomNumber(bigint_size).toString,
      "modified_by" -> getRandomNumber(bigint_size).toString,
      "modified_date" -> getCurrentTimestamp,
      "payment_arrangement_indicator" -> false,
      "routing_number" -> getRandomNumber(bigint_size).toString,
      "schedule_payment_indicator" -> true,
      "stored_payment_indicator" -> false,
      "wallet_id" -> getRandomNumber(bigint_size).toString,
      "zip" -> getRandomNumber(bigint_size).toString
    )
  }

  def getRandomNumber(digits:Int) = {
    System.nanoTime()
  }

}
