package actions.tmobile.payment.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class Acquirer_Transactions_Detail_Action(cassandra: Cassandra, simConfig: SimConfig) extends BaseAction(cassandra, simConfig) {

  private val tableName = "acquirer_transactions_detail"

  createKeyspace
  createTable()

  private val writeTransactionsDetailQuery: Insert = QueryBuilder.insertInto(keyspace, tableName)
    .value("dps_payment_id", raw("?"))
    .value("process_id", raw("?"))
    .value("acquirer_api", raw("?"))
    .value("acquirer_name", raw("?"))
    .value("acquirer_parent_payment_id", raw("?"))
    .value("acquirer_paymentid", raw("?"))
    .value("acquirer_response_code", raw("?"))
    .value("acquirer_response_message", raw("?"))
    .value("acquirer_settled_batch_id", raw("?"))
    .value("acquirer_settled_response_code", raw("?"))
    .value("acquirer_settled_timestamp", raw("?"))
    .value("acquirer_settlement_id", raw("?"))
    .value("acquirer_token", raw("?"))
    .value("merchant_routing_id", raw("?"))


  def LoadRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writeTransactionsDetailQuery)

    exec(cql("LoadRecords")
      .executeNamed(preparedStatement)
      .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM)
      .check(rowCount.saveAs("cnt"))
    ).exec {s: Session =>
      if(s.get("cnt").as[Int]>=1){
        logger.error(preparedStatement.toString)

      }
      s
    }
  }

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


  val readTransactionsDetailQuery = QueryBuilder.select("dps_payment_id", "process_id", "acquirer_api", "acquirer_name", "acquirer_parent_payment_id", "acquirer_paymentid", "acquirer_response_code")
    .from(keyspace, table)
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
        s"""|CREATE TABLE IF NOT EXISTS $keyspace.$tableName (
            |dps_payment_id bigint,
            |process_id text,
            |acquirer_api text,
            |acquirer_name text,
            |acquirer_parent_payment_id text,
            |acquirer_paymentid text,
            |acquirer_response_code text,
            |acquirer_response_message text,
            |acquirer_settled_batch_id text,
            |acquirer_settled_response_code text,
            |acquirer_settled_timestamp timestamp,
            |acquirer_settlement_id text,
            |acquirer_token text,
            |merchant_routing_id text,
            |src_settlement_filename text,
            |txn_ref_idx int,
            |PRIMARY KEY (dps_payment_id, process_id)
            |) WITH CLUSTERING ORDER BY (process_id ASC);"""
          .stripMargin
      )
    )
  }
}
