package actions.tmobile.payment.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class Payment_Lifecycle_Action(cassandra: Cassandra, simConfig: SimConfig) extends BaseAction(cassandra, simConfig) {

  private val tableName = "payment_lifecycle"

  createKeyspace
  createTable()

  private val writePaymentLifecycleQuery: Insert = QueryBuilder.insertInto(keyspace, tableName)
    .value("parent_payment_id", raw("?"))
    .value("transaction_type", raw("?"))
    .value("dps_payment_id", raw("?"))
    .value("reversal_retry_count", raw("?"))
    .value("status", raw("?"))

  def writeRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writePaymentLifecycleQuery)

    group(Groups.INSERT) {
      exec(cql("writeRecords")
        .executeNamed(preparedStatement)

        .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM) // ConsistencyLevel can be set per query
        .check(rowCount is 0) // an insert should not return rows
      )
    }
  }

  /*
  "ProcessRefund V3 :
""select * from payment_lifecycle where parent_payment_id=?0""

ProcessRefund V3 :
""select dps_payment_id from payment_lifecycle where parent_payment_id=?0""

ProcessRefund V3 :
""select dps_payment_id from payment_lifecycle where parent_payment_id=:paymentId and transaction_type in (:transactionTypeList)""

DigitalTransactionManagerV3 : INSERT INTO payment_lifecycle (parent_payment_id,transaction_type,dps_payment_id,reversal_retry_count,status) VALUES (1382349,'settlement',1382349,null,'COMPLETE');


PaymentTxnHistory V3 :
""select dps_payment_id from payment_lifecycle where parent_payment_id=:paymentId and transaction_type in (:transactionTypeList)"""

   */

  val readPaymentLifecycleQuery = QueryBuilder.select("parent_payment_id","transaction_type","dps_payment_id","reversal_retry_count","status")
    .from(keyspace, tableName)
    .limit(10)

  def readRecords:  ChainBuilder = {

    val preparedStatement = session.prepare(readPaymentLifecycleQuery)

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
           |parent_payment_id bigint,
           |transaction_type text,
           |dps_payment_id bigint,
           |reversal_retry_count int,
           |status text,
           |PRIMARY KEY (parent_payment_id, transaction_type, dps_payment_id)
           |) WITH CLUSTERING ORDER BY (transaction_type ASC, dps_payment_id ASC);"""
          .stripMargin
        )
    )
  }
}
