package sims.tmobile.payment.cql

import actions.tmobile.payment.cql.{Demo_Actions}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.tmobile.payment.cql.{Demo_Feed}
import io.gatling.core.Predef._

class Demo_Simulation extends BaseSimulation{
  val simName = "Demo_Simulation_configuration"
  val tmoidScenarioWriteName = "Demo_Simulation_Write_tmoid"
  val paymentScenarioWriteName = "Demo_Simulation_Write_payment"

  val simConfWrite = new SimConfig(conf, simName, tmoidScenarioWriteName)

  val DemoActions = new Demo_Actions(cass, simConfWrite)

  val DemoFeed = new Demo_Feed()

  val tmoidWriteScenario = scenario(tmoidScenarioWriteName)
    .feed(DemoFeed.getWalletTmoid)
    .exec(DemoActions.writeTmoidRecords)

  val storedPaymentWriteScenario = scenario(paymentScenarioWriteName)
    .feed(DemoFeed.getStoredPayment)
    .exec(DemoActions.writeStoredPaymentRecords)

  setUp(
    loadGenerator.rampUpToConstant(tmoidWriteScenario, simConfWrite),
    loadGenerator.rampUpToConstant(storedPaymentWriteScenario, simConfWrite)
  ).protocols(cqlConfig)
}
