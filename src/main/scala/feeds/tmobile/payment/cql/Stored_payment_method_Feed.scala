package feeds.tmobile.payment.cql

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

class Stored_payment_method_Feed
  extends BaseFeed with LazyLogging{


  def getStored_payment_method = {
    def rowData = this.getRowData


    Iterator.continually(rowData)
  }

  def getRowData = {

    val bigint_size = 12

    Map(
        "wallet_id" -> getRandomNumber(bigint_size).toString,
        "tmotoken" -> getRandomNumber(bigint_size).toString,
        "account_holder_first_name" -> getRandomNumber(bigint_size).toString,
        "account_holder_full_name" -> getRandomNumber(bigint_size).toString,
        "account_holder_last_name" -> getRandomNumber(bigint_size).toString,
        "address_line_1" -> getRandomNumber(bigint_size).toString,
        "address_line_2" -> getRandomNumber(bigint_size).toString,
        "applicationname" -> getRandomNumber(bigint_size).toString,
        //"autopay_indicator" -> faker.bool(),
        "biller_code" -> getRandomNumber(bigint_size).toString,
        "business_segment" -> getRandomNumber(bigint_size).toString,
        "business_unit" -> getRandomNumber(bigint_size).toString,
        "channel" -> getRandomNumber(bigint_size).toString,
        "city_name" -> getRandomNumber(bigint_size).toString,
        "created_by" -> getRandomNumber(bigint_size).toString,
        "created_date" -> getCurrentTimestamp,
        //"customer_id_blocked" -> faker.bool(),
        "dps_payment_id" -> getRandomNumber(bigint_size).toInt,
        "expiration_month_year" -> getRandomNumber(bigint_size).toString,
        "last_use_date" -> getCurrentTimestamp,
        "legacytoken" -> getRandomNumber(bigint_size).toString,
        "modified_by" -> getRandomNumber(bigint_size).toString,
        "modified_date" -> getCurrentTimestamp,
        "nick_name" -> getRandomNumber(bigint_size).toString,
        "payment_device_id" -> getRandomNumber(bigint_size).toString,
        "payment_method_code" -> getRandomNumber(bigint_size).toString,
        "payment_method_status" -> getRandomNumber(bigint_size).toString,
        //"preferred_payment_device" -> faker.bool(),
        "program_code" -> getRandomNumber(bigint_size).toString,
        "routing_number" -> getRandomNumber(bigint_size).toString,
        "sec" -> getRandomNumber(bigint_size).toString,
        "state_code" -> getRandomNumber(bigint_size).toString,
        "status" -> getRandomNumber(bigint_size).toString,
        //"terms_agreement_indicator" -> faker.bool(),
        "terms_agreement_time" -> getCurrentTimestamp,
        "type_code" -> getRandomNumber(bigint_size).toString,
        "zip" -> getRandomNumber(bigint_size).toString
      )
    }

  def getRandomNumber(digits:Int) = {
    System.nanoTime()
  }
}
