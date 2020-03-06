package actions.tmobile.payment.solr

import java.io.InputStream

import com.datastax.driver.core.{ConsistencyLevel, DataType}
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.driver.core.schemabuilder.SchemaBuilder
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import com.mashape.unirest.http.Unirest
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import org.json4s.DefaultFormats




class Payment_Search_Action (cassandra: Cassandra, simConf: SimConfig) extends BaseAction(cassandra, simConf) {

  implicit val formats = DefaultFormats

  //createKeyspace
  //createTables
  //createSolrSchema

  private val writeAccountQuery: Insert = QueryBuilder.insertInto(keyspace, table)
    .value("account_id", raw(":account_id"))
    .value("first_name", raw(":first_name"))
    .value("last_name", raw(":last_name"))
    .value("email", raw(":email"))
    .value("age", raw(":age"))
    .value("country_code", raw(":country_code"))
    .value("locale", raw(":locale"))
    .value("pass", raw(":pass"))
    .value("birthday", raw(":birthday"))
    .value("created_date", raw(":created_date"))
    .value("updated_date", raw(":updated_date"))


  private val writeMemberPreparedStatement = session.prepare(writeAccountQuery)

  def writeAccount: ChainBuilder = {

    exec(
      cql("InsertAccount")
        .executeNamed(writeMemberPreparedStatement)
    )

  }

  private val solrQuery = QueryBuilder.select().from(keyspace, table)
    .where(QueryBuilder.eq("solr_query", raw(":solr_query")))


  private val solrQueryPreparedStatement = session.prepare(solrQuery)

  def queryWithSolr = {
    exec(
      cql("SolrQuery")
        .executeNamed(solrQueryPreparedStatement)
        .consistencyLevel(ConsistencyLevel.LOCAL_ONE)
    )
  }


  private def createTables = {

    runQueries(Array(

      SchemaBuilder.createTable(keyspace, table)
        .addPartitionKey("account_id", DataType.uuid())
        .addColumn("first_name", DataType.text())
        .addColumn("last_name", DataType.text())
        .addColumn("email", DataType.text())
        .addColumn("country_code", DataType.text())
        .addColumn("locale", DataType.text())
        .addColumn("age", DataType.cint())
        .addColumn("birthday", DataType.date())
        .addColumn("pass", DataType.text())
        .addColumn("created_date", DataType.timestamp())
        .addColumn("updated_date", DataType.timestamp())
        .ifNotExists()
        .toString,

      SchemaBuilder.createTable(keyspace, "payment_transactions_detail_dpt")
        .addPartitionKey("dps_payment_id", DataType.bigint())
        .addClusteringColumn("card_first_6", DataType.text())
        .addClusteringColumn("card_last_4", DataType.text())
        .addClusteringColumn("process_id", DataType.text())
        .addColumn("accuity_abarouting_number", DataType.text())
        .addColumn("acquirer_approval_code", DataType.text())
        .addColumn("acquirer_avs_responsecode", DataType.text())
        .addColumn("acquirer_cvv_responsecode", DataType.text())
        .addColumn("acquirer_name", DataType.text())
        .addColumn("acquirer_parent_payment_id", DataType.text())
        .addColumn("acquirer_payment_id", DataType.text())
        .addColumn("address_matched", DataType.text())
        .addColumn("address_type", DataType.text())
        .addColumn("amount", DataType.cdouble())
        .addColumn("application_id", DataType.text())
        .addColumn("application_name", DataType.text())
        .addColumn("application_version", DataType.text())
        .addColumn("applicationcryptogram", DataType.text())
        .addColumn("auth_hold_revrsal_action", DataType.text())
        .addColumn("authorized_amount", DataType.cdouble())
        .addColumn("bank_name", DataType.text())
        .addColumn("batch_id", DataType.text())
        .addColumn("bill_due_date", DataType.date())
        .addColumn("biller_code", DataType.text())
        .addColumn("billing_account_number", DataType.text())
        .addColumn("business_segment", DataType.text())
        .addColumn("business_unit", DataType.text())
        .addColumn("card_brand", DataType.text())
        .addColumn("card_present", DataType.cboolean())
        .addColumn("cardholder_address_line1", DataType.text())
        .addColumn("cardholder_address_line2", DataType.text())
        .addColumn("cardholder_address_line3", DataType.text())
        .addColumn("cardholder_address_line4", DataType.text())
        .addColumn("cardholder_address_line5", DataType.text())
        .addColumn("cardholder_city", DataType.text())
        .addColumn("cardholder_state", DataType.text())
        .addColumn("cardholder_zip", DataType.text())
        .addColumn("charge_source", DataType.text())
        .addColumn("chargeback_export_destination", DataType.text())
        .addColumn("chargeback_export_status", DataType.text())
        .addColumn("client_id", DataType.text())
        .addColumn("common_customer_id", DataType.text())
        .addColumn("contactless_category", DataType.text())
        .addColumn("created_by", DataType.text())
        .addColumn("created_date", DataType.timestamp())
        .addColumn("customer_mobile_phone", DataType.text())
        .addColumn("customer_name", DataType.text())
        .addColumn("customer_present", DataType.cboolean())
        .addColumn("debit_signature_code", DataType.cboolean())
        .addColumn("deviceid", DataType.text())
        .addColumn("dps_response_code", DataType.text())
        .addColumn("dps_response_message", DataType.text())
        .addColumn("dps_shortcode", DataType.text())
        .addColumn("electronic_authentication_capability", DataType.text())
        .addColumn("entrymode", DataType.text())
        .addColumn("expiration_date", DataType.text())
        .addColumn("fraud_check_transaction_id", DataType.text())
        .addColumn("fraud_decision_status", DataType.text())
        .addColumn("guid", DataType.text())
        .addColumn("import_batch_id", DataType.text())
        .addColumn("integratedchipdata", DataType.text())
        .addColumn("keyed_or_manual_indicator", DataType.text())
        .addColumn("legacytoken", DataType.text())
        .addColumn("local_transaction_time", DataType.text())
        .addColumn("mac_address", DataType.timestamp())
        .addColumn("manager_login_id", DataType.text())
        .addColumn("member_id", DataType.text())
        .addColumn("member_id_type", DataType.text())
        .addColumn("merchant_routing_id", DataType.text())
        .addColumn("modified_by", DataType.text())
        .addColumn("modified_date", DataType.timestamp())
        .addColumn("onlineoffline_indicator", DataType.text())
        .addColumn("operation_type", DataType.text())
        .addColumn("operator_id", DataType.text())
        .addColumn("order_number", DataType.text())
        .addColumn("order_type", DataType.text())
        .addColumn("parent_order_id", DataType.text())
        .addColumn("parent_payment_id", DataType.bigint())
        .addColumn("partner_id", DataType.text())
        .addColumn("payment_instrument_account_number", DataType.text())
        .addColumn("payment_instrument_category", DataType.text())
        .addColumn("payment_instrument_holder_firstname", DataType.text())
        .addColumn("payment_instrument_holder_fullname", DataType.text())
        .addColumn("payment_instrument_holder_lastname", DataType.text())
        .addColumn("payment_instrument_holder_phonenumber", DataType.text())
        .addColumn("payment_method_code", DataType.text())
        .addColumn("payment_type_code", DataType.text())
        .addColumn("pin_indicator", DataType.cboolean())
        .addColumn("pinless_indicator", DataType.cboolean())
        .addColumn("posting_destination", DataType.text())
        .addColumn("posting_status", DataType.text())
        .addColumn("prepaid_available_balance", DataType.text())
        .addColumn("prepaid_card_indicator", DataType.cboolean())
        .addColumn("product_group", DataType.text())
        .addColumn("program_code", DataType.text())
        .addColumn("refunded_amount", DataType.cdouble())
        .addColumn("remaining_balance", DataType.cdouble())
        .addColumn("reversal_action", DataType.text())
        .addColumn("routing_number", DataType.text())
        .addColumn("sale_terminal_num", DataType.text())
        .addColumn("settlement_batch_id", DataType.text())
        .addColumn("settlement_required", DataType.cboolean())
        .addColumn("settlement_status", DataType.text())
        .addColumn("ship_to_address_line1", DataType.text())
        .addColumn("ship_to_address_line2", DataType.text())
        .addColumn("ship_to_address_line3", DataType.text())
        .addColumn("ship_to_address_line4", DataType.text())
        .addColumn("ship_to_address_line5", DataType.text())
        .addColumn("ship_to_city", DataType.text())
        .addColumn("ship_to_state", DataType.text())
        .addColumn("ship_to_zip", DataType.text())
        .addColumn("status", DataType.text())
        .addColumn("status_modified_date", DataType.timestamp())
        .addColumn("store_addressline_1", DataType.text())
        .addColumn("store_addressline_2", DataType.text())
        .addColumn("store_cityname", DataType.text())
        .addColumn("store_countrycode", DataType.text())
        .addColumn("store_id", DataType.text())
        .addColumn("store_statecode", DataType.text())
        .addColumn("store_zip", DataType.text())
        .addColumn("stored_payment_indicator", DataType.cboolean())
        .addColumn("surcharge_amount", DataType.cdouble())
        .addColumn("tax_amount", DataType.cdouble())
        .addColumn("terminal_make", DataType.text())
        .addColumn("terminal_model", DataType.text())
        .addColumn("terminalcapability", DataType.text())
        .addColumn("terminalid", DataType.text())
        .addColumn("terminaltype", DataType.text())
        .addColumn("termscond_accepted", DataType.cboolean())
        .addColumn("termscond_accepted_timestamp", DataType.timestamp())
        .addColumn("termscond_application_id", DataType.text())
        .addColumn("timeout_reversal", DataType.text())
        .addColumn("tmoaccountholder_address_line1", DataType.text())
        .addColumn("tmoaccountholder_address_line2", DataType.text())
        .addColumn("tmoaccountholder_address_line3", DataType.text())
        .addColumn("tmoaccountholder_address_line4", DataType.text())
        .addColumn("tmoaccountholder_address_line5", DataType.text())
        .addColumn("tmoaccountholder_city", DataType.text())
        .addColumn("tmoaccountholder_state", DataType.text())
        .addColumn("tmoaccountholder_zip", DataType.text())
        .addColumn("tmotoken", DataType.text())
        .addColumn("trackonedata", DataType.text())
        .addColumn("tracktwodata", DataType.text())
        .addColumn("transaction_date", DataType.timestamp())
        .addColumn("transaction_type", DataType.text())
        .addColumn("verification_method", DataType.text())
        .addColumn("void_reason", DataType.text())
        .addColumn("wallet_id", DataType.text())
        .addColumn("zip_matched", DataType.text())
        .ifNotExists()
        .toString
    ))

  }


  private def createSolrSchema = {

    val solrConfig = simConf.getSimulationConf.getConfig("solr")
    val httpsBool = solrConfig.getBoolean("https")

    val urlBase = StringBuilder.newBuilder
    if (httpsBool) {
      urlBase.append("https")
    } else {
      urlBase.append("http")
    }

    urlBase.append("://")
    urlBase.append(simConf.getCassandraConf.getList("hosts").unwrapped().get(0))
    urlBase.append(":")
    urlBase.append(solrConfig.getInt("httpPort"))

    // URL: /solr/admin/cores?action=CREATE&name=$keyspace.$table
    val createCoreBase = urlBase.clone()

    createCoreBase.append("/solr/admin/cores")

    // URL: http://localhost:8983/solr/resource/keyspace.table/solrconfig.xml
    val createResourceBase = urlBase.clone()
    createResourceBase.append(s"/solr/resource/$keyspace.$table/")

    val stream_1 = getClass.getResourceAsStream(solrConfig.getString("configXml"))
    val solrConfigXml = scala.io.Source.fromInputStream(stream_1).mkString

    val stream_2: InputStream = getClass.getResourceAsStream(solrConfig.getString("schemaXml"))
    val solrSchemaXml = scala.io.Source.fromInputStream(stream_2).mkString

    val submitSolrConfigResource = Unirest.post(createResourceBase.toString() + "solrconfig.xml")
      .header("Content-type", "text/xml; charset=utf-8")
      .body(solrConfigXml)
      .asBinary()

    if (submitSolrConfigResource.getStatus != 200) {
      logger.error(s"Unable to submit solrconfig.xml file for core $keyspace.$table. " +
        s"Server Error: ${submitSolrConfigResource.getBody}")
      System.exit(1)
    }

    val submitSolrSchemaResource = Unirest.post(createResourceBase.toString() + "schema.xml")
      .header("Content-type", "text/xml; charset=utf-8")
      .body(solrSchemaXml)
      .asBinary()

    if (submitSolrSchemaResource.getStatus != 200) {
      logger.error(s"Unable to submit schema.xml file for core $keyspace.$table. " +
        s"Server Error: ${submitSolrSchemaResource.getBody}")
      System.exit(1)
    }

    val createCoreResponse = Unirest.post(createCoreBase.toString())
      .queryString("action", "CREATE")
      .queryString("name", s"$keyspace.$table")
      .queryString("reindex", "false")
      .asString()

    if (createCoreResponse.getStatus != 200) {

      if (createCoreResponse.getBody.contains("already exists and is loaded")) {
        logger.warn(s"Core for $keyspace.$table is already created, skipping.")
      } else {
        logger.error(s"Unable to submit CREATE core for $keyspace.$table. " +
          s"erver Error: ${createCoreResponse.getBody}")
        System.exit(1)
      }

    }
  }
}
