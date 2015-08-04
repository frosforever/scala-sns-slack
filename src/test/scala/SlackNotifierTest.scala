import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.amazonaws.services.lambda.runtime.events.SNSEvent.{SNS, SNSRecord}
import org.scalatest.{Matchers, WordSpec}

class SlackNotifierTest extends WordSpec with Matchers {

  // This is the JSON that gets sent in as an event. AWS already parses this to a SNSEvent
  // but it's just here so you know what it looks like
  val inputEventJson = """{
                     |  "Records": [
                     |    {
                     |      "EventSource": "aws:sns",
                     |      "EventVersion": "1.0",
                     |      "EventSubscriptionArn": "arn:aws:sns:us-east-1...ElasticBeanstalkNotifications-Environment-foo-app...",
                     |      "Sns": {
                     |        "Type": "Notification",
                     |        "MessageId": "111",
                     |        "TopicArn": "arn:aws:sns:us-east-1...ElasticBeanstalkNotifications-Environment-foo-app",
                     |        "Subject": "AWS Elastic Beanstalk Notification - New application version was deployed to running EC2 instances",
                     |        "Message": "Timestamp: Thu May 07 23:38:22 UTC 2015\nMessage: Lambda Function Test: New application version was deployed to running EC2 instances.\n\nEnvironment: foo-app\nApplication: FooApp\n\nEnvironment URL: http://foo.com\nRequestId: 222\nNotificationProcessId: 333",
                     |        "Timestamp": "2015-05-07T23:39:18.628Z",
                     |        "SignatureVersion": "1",
                     |        "Signature": "hello-sig",
                     |        "SigningCertUrl": "https://sns.us-east-1.amazonaws.com/...",
                     |        "UnsubscribeUrl": "https://sns.us-east-1.amazonaws.com/...",
                     |        "MessageAttributes": {}
                     |      }
                     |    }
                     |  ]
                     |}""".stripMargin


  val message = """Timestamp: Thu May 07 23:38:22 UTC 2015\nMessage: Lambda Function Test: New application version was deployed to running EC2 instances.\n\nEnvironment: foo-app\nApplication: FooApp\n\nEnvironment URL: http://foo.com\nRequestId: 222\nNotificationProcessId: 333"""
  val subject = """AWS Elastic Beanstalk Notification - New application version was deployed to running EC2 instances"""

  import scala.collection.JavaConverters._
  "notifier" should {
    val sns = new SNS
    sns.setMessage(message)
    sns.setSubject(subject)
    val sNSRecord = new SNSRecord
    sNSRecord.setSns(sns)

    val event = new SNSEvent()
    event.setRecords(List(sNSRecord).asJava)

    "handleSnsEvent" in {
      SlackNotifier.handleSNSEvent(event)

      /** Add tests etc here */
    }

    "parse environment" in {
      SlackNotifier.environment(message) should be ("foo-app")
    }

    "parse application" in {
      SlackNotifier.application(message) should be ("FooApp")
    }
  }

}
