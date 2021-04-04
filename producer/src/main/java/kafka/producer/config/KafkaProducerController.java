package kafka.producer.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.LongStream;

@RestController
//@RequestMapping("kafka")
public class KafkaProducerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerController.class);

    @Value("${kafka.publisher.generic.topic}")
    String announceTopic;

    @Value("${kafka.publisher.generic.topic.partitioned}")
    String announceTopicPartitioned;

    @Autowired
    KafkaProducer<String,String> kafkaProducer;

    @GetMapping(value = "/publish/{count}")
    public String announce(@PathVariable("count") Long count) {
        try {
            LongStream.rangeClosed(1, count).forEach(c -> {
                Future<RecordMetadata> recordMetadataFuture = kafkaProducer.send(new ProducerRecord(announceTopic, "Announcement: " + c));

                try {
                    RecordMetadata recordMetadata = recordMetadataFuture.get();
                    LOGGER.info("Record sent timestamp: {}",recordMetadata.timestamp());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            return "Could not publish messages to Kafka";
        }
        return "Request completed successfully";
    }

    @GetMapping(value = "/publishPartition/{count}")
    public String publishWithPartition(@PathVariable("count") Long count) {
        try {
            LongStream.rangeClosed(1, count).forEach(c -> {
                Future<RecordMetadata> recordMetadataFuture = kafkaProducer.send(new ProducerRecord(announceTopicPartitioned,String.valueOf(c), "Announcement: " + c));

                try {
                    RecordMetadata recordMetadata = recordMetadataFuture.get();
                    LOGGER.info("Record sent with timestamp={} and partition={}",recordMetadata.timestamp(),recordMetadata.partition());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            return "Could not publish messages to Kafka";
        }
        return "Request completed successfully";
    }

    @GetMapping(value = "/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        return "Welcome "+name+"!";
    }
}
