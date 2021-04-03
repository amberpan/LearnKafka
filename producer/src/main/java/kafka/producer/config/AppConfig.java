package kafka.producer.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public KafkaProducer<String,String> announceTopicKafkaProducer(
            @Value("${bootstrap.servers}") List<String> servers,
            @Value("${topic.announce.key.serializer}") String keySerializer,
            @Value("${topic.announce.value.serializer}") String valueSerializer
    ){
        Properties prop = new Properties();
        prop.put("bootstrap.servers",servers);
        prop.put("key.serializer",keySerializer);
        prop.put("value.serializer",valueSerializer);
        return new KafkaProducer(prop);
    }
}
