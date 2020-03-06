package actions.tmobile.payment.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class Wallet_Action (cassandra: Cassandra, simConfig: SimConfig) extends BaseAction(cassandra, simConfig) {

  private val tableName = "wallet"

  createKeyspace
  createTable()

  private val writeWalletQuery: Insert = QueryBuilder.insertInto(keyspace, tableName)
    .value("billing_account_number", raw("?"))
    .value("common_customer_id", raw("?"))
    .value("wallet_id", raw("?"))
    .value("created_by", raw("?"))
    .value("created_date", raw("?"))
    .value("modified_by", raw("?"))
    .value("modified_date", raw("?"))

  def writeRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writeWalletQuery)

    group(Groups.INSERT) {
      exec(cql("writeRecords")
        .executeNamed(preparedStatement)
        .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM) // ConsistencyLevel can be set per query
        .check(rowCount is 0) // an insert should not return rows
      )
    }
  }
  /*
  SELECT:
  SELECT billing_account_number, common_customer_id, wallet_id, created_by, created_date, modified_by, modified_date
  FROM payments_01.wallet
  WHERE billing_account_number='964863342' and common_customer_id='';

  SELECT * from wallet where billing_account_number=:customerId and common_customer_id=:commonCustomerId"
  SELECT * from wallet where billing_account_number=:customerId and common_customer_id=:commonCustomerId
  SELECT * from stored_payment_method where nick_name=:nickName
  SELECT * from stored_payment_method where wallet_id=:walletId
   */
  val readWalletQuery = QueryBuilder.select("billing_account_number", "common_customer_id", "wallet_id", "created_by", "created_by", "created_date", "modified_by", "modified_date")
    .from(keyspace, tableName)
    .limit(10)

  def readRecords:  ChainBuilder = {

    val preparedStatement = session.prepare(readWalletQuery)

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
           |billing_account_number text,
           |common_customer_id text,
           |wallet_id text,
           |created_by text,
           |created_date timestamp,
           |modified_by text,
           |modified_date timestamp,
           |PRIMARY KEY ((billing_account_number, common_customer_id), wallet_id)
           |) WITH CLUSTERING ORDER BY (wallet_id ASC);"""
          .stripMargin
      )
    )
  }
}

