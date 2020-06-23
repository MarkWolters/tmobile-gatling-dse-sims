package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.{Autopay_details_action}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.tmobile.payment.cql.{Autopay_details_feed}
import io.gatling.core.Predef._

class Autopay_details_write_simulation extends BaseSimulation{
  val simName = "Autopay_details_write_simulation_configuration"
  val scenarioWriteName = "Autopay_details_write_simulation"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Autopay_details_action(cass, simConfWrite)

  val LoadWriteFeed = new Autopay_details_feed().getAutopayDetailsFeed

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  setUp(
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite)
  ).protocols(cqlConfig)
}
