package feeds.tmobile.payment.solr

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging
import com.datastax.gatling.stress.helpers.SolrQueryBuilder

import scala.util.Random
import scala.io.Source

class SearchFeed extends BaseFeed with LazyLogging {

  var dpsIds = scala.collection.mutable.ArrayBuffer.empty[String]
  var batchIds = scala.collection.mutable.ArrayBuffer.empty[String]
  var commonCustomerIds = scala.collection.mutable.ArrayBuffer.empty[String]

  val random = new Random

  for (line <- Source.fromFile("ids.csv").getLines) {
    val tokens = line.split(",")
    if (tokens.length > 0) dpsIds += {tokens(0)}
    if (tokens.length > 1) batchIds += {tokens(1)}
    if (tokens.length > 2) commonCustomerIds += {tokens(2)}
  }

  def dptSolrQuery= Iterator.continually(getDptSolrData)
  def txDetailsSolrQuery = Iterator.continually(getTxDetailsSolrQuery)
  def autopayDetailsSolrQuery = Iterator.continually(getAutopayDetailsSolrQuery)


  private def getDptSolrData = {
    val dps_payment_id = dpsIds(random.nextInt(dpsIds.length))
    val search_query = new SolrQueryBuilder().withQuery(s"dps_payment_id:$dps_payment_id").withPaging("driver").build
    Map("solr_query" -> search_query)
  }

  private def getTxDetailsSolrQuery = {
    val batch_id = batchIds(random.nextInt(batchIds.length))
    val search_query = new SolrQueryBuilder().withQuery(s"batch_id:$batch_id").withPaging("driver").build
    Map("solr_query" -> search_query)
  }

  private def getAutopayDetailsSolrQuery = {
    val batch_id = batchIds(random.nextInt(batchIds.length))
    val common_customer_id = commonCustomerIds(random.nextInt(commonCustomerIds.length))
    val search_query = new SolrQueryBuilder().withQuery(s"batch_id:$batch_id").withFilterQuery(s"common_customer_id:$common_customer_id") .withPaging("driver").build
    Map("solr_query" -> search_query)
  }


}
