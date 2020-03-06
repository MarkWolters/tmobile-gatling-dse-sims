package sims.tmobile.payment.solr

import actions.tmobile.payment.solr.Payment_Search_Action
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.tmobile.payment.solr.Payment_Search_Feed
import io.gatling.core.Predef._

class Payment_Search_Simulation  extends BaseSimulation {

  val simName = "solrPayments"
  val scenarioName = "queryPayments"

  val simConf = new SimConfig(conf, simName, scenarioName)

  val actions = new Payment_Search_Action(cass, simConf)

  val accountFeed = new Payment_Search_Feed

  val writeScenario = scenario("SolrQuery")
    .feed(accountFeed.solrLocaleEnUs)
    .exec(actions.queryWithSolr)

  setUp(
    loadGenerator.rampUpToConstant(writeScenario, simConf)
  ).protocols(cqlProtocol)
}
