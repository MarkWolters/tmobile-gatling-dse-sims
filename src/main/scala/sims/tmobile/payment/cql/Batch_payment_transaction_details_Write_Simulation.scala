package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.Batch_payment_transaction_details_Action
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.{FetchBaseData, SimConfig}
import io.gatling.core.Predef._
import com.datastax.gatling.plugin.CqlPredef._
import feeds.tmobile.payment.cql.Batch_payment_transaction_details_Feed

class Batch_payment_transaction_details_Write_Simulation extends BaseSimulation {

  val simName = "batch_payment_transaction_details_configuration"
  val scenarioWriteName = "btch_Trans_Details_Payment_Write"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Batch_payment_transaction_details_Action(cass, simConfWrite)

  val LoadWriteFeed = new Batch_payment_transaction_details_Feed().getBatch_payment_transaction_details

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  setUp(
    // loadGenerator.runOnlyOnce(writeScenario)
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite)
  ).protocols((cqlProtocol))


}
