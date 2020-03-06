package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.Batch_payment_transaction_details_Action
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.{FetchBaseData, SimConfig}
import io.gatling.core.Predef._
import com.datastax.gatling.plugin.CqlPredef._
import feeds.tmobile.payment.cql.Batch_payment_transaction_details_Feed

class Batch_payment_transaction_details_Simulation extends BaseSimulation {

  val simName = "batch_payment_transaction_details_configuration"
  val scenarioWriteName = "btch_Trans_Details_Payment_Write"
  val scenarioReadName = "btch_Trans_Details_Payment_Read"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Batch_payment_transaction_details_Action(cass, simConfWrite)

  val LoadWriteFeed = new Batch_payment_transaction_details_Feed().getBatch_payment_transaction_details

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  // read scenario

  val simConfRead = new SimConfig(conf, simName, scenarioReadName)

  val LoadReadActions = new Batch_payment_transaction_details_Action(cass, simConfRead)

  new FetchBaseData(simConfRead, cass).createBaseDataCsv()

  val csvFeeder = csv(getDataPath((simConfRead))).random

  val readScenario = scenario(scenarioReadName)
    .feed(csvFeeder)
    .exec(LoadReadActions.readRecords)

  setUp(
    // loadGenerator.runOnlyOnce(writeScenario)
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite),
    loadGenerator.rampUpToConstant(readScenario, simConfRead)
  ).protocols(cqlConfig)

}
