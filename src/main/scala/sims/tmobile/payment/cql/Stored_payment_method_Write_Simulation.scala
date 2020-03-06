package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.Stored_payment_method_Action
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.{FetchBaseData, SimConfig}
import io.gatling.core.Predef._
import com.datastax.gatling.plugin.CqlPredef._
import feeds.tmobile.payment.cql.Stored_payment_method_Feed

class Stored_payment_method_Write_Simulation extends BaseSimulation  {

  val simName = "Stored_payment_method_Simulation_configuration"
  val scenarioWriteName = "Stored_payment_method_Simulation_Payment_Write"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Stored_payment_method_Action(cass, simConfWrite)

  val LoadWriteFeed = new Stored_payment_method_Feed().getStored_payment_method

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  setUp(
    // loadGenerator.runOnlyOnce(writeScenario)
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite)
  ).protocols(cqlConfig)

}
