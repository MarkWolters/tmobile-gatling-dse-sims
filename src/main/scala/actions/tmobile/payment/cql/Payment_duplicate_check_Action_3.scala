package actions.tmobile.payment.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class Payment_duplicate_check_Action_3(cassandra: Cassandra, simConfig: SimConfig)  extends BaseAction(cassandra, simConfig) {

  private val tableName = "payment_duplicate_check_3"

  createKeyspace
  createTable()

  private val writePayment_duplicate_checkQuery: Insert = QueryBuilder.insertInto(keyspace, tableName)
    .value("transaction_type", raw("?"))
    .value("application_id", raw("?"))
    .value("amount", raw("?"))
    .value("billing_account_number", raw("?"))
    .value("common_customer_id", raw("?"))
    .value("transaction_date", raw("?"))
    .value("order_number", raw("?"))
    .value("payment_instrument_last4", raw("?"))
    .value("transaction_timestamp", raw("?"))
    .value("created_by", raw("?"))
    .value("created_date", raw("?"))
    .value("dps_shortcode", raw("?"))
    .value("modified_by", raw("?"))
    .value("modified_date", raw("?"))
    .value("status", raw("?"))

  def writeRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writePayment_duplicate_checkQuery)

    group(Groups.INSERT) {
      exec(cql("writeRecords")
        .executeNamed(preparedStatement)

        .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM) // ConsistencyLevel can be set per query
        .check(rowCount is 0) // an insert should not return rows
      )
    }
  }

  /*
  "ProcessEcommerceSalesV3 : 1) select * from payment_duplicate_check where transaction_type=:transactionType and application_id=:applicationId and amount=:amount and billing_account_number=:billingAccountNumber and common_customer_id=:commonCustomerId and transaction_date IN (:transactionDate)
  2) select * from payment_duplicate_check where transaction_type=:transactionType and application_id=:applicationId and amount=:amount and billing_account_number=:billingAccountNumber and common_customer_id=:commonCustomerId and transaction_date IN (:transactionDate) and order_number=:orderNumber

  ProcessEcommerceAuthorizationsV3 : 1) select * from payment_duplicate_check where transaction_type=:transactionType and application_id=:applicationId and amount=:amount and billing_account_number=:billingAccountNumber and common_customer_id=:commonCustomerId and transaction_date IN (:transactionDate)
  2) select * from payment_duplicate_check where transaction_type=:transactionType and application_id=:applicationId and amount=:amount and billing_account_number=:billingAccountNumber and common_customer_id=:commonCustomerId and transaction_date IN (:transactionDate) and order_number=:orderNumber

  DigitalTransactionManagerV3 : INSERT INTO payment_duplicate_check (transaction_type,application_id,amount,billing_account_number,common_customer_id,transaction_date,order_number,payment_instrument_last4,transaction_timestamp,created_by,created_date,dps_shortcode,modified_by,modified_date,status) VALUES ('validation','MWEB',0.0,'112303274','DEFAULT','2019-02-28','UNDEFINED','4448',1551408366375,'PAYMENTSAPI',1551408366460,'A','PAYMENTSAPI',1551408366460,'COMPLETE');

  ECPPaymentsV3 : 1) select * from payment_duplicate_check where transaction_type=:transactionType and application_id=:applicationId and amount=:amount and billing_account_number=:billingAccountNumber and common_customer_id=:commonCustomerId and transaction_date IN (:transactionDate)
  2) select * from payment_duplicate_check where transaction_type=:transactionType and application_id=:applicationId and amount=:amount and billing_account_number=:billingAccountNumber and common_customer_id=:commonCustomerId and transaction_date IN (:transactionDate) and order_number=:orderNumber"
*/

  val readPayment_duplicate_checkQuery = QueryBuilder.select("transaction_type", "application_id", "amount", "billing_account_number", "common_customer_id", "transaction_date")
    .from(keyspace, tableName)
    .limit(10)

  def readRecords:  ChainBuilder = {

    val preparedStatement = session.prepare(readPayment_duplicate_checkQuery)

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
           |transaction_type text,
           |application_id text,
           |amount double,
           |billing_account_number text,
           |common_customer_id text,
           |transaction_date text,
           |order_number text,
           |payment_instrument_last4 text,
           |transaction_timestamp timestamp,
           |created_by text,
           |created_date timestamp,
           |dps_shortcode text,
           |modified_by text,
           |modified_date timestamp,
           |status text,
           |PRIMARY KEY ((transaction_type, application_id), amount, billing_account_number, common_customer_id, transaction_date, order_number, payment_instrument_last4, transaction_timestamp)
           |) WITH CLUSTERING ORDER BY (amount DESC, billing_account_number DESC, common_customer_id DESC, transaction_date DESC, order_number DESC, payment_instrument_last4 DESC, transaction_timestamp DESC);"""
          .stripMargin
      )
    )
  }
}
