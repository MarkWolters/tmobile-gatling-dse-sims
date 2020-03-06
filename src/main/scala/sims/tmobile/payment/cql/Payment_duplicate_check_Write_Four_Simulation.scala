package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.Payment_duplicate_check_Action_4
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.tmobile.payment.cql.Payment_duplicate_check_Feed_0
import io.gatling.core.Predef._

class Payment_duplicate_check_Write_Four_Simulation extends BaseSimulation {

  val simName = "Payment_duplicate_check_configuration_0"
  val scenarioWriteName = "Payment_duplicate_check_Payment_Write"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Payment_duplicate_check_Action_4(cass, simConfWrite)

  val LoadWriteFeed = new Payment_duplicate_check_Feed_0().getPayment_duplicate_check

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  setUp(
    // loadGenerator.runOnlyOnce(writeScenario)
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite)
  ).protocols(cqlConfig)
}
