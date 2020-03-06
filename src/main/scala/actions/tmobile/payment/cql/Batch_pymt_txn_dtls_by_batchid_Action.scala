package actions.tmobile.payment.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class Batch_pymt_txn_dtls_by_batchid_Action(cassandra: Cassandra, simConfig: SimConfig) extends BaseAction(cassandra, simConfig) {

  private val tableName = "batch_pymt_txn_dtls_by_batchid"

  createKeyspace
  createTable()

  private val writebatch_pymt_txn_dtls_by_batchidQuery: Insert = QueryBuilder.insertInto(keyspace, tableName)
    .value("batch_id", raw("?"))
    .value("tmotoken", raw("?"))
    .value("account_number", raw("?"))
    .value("dps_payment_id", raw("?"))
    .value("account_name", raw("?"))
    .value("acquirer_message", raw("?"))
    .value("acquirer_name", raw("?"))
    .value("acquirer_response_code", raw("?"))
    .value("acquirer_response_message", raw("?"))
    .value("address_line1", raw("?"))
    .value("address_line2", raw("?"))
    .value("application_id", raw("?"))
    .value("auth_code", raw("?"))
    .value("auth_recycle_desc", raw("?"))
    .value("auth_recycled_status", raw("?"))
    .value("authorized_amount", raw("?"))
    .value("autopay_request_id", raw("?"))
    .value("avs_code", raw("?"))
    .value("batch_type", raw("?"))
    .value("biller_code", raw("?"))
    .value("business_unit", raw("?"))
    .value("card_brand", raw("?"))
    .value("card_first6", raw("?"))
    .value("card_last4", raw("?"))
    .value("channel_id", raw("?"))
    .value("city_name", raw("?"))
    .value("common_customer_id", raw("?"))
    .value("created_by", raw("?"))
    .value("created_date", raw("?"))
    .value("dps_error_code", raw("?"))
    .value("error_description", raw("?"))
    .value("expiration_date", raw("?"))
    .value("export_batch_id", raw("?"))
    .value("file_name", raw("?"))
    .value("file_sequence_number", raw("?"))
    .value("file_type", raw("?"))
    .value("merchant_id", raw("?"))
    .value("modified_by", raw("?"))
    .value("modified_date", raw("?"))
    .value("order_type", raw("?"))
    .value("original_amount", raw("?"))
    .value("original_payment_request_date", raw("?"))
    .value("payment_category", raw("?"))
    .value("payment_due_date", raw("?"))
    .value("payment_method_code", raw("?"))
    .value("payment_transaction_type", raw("?"))
    .value("process_id", raw("?"))
    .value("product_group", raw("?"))
    .value("program_code", raw("?"))
    .value("reason_code", raw("?"))
    .value("response_code", raw("?"))
    .value("routing_number", raw("?"))
    .value("state_code", raw("?"))
    .value("status", raw("?"))
    .value("transaction_status_response", raw("?"))
    .value("zip", raw("?"))

  def writeRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writebatch_pymt_txn_dtls_by_batchidQuery)

    group(Groups.INSERT) {
      exec(cql("writeRecords")
        .executeNamed(preparedStatement)

        .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM) // ConsistencyLevel can be set per query
        .check(rowCount is 0) // an insert should not return rows
      )
    }
  }

  def writeRecords1: ChainBuilder = {

    val preparedStatement = session.prepare(writebatch_pymt_txn_dtls_by_batchidQuery)

    group(Groups.INSERT) {
      exec(cql("writeRecords")
        .executeNamed(preparedStatement)
        .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM) // ConsistencyLevel can be set per query
        .check(rowCount is 0) // an insert should not return rows
      )
    }
  }

  val readbatch_pymt_txn_dtls_by_batchidQuery = QueryBuilder.select("batch_id", "tmotoken", "account_number", "dps_payment_id", "account_name", "acquirer_message", "acquirer_name")
    .from(keyspace, tableName)
    .limit(10)

  def readRecords:  ChainBuilder = {

    val preparedStatement = session.prepare(readbatch_pymt_txn_dtls_by_batchidQuery)

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
           |batch_id text,
           |tmotoken text,
           |account_number text,
           |dps_payment_id bigint,
           |account_name text,
           |acquirer_message text,
           |acquirer_name text,
           |acquirer_response_code text,
           |acquirer_response_message text,
           |address_line1 text,
           |address_line2 text,
           |application_id text,
           |auth_code text,
           |auth_recycle_desc text,
           |auth_recycled_status text,
           |authorized_amount double,
           |autopay_request_id text,
           |avs_code text,
           |batch_type text,
           |biller_code text,
           |business_unit text,
           |card_brand text,
           |card_first6 text,
           |card_last4 text,
           |channel_id text,
           |city_name text,
           |common_customer_id text,
           |created_by text,
           |created_date timestamp,
           |dps_error_code text,
           |error_description text,
           |expiration_date text,
           |export_batch_id text,
           |file_name text,
           |file_sequence_number text,
           |file_type text,
           |merchant_id text,
           |modified_by text,
           |modified_date timestamp,
           |order_type text,
           |original_amount double,
           |original_payment_request_date timestamp,
           |payment_category text,
           |payment_due_date timestamp,
           |payment_method_code text,
           |payment_transaction_type text,
           |process_id text,
           |product_group text,
           |program_code text,
           |reason_code text,
           |response_code text,
           |routing_number text,
           |state_code text,
           |status text,
           |transaction_status_response text,
           |zip text,
           |PRIMARY KEY (batch_id, tmotoken, account_number, dps_payment_id)
           |) WITH CLUSTERING ORDER BY (tmotoken DESC, account_number DESC, dps_payment_id DESC);"""
          .stripMargin
      )
    )
  }
}
