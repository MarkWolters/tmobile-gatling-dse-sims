package feeds.tmobile.payment.cql

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

class Acquirer_Transactions_Detail_Feed
  extends BaseFeed with LazyLogging{

  def getAcquirer_Transaction_Details = {
    def rowData= this.getRowData

    Iterator.continually(rowData)
  }

  def getRowData = {

    val bigint_size = 12

    Map(
      "dps_payment_id" -> getRandomNumber(bigint_size).toInt,
      "process_id" -> getRandomNumber(bigint_size).toString,
      "acquirer_api" -> getRandomNumber(bigint_size).toString,
      "acquirer_name" -> faker.name.firstName,
      "acquirer_parent_payment_id" -> getRandomNumber(bigint_size).toString,
      "acquirer_paymentid" -> getRandomNumber(bigint_size).toString,
      "acquirer_response_code" -> getRandomNumber(bigint_size).toString,
      "acquirer_response_message" -> getRandomNumber(bigint_size).toString,
      "acquirer_settled_batch_id" -> getRandomNumber(bigint_size).toString,
      "acquirer_settled_response_code" -> getRandomNumber(bigint_size).toString,
      "acquirer_settled_timestamp" ->  getCurrentTimestamp,
      "acquirer_settlement_id" -> getRandomNumber(bigint_size).toString,
      "acquirer_token" -> getRandomNumber(bigint_size).toString,
      "merchant_routing_id" -> getRandomNumber(bigint_size).toString
    )
  }

  def getRandomNumber(digits:Int) = {
    System.nanoTime()
  }

}
