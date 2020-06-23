package actions.tmobile.payment.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class Wallet_tmoid_action(cassandra: Cassandra, simConfig: SimConfig)  extends BaseAction(cassandra, simConfig) {

  private val tableName = "wallet_tmoid_stg"

  createKeyspace
  createTable()

  private val writeWalletTmoidStg: Insert = QueryBuilder.insertInto(keyspace, tableName)
    .value("account_number", raw("?"))
    .value("tmoid", raw("?"))
    .value("account_status", raw("?"))
    .value("account_type", raw("?"))
    .value("created_by", raw("?"))
    .value("created_date", raw("?"))
    .value("dps_status_code", raw("?"))
    .value("error_description", raw("?"))
    .value("file_id", raw("?"))
    .value("modified_by", raw("?"))
    .value("modified_date", raw("?"))
    .value("retry_counter", raw("?"))
    .value("status", raw("?"))
    .value("wallet_id", raw("?"))

  def writeRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writeWalletTmoidStg)

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
           |account_number text,
           |tmoid text,
           |account_status text,
           |account_type text,
           |created_by text,
           |created_date timestamp,
           |dps_status_code text,
           |error_description text,
           |file_id text,
           |modified_by text,
           |modified_date timestamp,
           |retry_counter int,
           |status text,
           |wallet_id text,
           |PRIMARY KEY ((account_number, tmoid))
           |) ;"""
          .stripMargin
      )
    )
  }
}
