package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.Payment_Lifecycle_Action
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.{FetchBaseData, SimConfig}
import io.gatling.core.Predef._
import com.datastax.gatling.plugin.CqlPredef._
import feeds.tmobile.payment.cql.Payment_Lifecycle_Feed

class Payment_Lifecycle_Write_Simulation extends BaseSimulation {

  val simName = "Payment_Lifecycle_Simulation_configuration"
  val scenarioWriteName = "Payment_Lifecycle_Simulation_Payment_Write"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Payment_Lifecycle_Action(cass, simConfWrite)

  val LoadWriteFeed = new Payment_Lifecycle_Feed().getPayment_Lifecycle

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  setUp(
    // loadGenerator.runOnlyOnce(writeScenario)
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite)
  ).protocols(cqlConfig)

}
