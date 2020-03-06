package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.Payment_transactions_detail_dpt_Action
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.{FetchBaseData, SimConfig}
import io.gatling.core.Predef._
import com.datastax.gatling.plugin.CqlPredef._
import feeds.tmobile.payment.cql.Payment_transactions_detail_dpt_Feed

class Payment_transactions_detail_dpt_Write_Simulation extends BaseSimulation {

  val simName = "Payment_transactions_detail_dpt_Simulation_configuration"
  val scenarioWriteName = "Payment_transactions_detail_dpt_Simulation_Payment_Write"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Payment_transactions_detail_dpt_Action(cass, simConfWrite)

  val LoadWriteFeed = new Payment_transactions_detail_dpt_Feed().getPayment_transactions_detail_dpt

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  setUp(
    // loadGenerator.runOnlyOnce(writeScenario)
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite)
  ).protocols(cqlConfig)

}
