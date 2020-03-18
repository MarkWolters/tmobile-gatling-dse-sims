package sims.tmobile.payment.solr

import actions.tmobile.payment.solr.{Payment_Search_Action, SearchWriteActions}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.tmobile.payment.solr.{Payment_Search_Feed, SearchWriteFeed}
import io.gatling.core.Predef._

class SearchWriteSimulation  extends BaseSimulation {

  val simName = "solrPaymentsBatch"
  val scenarioName = "writeDpt"

  val simConf = new SimConfig(conf, simName, scenarioName)

  val actions = new SearchWriteActions(cass, simConf)

  val feed = new SearchWriteFeed

  val writeScenario = scenario("SolrQuery")
    .feed(feed.getDptRowData)
    .exec(actions.writeDpt("writeDpt"))

  setUp(
    loadGenerator.rampUpToConstant(writeScenario, simConf)
  ).protocols(cqlProtocol)
}
