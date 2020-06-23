package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.{Wallet_tmoid_action}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.tmobile.payment.cql.{Wallet_tmoid_feed}
import io.gatling.core.Predef._

class Wallet_tmoid_write_simulation extends BaseSimulation{
  val simName = "Wallet_tmoid_write_simulation_configuration"
  val scenarioWriteName = "Wallet_tmoid_write_simulation"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Wallet_tmoid_action(cass, simConfWrite)

  val LoadWriteFeed = new Wallet_tmoid_feed().getWalletTmoidStgFeed

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  setUp(
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite)
  ).protocols(cqlConfig)
}
