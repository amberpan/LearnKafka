package kafka.config.topic.manager.consumer;

import kafka.config.topic.manager.model.KafkaConsumerTopicConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class KafkaConsumerRunner implements Callable<KafkaConsumer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerRunner.class);

    AtomicBoolean open = new AtomicBoolean(true);

    KafkaConsumerTopicConfig config;
    KafkaConsumer kafkaConsumer;

    public KafkaConsumerRunner(KafkaConsumerTopicConfig kafkaConsumerConfig, KafkaConsumer kafkaConsumer){
        this.config = kafkaConsumerConfig;
        this.kafkaConsumer = kafkaConsumer;
    }

    @Override
    public KafkaConsumer call() throws Exception {
        LOGGER.info("Initialized consumer: {}",kafkaConsumer);
        try {
            if (config.getPartitions() != null && !config.getPartitions().isEmpty()) {
                AtomicReference<List<TopicPartition>> topicPartitions = new AtomicReference<>();
                config.getTopics().forEach(t->{
                    topicPartitions.set(config.getPartitions().stream().map(p -> new TopicPartition(t, p)).collect(Collectors.toList()));
                });
                kafkaConsumer.assign(topicPartitions.get());
            }
            else
                kafkaConsumer.subscribe(config.getTopics());
            while (open.get()) {
                ConsumerRecords consumerRecords = kafkaConsumer.poll(Duration.ofMillis(config.getTimeout()));

                if (!consumerRecords.isEmpty()) {
                    LOGGER.debug("Fetched {} records from {} topic and {} partition(s) for processing",
                            consumerRecords.count(), config.getTopics(), consumerRecords.partitions());

                    config.getConsumerRecordsProcessor().processRecords(consumerRecords);

                    kafkaConsumer.commitSync();
                }

            }
        }catch (Exception e){
            throw e;
        }finally {
            kafkaConsumer.close();
        }

        return kafkaConsumer;
    }
}
