package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.Wallet_Action
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.{FetchBaseData, SimConfig}
import io.gatling.core.Predef._
import com.datastax.gatling.plugin.CqlPredef._
import feeds.tmobile.payment.cql.Wallet_Feed

class Wallet_Write_Simulation extends BaseSimulation{
  val simName = "Wallet_Simulation_configuration"
  val scenarioWriteName = "Wallet_Simulation_Payment_Write"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Wallet_Action(cass, simConfWrite)

  val LoadWriteFeed = new Wallet_Feed().getWallet

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  setUp(
    // loadGenerator.runOnlyOnce(writeScenario)
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite)
  ).protocols(cqlConfig)
}
