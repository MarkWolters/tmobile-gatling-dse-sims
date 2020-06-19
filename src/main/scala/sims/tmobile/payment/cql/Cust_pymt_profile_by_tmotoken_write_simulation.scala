package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.{Cust_pymt_profile_by_tmotoken_action}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.tmobile.payment.cql.Cust_pymt_profile_by_tmotoken_feed
import io.gatling.core.Predef._

class Cust_pymt_profile_by_tmotoken_write_simulation extends BaseSimulation{
  val simName = "Cust_pymt_profile_by_tmotoken_write_simulation_configuration"
  val scenarioWriteName = "Cust_pymt_profile_by_tmotoken_write_simulation"

  // write scenario

  val simConfWrite = new SimConfig(conf, simName, scenarioWriteName)

  val LoadWriteActions = new Cust_pymt_profile_by_tmotoken_action(cass, simConfWrite)

  val LoadWriteFeed = new Cust_pymt_profile_by_tmotoken_feed().getCustPymtProfileByTmotokenFeed

  val writeScenario = scenario(scenarioWriteName)
    .feed(LoadWriteFeed)
    .exec(LoadWriteActions.writeRecords)

  setUp(
    loadGenerator.rampUpToConstant(writeScenario, simConfWrite)
  ).protocols(cqlConfig)
}
