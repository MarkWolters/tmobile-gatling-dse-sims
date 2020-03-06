package feeds.tmobile.payment.cql

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

class Payment_duplicate_check_Feed   extends BaseFeed with LazyLogging{


  def getPayment_duplicate_check = {
    def rowData = this.getRowData


    Iterator.continually(rowData)
  }

  def getRowData = {

    val bigint_size = 12

    Map(
      "transaction_type" -> getRandomNumber(bigint_size).toString,
      "application_id" -> getRandomNumber(bigint_size).toString,
      "amount" -> getRandomNumber(bigint_size).toDouble,
      "billing_account_number" -> getRandomNumber(bigint_size).toString,
      "common_customer_id" -> getRandomNumber(bigint_size).toString,
      "transaction_date" -> getRandomNumber(bigint_size).toString,
      "order_number" -> getRandomNumber(bigint_size).toString,
      "payment_instrument_last4" -> getRandomNumber(bigint_size).toString,
      "transaction_timestamp" -> getCurrentTimestamp,
      "created_by" -> getRandomNumber(bigint_size).toString,
      "created_date" -> getCurrentTimestamp,
      "dps_shortcode" -> getRandomNumber(bigint_size).toString,
      "modified_by" -> getRandomNumber(bigint_size).toString,
      "modified_date" -> getCurrentTimestamp,
      "status" -> getRandomNumber(bigint_size).toString
    )
  }

  def getRandomNumber(digits:Int) = {
    System.nanoTime()
  }

}
