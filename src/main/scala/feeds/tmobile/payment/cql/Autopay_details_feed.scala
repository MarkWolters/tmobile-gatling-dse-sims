package feeds.tmobile.payment.cql

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

class Autopay_details_feed
  extends BaseFeed with LazyLogging{

  def getAutopayDetailsFeed = {
    def rowData= this.getRowData

    Iterator.continually(rowData)
  }

  def getRowData = {

    val bigint_size = 12

    Map(
      "batch_id" -> getRandomNumber(bigint_size).toString,
      "common_customer_id" -> getRandomNumber(bigint_size).toString,
      "address_line_1" -> getRandomNumber(bigint_size).toString,
      "address_line_2" -> getRandomNumber(bigint_size).toString,
      "autopay_request_id" -> getRandomNumber(bigint_size).toString,
      "business_unit" -> getRandomNumber(bigint_size).toString,
      "card_brand" -> getRandomNumber(bigint_size).toString,
      "city" -> getRandomNumber(bigint_size).toString,
      "created_by" -> getRandomNumber(bigint_size).toString,
      "created_date" -> getCurrentTimestamp,
      "customer_name" ->  getRandomNumber(bigint_size).toString,
      "error_description" -> getRandomNumber(bigint_size).toString,
      "expiration_month_year" -> getRandomNumber(bigint_size).toString,
      "file_name" -> getRandomNumber(bigint_size).toString,
      "file_sequence_number" -> getRandomNumber(bigint_size).toString,
      "file_type" -> getRandomNumber(bigint_size).toString,
      "financial_account_number" -> getRandomNumber(bigint_size).toString,
      "group_id" -> getRandomNumber(bigint_size).toString,
      "last_run_date" -> getCurrentTimestamp,
      "modified_by" -> getRandomNumber(bigint_size).toString,
      "modified_date" -> getCurrentTimestamp,
      "payment_due_date" -> getCurrentTimestamp,
      "payment_request_date" -> getCurrentTimestamp,
      "payment_token" -> getRandomNumber(bigint_size).toString,
      "product_group" -> getRandomNumber(bigint_size).toString,
      "program_code" -> getRandomNumber(bigint_size).toString,
      "requested_amount" -> getRandomNumber(bigint_size).toDouble,
      "routing_number" -> getRandomNumber(bigint_size).toString,
      "state" -> getRandomNumber(bigint_size).toString,
      "status" -> getRandomNumber(bigint_size).toString,
      "zip" -> getRandomNumber(bigint_size).toString
    )
  }

  def getRandomNumber(digits:Int) = {
    System.nanoTime()
  }

}
