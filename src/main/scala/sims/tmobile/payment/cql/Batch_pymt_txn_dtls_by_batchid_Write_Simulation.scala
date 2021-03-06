package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.Batch_pymt_txn_dtls_by_batchid_Action
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.{FetchBaseData, SimConfig}
import io.gatling.core.Predef._
import com.datastax.gatling.plugin.CqlPredef._
import feeds.tmobile.payment.cql.Batch_pymt_txn_dtls_by_batchid_Feed

class Batch_pymt_txn_dtls_by_batchid_Write_Simulation extends BaseSimulation {

  val simName = "Batch_pymt_txn_dtls_by_batchid_configuration"
  val scenarioWriteName = "btch_Trans_Details_Btchid_Payment_Write"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Batch_pymt_txn_dtls_by_batchid_Action(cass, simConfWrite)

  val LoadWriteFeed = new Batch_pymt_txn_dtls_by_batchid_Feed().getBatch_pymt_txn_dtls_by_batchid

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords1)

  setUp(
    // loadGenerator.runOnlyOnce(writeScenario)
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite)
  ).protocols((cqlProtocol))

}
