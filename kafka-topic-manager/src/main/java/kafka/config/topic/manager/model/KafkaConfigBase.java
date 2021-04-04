package kafka.config.topic.manager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaConfigBase {
    private List<String> topics;
    private List<String> bootstrapServers;
    private Integer totalPartitions;
    private KafkaConsumerConfigBase consumerConfig;


    public List<String> getTopics() {
        return topics;
    }

    public KafkaConfigBase setTopics(List<String> topics) {
        this.topics = topics;
        return this;
    }

    public List<String> getBootstrapServers() {
        return bootstrapServers;
    }

    public KafkaConfigBase setBootstrapServers(List<String> bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
        return this;
    }

    public Integer getTotalPartitions() {
        return totalPartitions;
    }

    public KafkaConfigBase setTotalPartitions(Integer partitions) {
        this.totalPartitions = partitions;
        return this;
    }

    public KafkaConsumerConfigBase getConsumerConfig() {
        return consumerConfig;
    }

    public KafkaConfigBase setConsumerConfig(KafkaConsumerConfigBase kafkaConsumerConfig) {
        this.consumerConfig = kafkaConsumerConfig;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KafkaConsumerConfigBase{");
        sb.append("topics=").append(topics);
        sb.append(", bootstrapServers=").append(bootstrapServers);
        sb.append(", totalPartitions=").append(totalPartitions);
        sb.append(", kafkaConsumerConfig=").append(consumerConfig);
        sb.append('}');
        return sb.toString();
    }
}
