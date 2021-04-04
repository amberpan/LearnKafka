package kafka.consumer.runner;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Component
public class KafkaConsumerCommandLineRunner implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerCommandLineRunner.class);

    @Value("${kafka.consumer.generic.topic}")
    String announceTopic;

    @Value("${kafka.consumer.generic.topic.group}")
    String announceTopicGroup;

    @Value("${kafka.consumer.generic.topic.partitioned}")
    String announceTopicPartitioned;

    @Value("${bootstrap.servers}")
    List<String> servers;

    @Autowired
    KafkaConsumer<String,String> announceTopicKafkaConsumer;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Initializing kafka consumers");


        Thread.currentThread().join();
    }

}
