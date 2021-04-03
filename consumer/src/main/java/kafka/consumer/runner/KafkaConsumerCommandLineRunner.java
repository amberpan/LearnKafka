package kafka.consumer.runner;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;

@Component
public class KafkaConsumerCommandLineRunner implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerCommandLineRunner.class);

    @Value("${kafka.consumer.generic.topic}")
    String announceTopic;

    @Autowired
    KafkaConsumer<String,String> announceTopicKafkaConsumer;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Initializing kafka consumer");
        announceTopicKafkaConsumer.subscribe(Arrays.asList(announceTopic));
        announceTopicKafkaConsumer.listTopics();

        try{
            while(true){
                ConsumerRecords<String,String> consumerRecords = announceTopicKafkaConsumer.poll(Duration.ofNanos(1000));
                if(!consumerRecords.isEmpty()) {
                    LOGGER.info("Fetched {} records in batch", consumerRecords.count());
                    consumerRecords.iterator().forEachRemaining(record ->
                            LOGGER.info("Topic={} Partition={} Timestamp={} Message={}",record.topic(),record.partition(), record.timestamp(), record.value()));
                }
            }
        }finally {
            announceTopicKafkaConsumer.close();
        }
    }
}
