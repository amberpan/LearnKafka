package kafka.consumer.runner;

import kafka.consumer.config.KafkaConsumerRunnable;
import kafka.consumer.model.KafkaConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
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

        ExecutorService executorService = Executors.newFixedThreadPool(4,
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        Thread t = Executors.defaultThreadFactory().newThread(r);
                        t.setDaemon(true);
                        return t;
                    }
                });

        KafkaConsumerConfig announceTopicConsumerConfig = new KafkaConsumerConfig()
        .setTopicGroup(announceTopicGroup)
                .setTopics(Arrays.asList(announceTopic))
                .setKeyDeserializer(StringDeserializer.class)
                .setValueDeserializer(StringDeserializer.class)
                .setServers(servers);

        KafkaConsumerConfig announceTopicWithPartitionsConsumerConfig = new KafkaConsumerConfig()
                .setTopicGroup(announceTopicGroup)
                .setTopics(Arrays.asList(announceTopicPartitioned))
                .setKeyDeserializer(StringDeserializer.class)
                .setValueDeserializer(StringDeserializer.class)
                .setServers(servers);

        KafkaConsumerConfig consumerForPartition0 = new KafkaConsumerConfig()
                .setTopicGroup(announceTopicGroup)
                .setTopics(Arrays.asList(announceTopicPartitioned))
                .setKeyDeserializer(StringDeserializer.class)
                .setValueDeserializer(StringDeserializer.class)
                .setServers(servers)
                .setPartitions(Arrays.asList(0));

        executorService.submit(new KafkaConsumerRunnable<String,String>(announceTopicConsumerConfig,getKafkaConsumer(announceTopicConsumerConfig)));
        executorService.submit(new KafkaConsumerRunnable<String,String>(announceTopicWithPartitionsConsumerConfig,getKafkaConsumer(announceTopicWithPartitionsConsumerConfig)));
        executorService.submit(new KafkaConsumerRunnable<String,String>(consumerForPartition0,getKafkaConsumer(consumerForPartition0)));

        Thread.currentThread().join();
    }

    public KafkaConsumer getKafkaConsumer(KafkaConsumerConfig config){
        Properties prop = new Properties();
        prop.put("bootstrap.servers",config.getServers());
        prop.put("key.deserializer",config.getKeyDeserializer());
        prop.put("value.deserializer",config.getValueDeserializer());
        prop.put("group.id",config.getTopicGroup());
        return new KafkaConsumer(prop);
    }
}
