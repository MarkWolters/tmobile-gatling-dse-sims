package sims.tmobile.payment.solr

import actions.tmobile.payment.solr.{SearchActions, SearchWriteActions}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.tmobile.payment.solr.{SearchFeed, SearchWriteFeed}
import io.gatling.core.Predef._

class SearchReadWriteSimulation extends BaseSimulation {
  val simName = "solrPaymentsBatch"
  val scenarioName = "readWriteDpt"

  val simConf = new SimConfig(conf, simName, scenarioName)

  val readActions = new SearchActions(cass, simConf)
  val writeActions = new SearchWriteActions(cass, simConf)

  val readFeed = new SearchFeed
  val writeFeed = new SearchWriteFeed

  val searchScenario = scenario("queryPaymentsBatch").randomSwitch(
    (70,exec(feed(readFeed.dptSolrQuery).exec(readActions.dptQueryWithSolr("dptSolrQuery")))),
    (30,exec(feed(writeFeed.getDptRowData).exec(writeActions.writeDpt("writeDpt"))))
  )

  setUp(
    loadGenerator.rampUpToConstant(searchScenario, simConf)
  ).protocols(cqlProtocol)
}
