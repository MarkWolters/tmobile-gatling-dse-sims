package feeds.tmobile.payment.cql

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

class Payment_outgoing_feed_Feed
  extends BaseFeed with LazyLogging{


  def getPayment_outgoing_feed = {
    def rowData = this.getRowData


    Iterator.continually(rowData)
  }

  def getRowData = {

    val bigint_size = 12

    Map(
      "batch_id" -> getRandomNumber(bigint_size).toString,
      "activity_id" -> getUuid,
      "acquirer_approval_code" -> getRandomNumber(bigint_size).toString,
      "acquirer_avs_response_code" -> getRandomNumber(bigint_size).toString,
      "acquirer_cvn_response_code" -> getRandomNumber(bigint_size).toString,
      "acquirer_cvv_response_code" -> getRandomNumber(bigint_size).toString,
      "acquirer_name" -> getRandomNumber(bigint_size).toString,
      "acquirer_reference_number" -> getRandomNumber(bigint_size).toString,
      "acquirer_transaction_id" -> getRandomNumber(bigint_size).toString,
      "amount" -> getRandomNumber(bigint_size).toDouble,
      "application_id" -> getRandomNumber(bigint_size).toString,
      "approx_interchange_fee_amt" -> getRandomNumber(bigint_size).toDouble,
      "backout_fail_reason_code" -> getRandomNumber(bigint_size).toString,
      "backout_fail_reason_code_desc" -> getRandomNumber(bigint_size).toString,
      "backout_reason_code" -> getRandomNumber(bigint_size).toString,
      "bck_rsn_code" -> getRandomNumber(bigint_size).toString,
      "biller_code" -> getRandomNumber(bigint_size).toString,
      "billing_account_number" -> getRandomNumber(bigint_size).toString,
      "bin" -> getRandomNumber(bigint_size).toString,
      "business_date" -> getCurrentTimestamp,
      "business_segment" -> getRandomNumber(bigint_size).toString,
      "business_unit" -> getRandomNumber(bigint_size).toString,
      "card_brand" -> getRandomNumber(bigint_size).toString,
      "card_holder_phone" -> getRandomNumber(bigint_size).toString,
      "card_last_4" -> getRandomNumber(bigint_size).toString,
      "cb_reason_code_classification" -> getRandomNumber(bigint_size).toInt,
      "cb_type_indicator" -> getRandomNumber(bigint_size).toString,
      "cb_usage_code" -> getRandomNumber(bigint_size).toInt,
      "channel" -> getRandomNumber(bigint_size).toString,
      "charge_back_gen_status" -> getRandomNumber(bigint_size).toString,
      "charge_back_posting_method" -> getRandomNumber(bigint_size).toString,
      "charge_back_posting_status" -> getRandomNumber(bigint_size).toString,
      "charge_back_reason_code" -> getRandomNumber(bigint_size).toString,
      "charge_back_type_indicator" -> getRandomNumber(bigint_size).toString,
      "check_number" -> getRandomNumber(bigint_size).toString,
      "common_customer_id" -> getRandomNumber(bigint_size).toString,
      "created_by" -> getRandomNumber(bigint_size).toString,
      "created_date" -> getCurrentTimestamp,
      "customer_first_name" -> getRandomNumber(bigint_size).toString,
      "customer_last_name" -> getRandomNumber(bigint_size).toString,
      "customer_name" -> getRandomNumber(bigint_size).toString,
      "customer_presence_code" -> getRandomNumber(bigint_size).toString,
      "debit_passthrough_fees" -> getRandomNumber(bigint_size).toDouble,
      "dps_payment_id" -> getRandomNumber(bigint_size).toInt,
      "file_type" -> getRandomNumber(bigint_size).toString,
      "gateway_batch_id" -> getRandomNumber(bigint_size).toInt,
      "indemnification_indicator" -> getRandomNumber(bigint_size).toString,
      "interchange_flat_rate" -> getRandomNumber(bigint_size).toDouble,
      "interchange_percent_rate" -> getRandomNumber(bigint_size).toDouble,
      "interchange_rate" -> getRandomNumber(bigint_size).toString,
      "last_modified_timestamp" -> getCurrentTimestamp,
      "merchant_id" -> getRandomNumber(bigint_size).toString,
      "modified_by" -> getRandomNumber(bigint_size).toString,
      "modified_date" -> getCurrentTimestamp,
      "msisdn" -> getRandomNumber(bigint_size).toString,
      "ni_feed_gen_status" -> getRandomNumber(bigint_size).toString,
      "operator_id" -> getRandomNumber(bigint_size).toString,
      "order_id" -> getRandomNumber(bigint_size).toString,
      "order_type" -> getRandomNumber(bigint_size).toString,
      "orginal_transaction_date" -> getCurrentTimestamp,
      "parent_payment_id" -> getRandomNumber(bigint_size).toInt,
      "payment_category" -> getRandomNumber(bigint_size).toString,
      "payment_method" -> getRandomNumber(bigint_size).toString,
      "payment_network_id" -> getRandomNumber(bigint_size).toString,
      "payment_status" -> getRandomNumber(bigint_size).toString,
      "payment_status_reason" -> getRandomNumber(bigint_size).toString,
      "payment_status_reason_code" -> getRandomNumber(bigint_size).toString,
      "payment_submission_method" -> getRandomNumber(bigint_size).toString,
      "payment_subtype_code" -> getRandomNumber(bigint_size).toString,
      "payment_type_code" -> getRandomNumber(bigint_size).toString,
      "payment_type_desc" -> getRandomNumber(bigint_size).toString,
      "pde_file_id" -> getRandomNumber(bigint_size).toString,
      "pos_entry_mode" -> getRandomNumber(bigint_size).toString,
      "posting_amount" -> getRandomNumber(bigint_size).toString,
      "posting_conf_number" -> getRandomNumber(bigint_size).toString,
      "posting_destination" -> getRandomNumber(bigint_size).toString,
      "posting_source_id" -> getRandomNumber(bigint_size).toString,
      "posting_status" -> getRandomNumber(bigint_size).toString,
      "product_group" -> getRandomNumber(bigint_size).toString,
      "program_code" -> getRandomNumber(bigint_size).toString,
      "reason_description" -> getRandomNumber(bigint_size).toString,
      "register_number" -> getRandomNumber(bigint_size).toString,
      "settlement_date" -> getCurrentTimestamp,
      "settlement_status" -> getRandomNumber(bigint_size).toString,
      "settlement_status_code" -> getRandomNumber(bigint_size).toString,
      "src_file_name" -> getRandomNumber(bigint_size).toString,
      "store_id" -> getRandomNumber(bigint_size).toString,
      "tax_amount" -> getRandomNumber(bigint_size).toDouble,
      "total_assessment_amt" -> getRandomNumber(bigint_size).toDouble,
      "total_interchange_amt" -> getRandomNumber(bigint_size).toDouble,
      "transaction_id" -> getRandomNumber(bigint_size).toString,
      "transaction_status" -> getRandomNumber(bigint_size).toString
    )
  }

  def getRandomNumber(digits:Int) = {
    System.nanoTime()
  }
}
