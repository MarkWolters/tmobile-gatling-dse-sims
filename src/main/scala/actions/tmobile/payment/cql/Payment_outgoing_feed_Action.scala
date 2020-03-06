package actions.tmobile.payment.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class Payment_outgoing_feed_Action(cassandra: Cassandra, simConfig: SimConfig) extends BaseAction(cassandra, simConfig) {

  private val tableName = "payment_outgoing_feed"

  createKeyspace
  createTable()

  private val writePayment_outgoing_feedQuery: Insert = QueryBuilder.insertInto(keyspace, tableName)
    .value("batch_id ", raw("?"))
    .value("activity_id ", raw("?"))
    .value("acquirer_approval_code ", raw("?"))
    .value("acquirer_avs_response_code ", raw("?"))
    .value("acquirer_cvn_response_code ", raw("?"))
    .value("acquirer_cvv_response_code ", raw("?"))
    .value("acquirer_name ", raw("?"))
    .value("acquirer_reference_number ", raw("?"))
    .value("acquirer_transaction_id ", raw("?"))
    .value("amount ", raw("?"))
    .value("application_id ", raw("?"))
    .value("approx_interchange_fee_amt ", raw("?"))
    .value("backout_fail_reason_code ", raw("?"))
    .value("backout_fail_reason_code_desc ", raw("?"))
    .value("backout_reason_code ", raw("?"))
    .value("bck_rsn_code ", raw("?"))
    .value("biller_code ", raw("?"))
    .value("billing_account_number ", raw("?"))
    .value("bin ", raw("?"))
    .value("business_date", raw("?"))
    .value("business_segment ", raw("?"))
    .value("business_unit ", raw("?"))
    .value("card_brand ", raw("?"))
    .value("card_holder_phone ", raw("?"))
    .value("card_last_4 ", raw("?"))
    .value("cb_reason_code_classification ", raw("?"))
    .value("cb_type_indicator ", raw("?"))
    .value("cb_usage_code ", raw("?"))
    .value("channel ", raw("?"))
    .value("charge_back_gen_status ", raw("?"))
    .value("charge_back_posting_method ", raw("?"))
    .value("charge_back_posting_status ", raw("?"))
    .value("charge_back_reason_code ", raw("?"))
    .value("charge_back_type_indicator ", raw("?"))
    .value("check_number ", raw("?"))
    .value("common_customer_id ", raw("?"))
    .value("created_by ", raw("?"))
    .value("created_date ", raw("?"))
    .value("customer_first_name ", raw("?"))
    .value("customer_last_name ", raw("?"))
    .value("customer_name ", raw("?"))
    .value("customer_presence_code ", raw("?"))
    .value("debit_passthrough_fees ", raw("?"))
    .value("dps_payment_id ", raw("?"))
    .value("file_type ", raw("?"))
    .value("gateway_batch_id ", raw("?"))
    .value("indemnification_indicator ", raw("?"))
    .value("interchange_flat_rate ", raw("?"))
    .value("interchange_percent_rate ", raw("?"))
    .value("interchange_rate ", raw("?"))
    .value("last_modified_timestamp ", raw("?"))
    .value("merchant_id ", raw("?"))
    .value("modified_by ", raw("?"))
    .value("modified_date ", raw("?"))
    .value("msisdn ", raw("?"))
    .value("ni_feed_gen_status ", raw("?"))
    .value("operator_id ", raw("?"))
    .value("order_id ", raw("?"))
    .value("order_type ", raw("?"))
    .value("orginal_transaction_date ", raw("?"))
    .value("parent_payment_id ", raw("?"))
    .value("payment_category ", raw("?"))
    .value("payment_method ", raw("?"))
    .value("payment_network_id ", raw("?"))
    .value("payment_status ", raw("?"))
    .value("payment_status_reason ", raw("?"))
    .value("payment_status_reason_code ", raw("?"))
    .value("payment_submission_method ", raw("?"))
    .value("payment_subtype_code ", raw("?"))
    .value("payment_type_code ", raw("?"))
    .value("payment_type_desc ", raw("?"))
    .value("pde_file_id ", raw("?"))
    .value("pos_entry_mode ", raw("?"))
    .value("posting_amount ", raw("?"))
    .value("posting_conf_number ", raw("?"))
    .value("posting_destination ", raw("?"))
    .value("posting_source_id ", raw("?"))
    .value("posting_status ", raw("?"))
    .value("product_group ", raw("?"))
    .value("program_code ", raw("?"))
    .value("reason_description ", raw("?"))
    .value("register_number ", raw("?"))
    .value("settlement_date ", raw("?"))
    .value("settlement_status ", raw("?"))
    .value("settlement_status_code ", raw("?"))
    .value("src_file_name ", raw("?"))
    .value("store_id ", raw("?"))
    .value("tax_amount ", raw("?"))
    .value("total_assessment_amt ", raw("?"))
    .value("total_interchange_amt ", raw("?"))
    .value("transaction_id ", raw("?"))
    .value("transaction_status ", raw("?"))

  def writeRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writePayment_outgoing_feedQuery)

    group(Groups.INSERT) {
      exec(cql("writeRecords")
        .executeNamed(preparedStatement)

        .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM) // ConsistencyLevel can be set per query
        .check(rowCount is 0) // an insert should not return rows
      )
    }
  }
/*
"select * from payment_outgoing_feed where batch_id=?0"
 */
  val readPayment_outgoing_feedQuery = QueryBuilder.select("batch_id", "activity_id", "acquirer_approval_code", "acquirer_avs_response_code", "acquirer_cvn_response_code", "acquirer_cvv_response_code")
    .from(keyspace, tableName)
    .limit(10)

  def readRecords:  ChainBuilder = {

    val preparedStatement = session.prepare(readPayment_outgoing_feedQuery)

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
           |CREATE TABLE IF NOT EXISTS $keyspace.$tableName  (
           |batch_id text,
           |activity_id uuid,
           |acquirer_approval_code text,
           |acquirer_avs_response_code text,
           |acquirer_cvn_response_code text,
           |acquirer_cvv_response_code text,
           |acquirer_name text,
           |acquirer_reference_number text,
           |acquirer_transaction_id text,
           |amount double,
           |application_id text,
           |approx_interchange_fee_amt double,
           |backout_fail_reason_code text,
           |backout_fail_reason_code_desc text,
           |backout_reason_code text,
           |bck_rsn_code text,
           |biller_code text,
           |billing_account_number text,
           |bin text,
           |business_date date,
           |business_segment text,
           |business_unit text,
           |card_brand text,
           |card_holder_phone text,
           |card_last_4 text,
           |cb_reason_code_classification int,
           |cb_type_indicator text,
           |cb_usage_code int,
           |channel text,
           |charge_back_gen_status text,
           |charge_back_posting_method text,
           |charge_back_posting_status text,
           |charge_back_reason_code text,
           |charge_back_type_indicator text,
           |check_number text,
           |common_customer_id text,
           |created_by text,
           |created_date timestamp,
           |customer_first_name text,
           |customer_last_name text,
           |customer_name text,
           |customer_presence_code text,
           |debit_passthrough_fees double,
           |dps_payment_id bigint,
           |file_type text,
           |gateway_batch_id bigint,
           |indemnification_indicator text,
           |interchange_flat_rate double,
           |interchange_percent_rate double,
           |interchange_rate text,
           |last_modified_timestamp timestamp,
           |merchant_id text,
           |modified_by text,
           |modified_date timestamp,
           |msisdn text,
           |ni_feed_gen_status text,
           |operator_id text,
           |order_id text,
           |order_type text,
           |orginal_transaction_date timestamp,
           |parent_payment_id bigint,
           |payment_category text,
           |payment_method text,
           |payment_network_id text,
           |payment_status text,
           |payment_status_reason text,
           |payment_status_reason_code text,
           |payment_submission_method text,
           |payment_subtype_code text,
           |payment_type_code text,
           |payment_type_desc text,
           |pde_file_id text,
           |pos_entry_mode text,
           |posting_amount text,
           |posting_conf_number text,
           |posting_destination text,
           |posting_source_id text,
           |posting_status text,
           |product_group text,
           |program_code text,
           |reason_description text,
           |register_number text,
           |settlement_date timestamp,
           |settlement_status text,
           |settlement_status_code text,
           |src_file_name text,
           |store_id text,
           |tax_amount double,
           |total_assessment_amt double,
           |total_interchange_amt double,
           |transaction_id text,
           |transaction_status text,
           |PRIMARY KEY (batch_id, activity_id)
           |) WITH CLUSTERING ORDER BY (activity_id DESC);"""
          .stripMargin
        )
    )
  }
}
