package kafka.consumer.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public KafkaConsumer<String,String> announceTopicKafkaConsumer(
            @Value("${bootstrap.servers}") List<String> servers,
            @Value("${topic.announce.key.deserializer}") String keySerializer,
            @Value("${topic.announce.value.deserializer}") String valueSerializer,
            @Value("${kafka.consumer.generic.topic.group}") String topicGroup
    ){
        Properties prop = new Properties();
        prop.put("bootstrap.servers",servers);
        prop.put("key.deserializer",keySerializer);
        prop.put("value.deserializer",valueSerializer);
        prop.put("group.id",topicGroup);
        return new KafkaConsumer<String, String>(prop);
    }
}
