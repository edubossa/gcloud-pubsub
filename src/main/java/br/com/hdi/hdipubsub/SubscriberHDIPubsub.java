package br.com.hdi.hdipubsub;

import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class SubscriberHDIPubsub {

    @Autowired
    private HDIPubsubRepository repository;

    // use the default project id
    private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();

    private static final BlockingQueue<PubsubMessage> messages = new LinkedBlockingDeque<>();

    static class MessageReceiverExample implements MessageReceiver {

        private final HDIPubsubRepository repository;

        public MessageReceiverExample(HDIPubsubRepository repository) {
            this.repository = repository;
        }

        @Override
        public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
            System.out.println("Message Id: " + message.getMessageId());
            System.out.println("Data: " + message.getData().toStringUtf8());
            System.out.println("Inserindo dados");
            HDIPubsub data = new  HDIPubsub(message.getMessageId(), message.getData().toStringUtf8());
            System.out.println(data);
            repository.save(data);

            messages.offer(message);
            consumer.ack();
        }
    }

    @PostConstruct
    public void start() throws Exception {

        System.out.println("OPEN CONNECTION ...");

        String subscriptionId = "hditeste";
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(
                PROJECT_ID, subscriptionId);
        Subscriber subscriber = null;
        try {
            // create a subscriber bound to the asynchronous message receiver
            subscriber =
                    Subscriber.newBuilder(subscriptionName, new MessageReceiverExample(this.repository)).build();
            subscriber.startAsync().awaitRunning();

        } finally {
            System.out.println("CONNECTION IS OPEN...");
        }

    }


}
