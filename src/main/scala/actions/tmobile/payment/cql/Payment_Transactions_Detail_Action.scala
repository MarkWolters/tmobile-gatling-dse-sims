package actions.tmobile.payment.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class Payment_Transactions_Detail_Action(cassandra: Cassandra, simConfig: SimConfig) extends BaseAction(cassandra, simConfig) {

  private val tableName = "payment_transactions_detail"

  createKeyspace
  createTable()

  private val writeTransactionsDetailQuery: Insert = QueryBuilder.insertInto(keyspace, tableName)
    .value(	"dps_payment_id"	, raw("?"))
    .value(	"process_id"	, raw("?"))
    .value(	"accuity_abarouting_number"	, raw("?"))
    .value(	"acquirer_approval_code"	, raw("?"))
    .value(	"acquirer_avs_responsecode "	, raw("?"))
    .value(	"acquirer_cvv_responsecode "	, raw("?"))
    .value(	"acquirer_name "	, raw("?"))
    .value(	"acquirer_parent_payment_id"	, raw("?"))
    .value(	"acquirer_payment_id"	, raw("?"))
    .value(	"address_matched "	, raw("?"))
    .value(	"amount"	, raw("?"))
    .value(	"application_id"	, raw("?"))
    .value(	"auth_hold_revrsal_action"	, raw("?"))
    .value(	"authorized_amount"	, raw("?"))
    .value(	"bank_name"	, raw("?"))
    .value(	"batch_id"	, raw("?"))
    .value(	"bill_due_date"	, raw("?"))
    .value(	"biller_code"	, raw("?"))
    .value(	"billing_account_number"	, raw("?"))
    .value(	"business_segment"	, raw("?"))
    .value(	"business_unit"	, raw("?"))
    .value(	"card_brand"	, raw("?"))
    .value(	"card_first_6"	, raw("?"))
    .value(	"card_last_4"	, raw("?"))
    .value(	"card_present"	, raw("?"))
    .value(	"channel_id"	, raw("?"))
    .value(	"charge_source"	, raw("?"))
    .value(	"chargeback_export_destination"	, raw("?"))
    .value(	"chargeback_export_status"	, raw("?"))
    .value(	"client_id"	, raw("?"))
    .value(	"common_customer_id"	, raw("?"))
    .value(	"created_by"	, raw("?"))
    .value(	"created_date"	, raw("?"))
    .value(	"customer_mobile_phone"	, raw("?"))
    .value(	"customer_name"	, raw("?"))
    .value(	"customer_present"	, raw("?"))
    .value(	"dps_response_code"	, raw("?"))
    .value(	"dps_response_message"	, raw("?"))
    .value(	"dps_shortcode"	, raw("?"))
    .value(	"entrymode"	, raw("?"))
    .value(	"expiration_date"	, raw("?"))
    .value(	"fraud_check_transaction_id"	, raw("?"))
    .value(	"fraud_decision_status"	, raw("?"))
    .value(	"guid"	, raw("?"))
    .value(	"import_batch_id"	, raw("?"))
    .value(	"legacytoken"	, raw("?"))
    .value(	"local_transaction_time"	, raw("?"))
    .value(	"manager_login_id"	, raw("?"))
    .value(	"merchant_routing_id"	, raw("?"))
    .value(	"modified_by"	, raw("?"))
    .value(	"modified_date"	, raw("?"))
    .value(	"operation_type"	, raw("?"))
    .value(	"operator_id"	, raw("?"))
    .value(	"order_number"	, raw("?"))
    .value(	"order_type"	, raw("?"))
    .value(	"parent_order_id"	, raw("?"))
    .value(	"parent_payment_id"	, raw("?"))
    .value(	"payment_instrument_account_number"	, raw("?"))
    .value(	"payment_instrument_category"	, raw("?"))
    .value(	"payment_instrument_holder_firstname"	, raw("?"))
    .value(	"payment_instrument_holder_fullname"	, raw("?"))
    .value(	"payment_instrument_holder_lastname"	, raw("?"))
    .value(	"payment_instrument_holder_phonenumber"	, raw("?"))
    .value(	"payment_method_code"	, raw("?"))
    .value(	"payment_type_code"	, raw("?"))
    .value(	"posting_destination"	, raw("?"))
    .value(	"posting_status"	, raw("?"))
    .value(	"prepaid_available_balance"	, raw("?"))
    .value(	"prepaid_card_indicator"	, raw("?"))
    .value(	"product_group"	, raw("?"))
    .value(	"program_code"	, raw("?"))
    .value(	"refunded_amount"	, raw("?"))
    .value(	"remaining_balance"	, raw("?"))
    .value(	"reversal_action"	, raw("?"))
    .value(	"routing_number"	, raw("?"))
    .value(	"settlement_batch_id"	, raw("?"))
    .value(	"settlement_required"	, raw("?"))
    .value(	"settlement_status"	, raw("?"))
    .value(	"status"	, raw("?"))
    .value(	"status_modified_date"	, raw("?"))
    .value(	"store_id"	, raw("?"))
    .value(	"stored_payment_indicator"	, raw("?"))
    .value(	"surcharge_amount"	, raw("?"))
    .value(	"tax_amount"	, raw("?"))
    .value(	"terminalid"	, raw("?"))
    .value(	"termscond_accepted"	, raw("?"))
    .value(	"termscond_accepted_timestamp"	, raw("?"))
    .value(	"termscond_application_id"	, raw("?"))
    .value(	"timeout_reversal"	, raw("?"))
    .value(	"tmotoken"	, raw("?"))
    .value(	"transaction_date"	, raw("?"))
    .value(	"transaction_type"	, raw("?"))
    .value(	"void_reason"	, raw("?"))
    .value(	"zip_matched"	, raw("?"))


  def writeRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writeTransactionsDetailQuery)

    group(Groups.INSERT) {
      exec(cql("writeRecords")
        .executeNamed(preparedStatement)

        .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM) // ConsistencyLevel can be set per query
        .check(rowCount is 0) // an insert should not return rows
      )
    }
  }

  val readTransactionsDetailQuery = QueryBuilder.select("dps_payment_id", "process_id", "accuity_abarouting_number", "acquirer_approval_code", "acquirer_avs_responsecode", "acquirer_cvv_responsecode")
    .from(keyspace, tableName)
    .limit(10)

  def readRecords:  ChainBuilder = {

    val preparedStatement = session.prepare(readTransactionsDetailQuery)

    group(Groups.SELECT) {
      exec(cql("readRecords")
        .executeNamed(preparedStatement)
        .check(rowCount greaterThan 0)
      )
    }
  }

  def createTable(): Unit = {
    runQueries(
      Array(
        s"""
           |CREATE TABLE IF NOT EXISTS $keyspace.$tableName (
           |dps_payment_id bigint,
           |process_id text,
           |accuity_abarouting_number text,
           |acquirer_approval_code text,
           |acquirer_avs_responsecode text,
           |acquirer_cvv_responsecode text,
           |acquirer_name text,
           |acquirer_parent_payment_id text,
           |acquirer_payment_id text,
           |address_matched text,
           |amount double,
           |application_id text,
           |auth_hold_revrsal_action text,
           |authorized_amount double,
           |bank_name text,
           |batch_id text,
           |bill_due_date date,
           |biller_code text,
           |billing_account_number text,
           |business_segment text,
           |business_unit text,
           |card_brand text,
           |card_first_6 text,
           |card_last_4 text,
           |card_present boolean,
           |channel_id text,
           |charge_source text,
           |chargeback_export_destination text,
           |chargeback_export_status text,
           |client_id text,
           |common_customer_id text,
           |created_by text,
           |created_date timestamp,
           |customer_mobile_phone text,
           |customer_name text,
           |customer_present boolean,
           |dps_response_code text,
           |dps_response_message text,
           |dps_shortcode text,
           |entrymode text,
           |expiration_date text,
           |fraud_check_transaction_id text,
           |fraud_decision_status text,
           |guid text,
           |import_batch_id text,
           |legacytoken text,
           |local_transaction_time timestamp,
           |manager_login_id text,
           |merchant_routing_id text,
           |modified_by text,
           |modified_date timestamp,
           |operation_type text,
           |operator_id text,
           |order_number text,
           |order_type text,
           |parent_order_id text,
           |parent_payment_id bigint,
           |payment_instrument_account_number text,
           |payment_instrument_category text,
           |payment_instrument_holder_firstname text,
           |payment_instrument_holder_fullname text,
           |payment_instrument_holder_lastname text,
           |payment_instrument_holder_phonenumber text,
           |payment_method_code text,
           |payment_type_code text,
           |posting_destination text,
           |posting_status text,
           |prepaid_available_balance text,
           |prepaid_card_indicator boolean,
           |product_group text,
           |program_code text,
           |refunded_amount double,
           |remaining_balance double,
           |reversal_action text,
           |routing_number text,
           |settlement_batch_id text,
           |settlement_required boolean,
           |settlement_status text,
           |status text,
           |status_modified_date timestamp,
           |store_id text,
           |stored_payment_indicator boolean,
           |surcharge_amount double,
           |tax_amount double,
           |terminalid text,
           |termscond_accepted boolean,
           |termscond_accepted_timestamp timestamp,
           |termscond_application_id text,
           |timeout_reversal text,
           |tmotoken text,
           |transaction_date timestamp,
           |transaction_type text,
           |void_reason text,
           |zip_matched text,
           |PRIMARY KEY (dps_payment_id, process_id)
           |) WITH CLUSTERING ORDER BY (process_id ASC);"""
          .stripMargin
      )
    )
  }
}

