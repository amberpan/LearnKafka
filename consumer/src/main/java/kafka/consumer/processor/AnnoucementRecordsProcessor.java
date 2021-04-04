package kafka.consumer.processor;

import kafka.config.topic.manager.consumer.IConsumerRecordsProcessor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("announcementRecordsProcessor")
public class AnnoucementRecordsProcessor implements IConsumerRecordsProcessor<String,String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnoucementRecordsProcessor.class);

    @Override
    public void processRecords(ConsumerRecords<String, String> consumerRecords) {
        consumerRecords.iterator().forEachRemaining(record -> {
            LOGGER.info("Processing record from Topic={} Partition={} Message={}",
                    record.topic(), record.partition(), record.value());
        });
    }
}
