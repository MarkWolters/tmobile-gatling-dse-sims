package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.Payment_Transactions_Detail_Action
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.{FetchBaseData, SimConfig}
import io.gatling.core.Predef._
import com.datastax.gatling.plugin.CqlPredef._
import feeds.tmobile.payment.cql.Payment_Transactions_Detail_Feed

class Payment_Transactions_Detail_Write_Simulation extends BaseSimulation {

  val simName = "Payment_Transactions_Detail_Simulation_configuration"
  val scenarioWriteName = "Payment_Transactions_Detail_Simulation_Payment_Write"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Payment_Transactions_Detail_Action(cass, simConfWrite)

  val LoadWriteFeed = new Payment_Transactions_Detail_Feed().getPayment_Transaction_Details

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  setUp(
    // loadGenerator.runOnlyOnce(writeScenario)
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite)
  ).protocols(cqlConfig)

}
