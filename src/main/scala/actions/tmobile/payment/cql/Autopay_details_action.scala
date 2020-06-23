package actions.tmobile.payment.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class Autopay_details_action(cassandra: Cassandra, simConfig: SimConfig)  extends BaseAction(cassandra, simConfig) {

  private val tableName = "autopay_details"

  createKeyspace
  createTable()

  private val writeAutopayDetails: Insert = QueryBuilder.insertInto(keyspace, tableName)
    .value("batch_id", raw("?"))
    .value("common_customer_id", raw("?"))
    .value("address_line_1", raw("?"))
    .value("address_line_2", raw("?"))
    .value("autopay_request_id", raw("?"))
    .value("business_unit", raw("?"))
    .value("card_brand", raw("?"))
    .value("city", raw("?"))
    .value("created_by", raw("?"))
    .value("created_date", raw("?"))
    .value("customer_name", raw("?"))
    .value("error_description", raw("?"))
    .value("expiration_month_year", raw("?"))
    .value("file_name", raw("?"))
    .value("file_sequence_number", raw("?"))
    .value("file_type", raw("?"))
    .value("financial_account_number", raw("?"))
    .value("group_id", raw("?"))
    .value("last_run_date", raw("?"))
    .value("modified_by", raw("?"))
    .value("modified_date", raw("?"))
    .value("payment_due_date", raw("?"))
    .value("payment_request_date", raw("?"))
    .value("payment_token", raw("?"))
    .value("product_group", raw("?"))
    .value("program_code", raw("?"))
    .value("requested_amount", raw("?"))
    .value("routing_number", raw("?"))
    .value("state", raw("?"))
    .value("status", raw("?"))
    .value("zip", raw("?"))

  def writeRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writeAutopayDetails)

    group(Groups.INSERT) {
      exec(cql("writeRecords")
        .executeNamed(preparedStatement)

        .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM) // ConsistencyLevel can be set per query
        .check(rowCount is 0) // an insert should not return rows
      )
    }
  }

  def createTable(): Unit = {
    runQueries(
      Array(
        s"""
           |CREATE TABLE IF NOT EXISTS $keyspace.$tableName  (
           |batch_id text,
           |common_customer_id text,
           |address_line_1 text,
           |address_line_2 text,
           |autopay_request_id text,
           |business_unit text,
           |card_brand text,
           |city text,
           |created_by text,
           |created_date timestamp,
           |customer_name text,
           |error_description text,
           |expiration_month_year text,
           |file_name text,
           |file_sequence_number text,
           |file_type text,
           |financial_account_number text,
           |group_id int,
           |last_run_date timestamp,
           |modified_by text,
           |modified_date timestamp,
           |payment_due_date timestamp,
           |payment_request_date timestamp,
           |payment_token text,
           |product_group text,
           |program_code text,
           |requested_amount double,
           |routing_number text,
           |state text,
           |status text,
           |zip text,
           |PRIMARY KEY (batch_id, common_customer_id)
           |) WITH CLUSTERING ORDER BY (common_customer_id DESC);"""
          .stripMargin
      )
    )
  }
}
