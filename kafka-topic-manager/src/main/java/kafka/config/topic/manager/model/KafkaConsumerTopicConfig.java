package kafka.config.topic.manager.model;

import kafka.config.topic.manager.consumer.IConsumerRecordsProcessor;

import java.util.List;
import java.util.Properties;

public class KafkaConsumerTopicConfig {
    private String id;
    private String group;
    private Properties configProperties;
    private Long timeout;
    private List<Integer> partitions;
    private List<String> topics;
    private String consumerRecordsProcessorBean;
    private IConsumerRecordsProcessor consumerRecordsProcessor;

    public String getId() {
        return id;
    }

    public KafkaConsumerTopicConfig setId(String id) {
        this.id = id;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public KafkaConsumerTopicConfig setGroup(String group) {
        this.group = group;
        return this;
    }

    public Properties getConfigProperties() {
        return configProperties;
    }

    public KafkaConsumerTopicConfig setConfigProperties(Properties configProperties) {
        this.configProperties = configProperties;
        return this;
    }

    public List<Integer> getPartitions() {
        return partitions;
    }

    public KafkaConsumerTopicConfig setPartitions(List<Integer> partitions) {
        this.partitions = partitions;
        return this;
    }

    public List<String> getTopics() {
        return topics;
    }

    public KafkaConsumerTopicConfig setTopics(List<String> topics) {
        this.topics = topics;
        return this;
    }

    public Long getTimeout() {
        return timeout;
    }

    public KafkaConsumerTopicConfig setTimeout(Long timeout) {
        this.timeout = timeout;
        return this;
    }

    public String getConsumerRecordsProcessorBean() {
        return consumerRecordsProcessorBean;
    }

    public KafkaConsumerTopicConfig setConsumerRecordsProcessorBean(String consumerRecordsProcessor) {
        this.consumerRecordsProcessorBean = consumerRecordsProcessor;
        return this;
    }

    public IConsumerRecordsProcessor getConsumerRecordsProcessor() {
        return consumerRecordsProcessor;
    }

    public KafkaConsumerTopicConfig setConsumerRecordsProcessor(IConsumerRecordsProcessor consumerRecordsProcessor) {
        this.consumerRecordsProcessor = consumerRecordsProcessor;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KafkaConsumerTopicConfig{");
        sb.append("id='").append(id).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append(", configProperties=").append(configProperties);
        sb.append(", partitions=").append(partitions);
        sb.append('}');
        return sb.toString();
    }
}
