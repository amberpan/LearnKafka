package kafka.consumer.model;


import org.apache.kafka.common.serialization.Deserializer;

import java.util.List;

public class KafkaConsumerConfig {
    List<String> servers;
    Class keyDeserializer;
    Class valueDeserializer;
    String topicGroup;
    List<String> topics;
    List<Integer> partitions;
    long timeout = 100L;

    public List<String> getServers() {
        return servers;
    }

    public KafkaConsumerConfig setServers(List<String> servers) {
        this.servers = servers;
        return this;
    }

    public Class getKeyDeserializer() {
        return keyDeserializer;
    }

    public KafkaConsumerConfig setKeyDeserializer(Class keyDeserializer) {
        this.keyDeserializer = keyDeserializer;
        return this;
    }

    public Class getValueDeserializer() {
        return valueDeserializer;
    }

    public KafkaConsumerConfig setValueDeserializer(Class valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
        return this;
    }

    public String getTopicGroup() {
        return topicGroup;
    }

    public KafkaConsumerConfig setTopicGroup(String topicGroup) {
        this.topicGroup = topicGroup;
        return this;
    }

    public List<String> getTopics() {
        return topics;
    }

    public KafkaConsumerConfig setTopics(List<String> topics) {
        this.topics = topics;
        return this;
    }

    public List<Integer> getPartitions() {
        return partitions;
    }

    public KafkaConsumerConfig setPartitions(List<Integer> partitions) {
        this.partitions = partitions;
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    public KafkaConsumerConfig setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KafkaConsumerConfig{");
        sb.append("servers=").append(servers);
        sb.append(", keyDeserializer='").append(keyDeserializer).append('\'');
        sb.append(", valueDeserializer='").append(valueDeserializer).append('\'');
        sb.append(", topicGroup='").append(topicGroup).append('\'');
        sb.append(", topic='").append(topics).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
