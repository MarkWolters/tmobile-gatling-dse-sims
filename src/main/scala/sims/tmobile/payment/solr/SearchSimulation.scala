package sims.tmobile.payment.solr

import actions.tmobile.payment.solr.SearchActions
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.{FetchBaseData, SimConfig}
import feeds.tmobile.payment.solr.SearchFeed
import io.gatling.core.Predef._

class SearchSimulation extends BaseSimulation {
  val simName = "solrPaymentsBatch"
  val scenarioName = "queryPaymentsBatch"

  val simConf = new SimConfig(conf, simName, scenarioName)

  val actions = new SearchActions(cass, simConf)

  val dataFeed = new SearchFeed

  val searchScenario = scenario("queryPaymentsBatch").randomSwitch(
    (50,exec(feed(dataFeed.dptSolrQuery).exec(actions.dptQueryWithSolr("dptSolrQuery")))),
    (25,exec(feed(dataFeed.txDetailsSolrQuery).exec(actions.txDetailsQueryWithSolr("txDetailsSolrQuery")))),
    (50,exec(feed(dataFeed.autopayDetailsSolrQuery).exec(actions.autopayDetailsQueryWithSolr("autopayDetailsSolrQuery"))))
  )

  setUp(
    loadGenerator.rampUpToConstant(searchScenario, simConf)
  ).protocols(cqlProtocol)
}
