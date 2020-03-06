package actions.tmobile.payment.solr

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.gatling.plugin.CqlPredef.{cql, rowCount}
import com.datastax.gatling.plugin.DsePredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._

class SearchActions(cassandra: Cassandra, simConf: SimConfig) extends BaseAction(cassandra, simConf) {

  private val dptSolrQuery = QueryBuilder.select().from(keyspace, "payment_transactions_detail_dpt").where(QueryBuilder.eq("solr_query", raw(":solr_query")))

  private val dptSolrQueryPS = session.prepare(dptSolrQuery)

  private val txDetailsSolrQuery = QueryBuilder.select().from(keyspace, "batch_payment_transaction_details").where(QueryBuilder.eq("solr_query", raw(":solr_query")))

  private val txDetailsSolrQueryPS = session.prepare(txDetailsSolrQuery)

  private val autopayDetailsSolrQuery = QueryBuilder.select().from(keyspace, "autopay_details").where(QueryBuilder.eq("solr_query", raw(":solr_query")))

  private val autopayDetailsSolrQueryPS = session.prepare(autopayDetailsSolrQuery)


  def dptQueryWithSolr(name:String) = {
    exec(
      cql(name)
        .executeNamed(dptSolrQueryPS)
        .consistencyLevel(ConsistencyLevel.LOCAL_ONE)
    )
  }

  def txDetailsQueryWithSolr(name:String) = {
    exec(
      cql(name)
        .executeNamed(txDetailsSolrQueryPS)
        .consistencyLevel(ConsistencyLevel.LOCAL_ONE)
    )
  }

  def autopayDetailsQueryWithSolr(name:String) = {
    exec(
      cql(name)
        .executeNamed(autopayDetailsSolrQueryPS)
        .consistencyLevel(ConsistencyLevel.LOCAL_ONE)
    )
  }

}
