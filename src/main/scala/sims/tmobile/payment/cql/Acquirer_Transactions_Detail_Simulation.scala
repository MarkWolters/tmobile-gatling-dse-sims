package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.Acquirer_Transactions_Detail_Action
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.{FetchBaseData, SimConfig}
import io.gatling.core.Predef._
import com.datastax.gatling.plugin.CqlPredef._
import feeds.tmobile.payment.cql.Acquirer_Transactions_Detail_Feed

class Acquirer_Transactions_Detail_Simulation extends BaseSimulation {

  val simName = "acquirer_transaction_configuration"
  val scenarioWriteName = "acq_Trans_Payment_Write"
  val scenarioReadName = "acq_Trans_Payment_Read"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Acquirer_Transactions_Detail_Action(cass, simConfWrite)

  val LoadWriteFeed = new Acquirer_Transactions_Detail_Feed().getAcquirer_Transaction_Details

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  // read scenario

  val simConfRead = new SimConfig(conf, simName, scenarioReadName)

  val LoadReadActions = new Acquirer_Transactions_Detail_Action(cass, simConfRead)

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
