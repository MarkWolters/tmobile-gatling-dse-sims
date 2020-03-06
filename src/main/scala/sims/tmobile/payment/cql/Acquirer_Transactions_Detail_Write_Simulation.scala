package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.Acquirer_Transactions_Detail_Action
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.{FetchBaseData, SimConfig}
import io.gatling.core.Predef._
import com.datastax.gatling.plugin.CqlPredef._
import feeds.tmobile.payment.cql.Acquirer_Transactions_Detail_Feed

class Acquirer_Transactions_Detail_Write_Simulation extends BaseSimulation {

  val simName = "acquirer_transaction_configuration"
  val scenarioWriteName = "acq_Trans_Payment_Write"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Acquirer_Transactions_Detail_Action(cass, simConfWrite)

  val LoadWriteFeed = new Acquirer_Transactions_Detail_Feed().getAcquirer_Transaction_Details

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  setUp(
    //loadGenerator.runOnlyOnce(writeScenario)
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite)
  ).protocols((cqlProtocol))


}
