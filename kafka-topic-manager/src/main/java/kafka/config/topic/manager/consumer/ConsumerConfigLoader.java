package kafka.config.topic.manager.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.config.topic.manager.model.FetchMechanism;
import kafka.config.topic.manager.model.KafkaConfigBase;
import kafka.config.topic.manager.model.KafkaConsumerTopicConfig;
import kafka.config.topic.manager.model.Pair;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class ConsumerConfigLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerConfigLoader.class);

    @Value("${consumer.config.locations}")
    private String configLocation;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ApplicationContext applicationContext;

    @PostConstruct
    public void init() throws IOException {
        KafkaConfigBase kafkaConfigBase = loadContent();

        prepareConfig(kafkaConfigBase);

        validateConfig(kafkaConfigBase);

        List<Pair<KafkaConsumerTopicConfig,KafkaConsumer>> kafkaConsumerList = initializeConsumers(kafkaConfigBase);

        ExecutorService executorService = Executors.newFixedThreadPool(kafkaConsumerList.size());
        kafkaConsumerList.forEach(c->executorService.submit(new KafkaConsumerRunner(c.getKey(),c.getValue())));

    }

    private void validateConfig(KafkaConfigBase kafkaConfigBase) {
        int totalPartitions = kafkaConfigBase.getTotalPartitions();

        kafkaConfigBase.getConsumerConfig().getConsumers().forEach(c->{
            if(kafkaConfigBase.getConsumerConfig().getFetchMechanism() == FetchMechanism.PARTITION) {
                LOGGER.debug("Validating partition number range");
                Set<Integer> invalidPartitions = c.getPartitions().stream().filter(p -> p > totalPartitions - 1).collect(Collectors.toSet());
                if(!ObjectUtils.isEmpty(invalidPartitions))
                    throw new RuntimeException("Out of range partition numbers provided. Please correct."+invalidPartitions);
            }else{
                if(c.getPartitions() != null)
                    LOGGER.warn("Partitions will not be used with TOPIC fetch mechanism");
            }
        });
    }

    private List<Pair<KafkaConsumerTopicConfig,KafkaConsumer>> initializeConsumers(KafkaConfigBase kafkaConfigBase) {
        List<Pair<KafkaConsumerTopicConfig,KafkaConsumer>> list = new ArrayList<>();
                kafkaConfigBase.getConsumerConfig().getConsumers()
                .forEach(c->{
                    list.add(new Pair(c,new KafkaConsumer(c.getConfigProperties())));
                });

        return list;
    }

    private void prepareConfig(KafkaConfigBase kafkaConfigBase) {

        kafkaConfigBase.getConsumerConfig().getConsumers().forEach(c->{
            try {
                c.getConfigProperties().put("bootstrap.servers",kafkaConfigBase.getBootstrapServers());
                c.getConfigProperties().put("key.deserializer",Class.forName(kafkaConfigBase.getConsumerConfig().getKeyDeserializer()));
                c.getConfigProperties().put("value.deserializer",Class.forName(kafkaConfigBase.getConsumerConfig().getEventDeserializer()));
                c.getConfigProperties().put("group.id",c.getGroup());
                c.setTopics(kafkaConfigBase.getTopics());
                try{
                    c.setConsumerRecordsProcessor(applicationContext.getBean(c.getConsumerRecordsProcessorBean(),IConsumerRecordsProcessor.class));
                }catch (NoSuchBeanDefinitionException e){
                    throw new RuntimeException("Could not find bean of type IConsumerRecordsProcessor with name: "+c.getConsumerRecordsProcessorBean()+". Consider defining one");
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Exception occurred while preparing consumer config",e);
            }

        });
    }

    private KafkaConfigBase loadContent() throws IOException {
        String content = IOUtils.toString(new ClassPathResource(configLocation).getURI(),StandardCharsets.UTF_8);
        LOGGER.info("Raw Content: {}",content);

        KafkaConfigBase kafkaConfigBase = objectMapper.readValue(content,KafkaConfigBase.class);

        LOGGER.info("Parsed content: {}",kafkaConfigBase);

        return kafkaConfigBase;
    }
}
