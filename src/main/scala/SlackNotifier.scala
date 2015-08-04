import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNSRecord

import scala.collection.JavaConverters._

object SlackNotifier {
  val slackWebhookToken = "dummyToken"

  /**
   * This is the main method that will be called on event firing
   * @param event
   * @return
   */
  def handleSNSEvent(event: SNSEvent) = {

    // A bit annoying as the aws library is Java so some converting is necessary.
    // This simply grabs the first (and only in this case) record from the message
    // into an Option
    val record: Option[SNSRecord] = event.getRecords.asScala.headOption

    record.map(_.getSNS.getMessage)

    /*
    Basic flow:
      * grab message: SNSRecord -> SNS -> Message
      * parse message for a) environment, b) application name
      * Format some message with environment and application name to send to slack
      * POST it
     */
  }

  /**
   * Parse the SNS message for the environment
   * @param message
   * @return
   */
  def environment(message: String): String = {
    // See tests for expected outcome
    ???
  }

  /**
   * Parse the SNS message for the Application
   * @param message
   * @return
   */
  def application(message: String): String = {
    // See tests for expected outcome
    ???
  }

  /**
   * Send message to slack via webhook
   * @param channel
   * @param message
   * @param token
   * @return
   */
  def sendToSlack(channel: String, message: String, token: String = slackWebhookToken) = {
    //See https://api.slack.com/incoming-webhooks for details on the expected payload

    /* Json body should look something like:
      {
        "channel": "#channelName",
        "username": "AWS SNS EB Bot",
        "text": "the message \n formatted as you like it"
      }

      It should POST to "https://hooks.slack.com/services/" + token

      You can either manually create the json using string concat or one of the many json libraries out there.
      You also need a someway of sending the POST to slack. There are plenty of web client libraries out there.
      A simple one is https://github.com/scalaj/scalaj-http
     */

    ???
  }
}

