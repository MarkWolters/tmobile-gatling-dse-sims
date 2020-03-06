package feeds.tmobile.payment.cql

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

class Batch_pymt_txn_dtls_by_batchid_Feed
  extends BaseFeed with LazyLogging {

  def getBatch_pymt_txn_dtls_by_batchid = {
    def rowData = this.getRowData


    Iterator.continually(rowData)
  }

  def getRowData = {

    val bigint_size = 12

    Map(
      "batch_id" -> getRandomNumber(bigint_size).toString,
      "tmotoken" -> getRandomNumber(bigint_size).toString,
      "account_number" -> getRandomNumber(bigint_size).toString,
      "dps_payment_id" -> getRandomNumber(bigint_size),
      "account_name" -> getRandomNumber(bigint_size).toString,
      "acquirer_message" -> getRandomNumber(bigint_size).toString,
      "acquirer_name" -> getRandomNumber(bigint_size).toString,
      "acquirer_response_code" -> getRandomNumber(bigint_size).toString,
      "acquirer_response_message" -> getRandomNumber(bigint_size).toString,
      "address_line1" -> getRandomNumber(bigint_size).toString,
      "address_line2" -> getRandomNumber(bigint_size).toString,
      "application_id" -> getRandomNumber(bigint_size).toString,
      "auth_code" -> getRandomNumber(bigint_size).toString,
      "auth_recycle_desc" -> getRandomNumber(bigint_size).toString,
      "auth_recycled_status" -> getRandomNumber(bigint_size).toString,
      "authorized_amount" -> getRandomNumber(bigint_size).toDouble,
      "autopay_request_id" -> getRandomNumber(bigint_size).toString,
      "avs_code" -> getRandomNumber(bigint_size).toString,
      "batch_type" -> getRandomNumber(bigint_size).toString,
      "biller_code" -> getRandomNumber(bigint_size).toString,
      "business_unit" -> getRandomNumber(bigint_size).toString,
      "card_brand" -> getRandomNumber(bigint_size).toString,
      "card_first6" -> getRandomNumber(bigint_size).toString,
      "card_last4" -> getRandomNumber(bigint_size).toString,
      "channel_id" -> getRandomNumber(bigint_size).toString,
      "city_name" -> getRandomNumber(bigint_size).toString,
      "common_customer_id" -> getRandomNumber(bigint_size).toString,
      "created_by" -> getRandomNumber(bigint_size).toString,
      "created_date" -> getCurrentTimestamp,
      "dps_error_code" -> getRandomNumber(bigint_size).toString,
      "error_description" -> getRandomNumber(bigint_size).toString,
      "expiration_date" -> getRandomNumber(bigint_size).toString,
      "export_batch_id" -> getRandomNumber(bigint_size).toString,
      "file_name" -> getRandomNumber(bigint_size).toString,
      "file_sequence_number" -> getRandomNumber(bigint_size).toString,
      "file_type" -> getRandomNumber(bigint_size).toString,
      "merchant_id" -> getRandomNumber(bigint_size).toString,
      "modified_by" -> getRandomNumber(bigint_size).toString,
      "modified_date" -> getCurrentTimestamp,
      "order_type" -> getRandomNumber(bigint_size).toString,
      "original_amount" -> getRandomNumber(bigint_size).toDouble,
      "original_payment_request_date" -> getCurrentTimestamp,
      "payment_category" -> getRandomNumber(bigint_size).toString,
      "payment_due_date" -> getCurrentTimestamp,
      "payment_method_code" -> getRandomNumber(bigint_size).toString,
      "payment_transaction_type" -> getRandomNumber(bigint_size).toString,
      "process_id" -> getRandomNumber(bigint_size).toString,
      "product_group" -> getRandomNumber(bigint_size).toString,
      "program_code" -> getRandomNumber(bigint_size).toString,
      "reason_code" -> getRandomNumber(bigint_size).toString,
      "response_code" -> getRandomNumber(bigint_size).toString,
      "routing_number" -> getRandomNumber(bigint_size).toString,
      "state_code" -> getRandomNumber(bigint_size).toString,
      "status" -> getRandomNumber(bigint_size).toString,
      "transaction_status_response" -> getRandomNumber(bigint_size).toString,
      "zip" -> getRandomNumber(bigint_size).toString
    )
  }

  def getRandomNumber(digits:Int) = {
    System.nanoTime()
  }
  
}
