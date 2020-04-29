package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class Producer {

private static final Logger logger = LoggerFactory.getLogger(Producer.class);

private static final String TOPIC = "metrics";

@Autowired

private KafkaTemplate<String,String> kafkaTemplate;

@Value(value = "${kafka.producer.bootstrap-servers}")
private String bootstrapAddress;
@Bean
public ProducerFactory<String, String> producerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
      bootstrapAddress);
    configProps.put(
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
      StringSerializer.class);
    configProps.put(
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
      StringSerializer.class);
    return new DefaultKafkaProducerFactory<>(configProps);
}


public void sendMessage(String message){

logger.info(String.format("$$ -> Producing message --> %s",message));

this.kafkaTemplate.send(TOPIC,message);

}

}
