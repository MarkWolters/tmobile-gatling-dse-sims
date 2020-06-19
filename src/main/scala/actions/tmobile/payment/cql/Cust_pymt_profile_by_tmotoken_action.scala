package actions.tmobile.payment.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class Cust_pymt_profile_by_tmotoken_action(cassandra: Cassandra, simConfig: SimConfig)  extends BaseAction(cassandra, simConfig) {

  private val tableName = "cust_pymt_profile_by_tmotoken"

  createKeyspace
  createTable()

  private val writeCustPymtProfileByTmotoken: Insert = QueryBuilder.insertInto(keyspace, tableName)
    .value("tmotoken", raw("?"))
    .value("id_type", raw("?"))
    .value("id", raw("?"))
    .value("autopay_indicator", raw("?"))
    .value("billing_account_number", raw("?"))
    .value("card_brand", raw("?"))
    .value("card_first_6", raw("?"))
    .value("card_last_4", raw("?"))
    .value("created_by", raw("?"))
    .value("created_date", raw("?"))
    .value("customer_mobile_phone", raw("?"))
    .value("dps_payment_id", raw("?"))
    .value("expiration_date", raw("?"))
    .value("legacytoken", raw("?"))
    .value("member_id", raw("?"))
    .value("modified_by", raw("?"))
    .value("modified_date", raw("?"))
    .value("payment_arrangement_indicator", raw("?"))
    .value("routing_number", raw("?"))
    .value("schedule_payment_indicator", raw("?"))
    .value("stored_payment_indicator", raw("?"))
    .value("wallet_id", raw("?"))
    .value("zip", raw("?"))

  def writeRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writeCustPymtProfileByTmotoken)

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
           |tmotoken text,
           |id_type text,
           |id text,
           |autopay_indicator boolean,
           |billing_account_number text,
           |card_brand text,
           |card_first_6 text,
           |card_last_4 text,
           |created_by text,
           |created_date timestamp,
           |customer_mobile_phone text,
           |dps_payment_id bigint,
           |expiration_date text,
           |legacytoken text,
           |member_id text,
           |modified_by text,
           |modified_date timestamp,
           |payment_arrangement_indicator boolean,
           |routing_number text,
           |schedule_payment_indicator boolean,
           |stored_payment_indicator boolean,
           |wallet_id text,
           |zip text,
           |PRIMARY KEY (tmotoken, id_type, id)
           |) WITH CLUSTERING ORDER BY (id_type DESC, id DESC);"""
          .stripMargin
      )
    )
  }
}
