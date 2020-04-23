package actions.tmobile.payment.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class Demo_Actions(cassandra: Cassandra, simConfig: SimConfig) extends BaseAction(cassandra, simConfig) {

  createKeyspace

  private val writeWalletTmoidStgQuery: Insert = QueryBuilder.insertInto(keyspace, "wallet_tmoid_stg")
    .value("account_number", raw("?"))
    .value("tmoid", raw("?"))
    .value("wallet_id", raw("?"))

  def writeTmoidRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writeWalletTmoidStgQuery)

    group(Groups.INSERT) {
      exec(cql("writeRecords")
        .executeNamed(preparedStatement)
        .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM)
      )
    }
  }

  private val writeStoredPaymentQuery: Insert = QueryBuilder.insertInto(keyspace, "stored_payment_method")
    .value("wallet_id", raw("?"))
    .value("tmotoken", raw("?"))
    .value("payment_device_id", raw("?"))

  def writeStoredPaymentRecords: ChainBuilder = {

    val preparedStatement = session.prepare(writeStoredPaymentQuery)

    group(Groups.INSERT) {
      exec(cql("writeRecords")
        .executeNamed(preparedStatement)
        .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM)
      )
    }
  }

}

