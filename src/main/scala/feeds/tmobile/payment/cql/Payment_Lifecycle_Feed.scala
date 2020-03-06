package feeds.tmobile.payment.cql

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

class Payment_Lifecycle_Feed
  extends BaseFeed with LazyLogging{


    def getPayment_Lifecycle = {
      def rowData = this.getRowData


      Iterator.continually(rowData)
    }

    def getRowData = {

      val bigint_size = 12

      Map(
        "parent_payment_id" -> getRandomNumber(bigint_size).toInt,
        "transaction_type" -> getRandomNumber(bigint_size).toString,
        "dps_payment_id" -> getRandomNumber(bigint_size).toInt,
        "reversal_retry_count" -> getRandomNumber(bigint_size).toInt,
        "status" -> getRandomNumber(bigint_size).toString,
      )
    }

  def getRandomNumber(digits:Int) = {
    System.nanoTime()
  }
}
