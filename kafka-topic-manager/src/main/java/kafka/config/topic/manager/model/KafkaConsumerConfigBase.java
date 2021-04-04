package kafka.config.topic.manager.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaConsumerConfigBase {
    private String keyDeserializer;
    private String eventDeserializer;
    private FetchMechanism fetchMechanism = FetchMechanism.TOPIC;
    private List<KafkaConsumerTopicConfig> consumers;

    public String getKeyDeserializer() {
        return keyDeserializer;
    }

    public KafkaConsumerConfigBase setKeyDeserializer(String keyDeserializer) throws ClassNotFoundException {
        this.keyDeserializer = keyDeserializer;
        return this;
    }

    public String getEventDeserializer() {
        return eventDeserializer;
    }

    public KafkaConsumerConfigBase setEventDeserializer(String eventDeserializer) {
        this.eventDeserializer = eventDeserializer;
        return this;
    }

    public FetchMechanism getFetchMechanism() {
        return fetchMechanism;
    }

    public KafkaConsumerConfigBase setFetchMechanism(FetchMechanism fetchMechanism) {
        this.fetchMechanism = fetchMechanism;
        return this;
    }

    public List<KafkaConsumerTopicConfig> getConsumers() {
        return consumers;
    }

    public KafkaConsumerConfigBase setConsumers(List<KafkaConsumerTopicConfig> consumers) {
        this.consumers = consumers;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KafkaConsumerConfigBase{");
        sb.append("keyDeserializer=").append(keyDeserializer);
        sb.append(", valueDeserializer=").append(eventDeserializer);
        sb.append(", fetchMechanism=").append(fetchMechanism);
        sb.append(", consumers=").append(consumers);
        sb.append('}');
        return sb.toString();
    }
}
