package kafka.config.topic.manager.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;

public interface IConsumerRecordsProcessor<T,V> {

    void processRecords(ConsumerRecords<T,V> consumerRecords);
}
