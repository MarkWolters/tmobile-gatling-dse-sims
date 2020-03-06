package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.Stored_payment_method_Action
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.{FetchBaseData, SimConfig}
import feeds.tmobile.payment.cql.Stored_payment_method_Feed
import io.gatling.core.Predef._

class Stored_payment_method_read_Simulation extends BaseSimulation  {

  val simName = "Stored_payment_method_Simulation_configuration"
  val scenarioReadName = "Stored_payment_method_Simulation_Payment_Read"

  val simConfRead = new SimConfig(conf, simName, scenarioReadName)

  val LoadReadActions = new Stored_payment_method_Action(cass, simConfRead)

  new FetchBaseData(simConfRead, cass).createBaseDataCsv()

  val csvFeeder = csv(getDataPath((simConfRead))).random

  val readScenario = scenario(scenarioReadName)
    .feed(csvFeeder)
    .exec(LoadReadActions.readRecords)

  setUp(
    loadGenerator.rampUpToConstant(readScenario, simConfRead)
  ).protocols(cqlConfig)

}
