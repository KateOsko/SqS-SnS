import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SnS_SqS {

    @Test
    @EnabledIfEnvironmentVariable(named = "AWS_REGION", matches = ".+")
    @EnabledIfEnvironmentVariable(named = "SQS_QUEUE_URL", matches = ".+")
    void sendMessageToSqsQueue() {
        Region region = Region.of(System.getenv("AWS_REGION"));
        String queueUrl = System.getenv("SQS_QUEUE_URL");

        try (SqsClient sqsClient = SqsClient.builder().region(region).build()) {
            SendMessageResponse response = sqsClient.sendMessage(
                    SendMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .messageBody("Hello SQS from SpringBoot")
                            .build()
            );

            assertNotNull(response.messageId());
            assertFalse(response.messageId().isBlank());
            System.out.println("Sent SQS message id: " + response.messageId());
        }
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "AWS_REGION", matches = ".+")
    @EnabledIfEnvironmentVariable(named = "SNS_TOPIC_ARN", matches = ".+")
    void publishMessageToSnsTopic() {
        Region region = Region.of(System.getenv("AWS_REGION"));
        String topicArn = System.getenv("SNS_TOPIC_ARN");

        try (SnsClient snsClient = SnsClient.builder().region(region).build()) {
            PublishResponse response = snsClient.publish(
                    PublishRequest.builder()
                            .topicArn(topicArn)
                            .subject("Test message from Java")
                            .message("Hello SNS from JUnit")
                            .build()
            );

            assertNotNull(response.messageId());
            assertFalse(response.messageId().isBlank());
            System.out.println("Published SNS message id: " + response.messageId());
        }
    }
}
