package actions.tmobile.payment.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class Stored_payment_method_Action (cassandra: Cassandra, simConfig: SimConfig) extends BaseAction(cassandra, simConfig) {

  private val tableName = "Stored_payment_method"

  createKeyspace
  createTable()

  private val writeStored_payment_methodQuery: Insert = QueryBuilder.insertInto(keyspace, tableName)
    .value("wallet_id", raw("?"))
    .value("tmotoken", raw("?"))
    .value("account_holder_first_name", raw("?"))
    .value("account_holder_full_name", raw("?"))
    .value("account_holder_last_name", raw("?"))
    .value("address_line_1", raw("?"))
    .value("address_line_2", raw("?"))
    .value("applicationname", raw("?"))
    .value("autopay_indicator", raw("?"))
    .value("biller_code", raw("?"))
    .value("business_segment", raw("?"))
    .value("business_unit", raw("?"))
    .value("channel", raw("?"))
    .value("city_name", raw("?"))
    .value("created_by", raw("?"))
    .value("created_date", raw("?"))
    .value("customer_id_blocked", raw("?"))
    .value("dps_payment_id", raw("?"))
    .value("expiration_month_year", raw("?"))
    .value("last_use_date", raw("?"))
    .value("legacytoken", raw("?"))
    .value("modified_by", raw("?"))
    .value("modified_date", raw("?"))
    .value("nick_name", raw("?"))
    .value("payment_device_id", raw("?"))
    .value("payment_method_code", raw("?"))
    .value("payment_method_status", raw("?"))
    .value("preferred_payment_device", raw("?"))
    .value("program_code", raw("?"))
    .value("routing_number", raw("?"))
    .value("sec", raw("?"))
    .value("state_code", raw("?"))
    .value("status", raw("?"))
    .value("terms_agreement_indicator", raw("?"))
    .value("terms_agreement_time", raw("?"))
    .value("type_code", raw("?"))
    .value("zip", raw("?"))

  def writeRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writeStored_payment_methodQuery)

    group(Groups.INSERT) {
      exec(cql("writeRecords")
        .executeNamed(preparedStatement)

        .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM) // ConsistencyLevel can be set per query
        .check(rowCount is 0) // an insert should not return rows
      )
    }
  }
/*
SELECT * from stored_payment_method where wallet_id=:walletId and tmotoken=:tmotoken
 */
  val readStored_payment_methodQuery = QueryBuilder.select("wallet_id", "tmotoken", "account_holder_first_name", "account_holder_full_name", "account_holder_last_name", "address_line_1", "applicationname")
    .from(keyspace, tableName)
    .limit(10)

  def readRecords:  ChainBuilder = {

    val preparedStatement = session.prepare(readStored_payment_methodQuery)

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
           |wallet_id text,
           |tmotoken text,
           |account_holder_first_name text,
           |account_holder_full_name text,
           |account_holder_last_name text,
           |address_line_1 text,
           |address_line_2 text,
           |applicationname text,
           |autopay_indicator boolean,
           |biller_code text,
           |business_segment text,
           |business_unit text,
           |channel text,
           |city_name text,
           |created_by text,
           |created_date timestamp,
           |customer_id_blocked boolean,
           |dps_payment_id bigint,
           |expiration_month_year text,
           |last_use_date timestamp,
           |legacytoken text,
           |modified_by text,
           |modified_date timestamp,
           |nick_name text,
           |payment_device_id text,
           |payment_method_code text,
           |payment_method_status text,
           |preferred_payment_device boolean,
           |program_code text,
           |routing_number text,
           |sec text,
           |state_code text,
           |status text,
           |terms_agreement_indicator boolean,
           |terms_agreement_time timestamp,
           |type_code text,
           |zip text,
           |PRIMARY KEY (wallet_id, tmotoken)
           |) WITH CLUSTERING ORDER BY (tmotoken ASC);"""
          .stripMargin
        )
    )
  }
}
