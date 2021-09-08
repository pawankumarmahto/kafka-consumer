package com.kafkaconsumer.kafka.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.kafkaconsumer.kafka.consumer.entity.User;

@Configuration
public class KafkaConsumerConfig {

	
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> config= new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092" );
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "Group 1");
		return new DefaultKafkaConsumerFactory<String, String>(config);
	}
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new  ConcurrentKafkaListenerContainerFactory<String, String>();
		 factory.setConsumerFactory(consumerFactory());
		 return factory;
	}
	
	@Bean
	public ConsumerFactory<String, User> userConsumerFactory() {
		
		JsonDeserializer<User> deserializer = new JsonDeserializer<>(User.class);
	    deserializer.setRemoveTypeHeaders(false);
	    deserializer.addTrustedPackages("*");
	    deserializer.setUseTypeMapperForKey(true);
		
		
		Map<String, Object> config= new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092" );
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class );
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "Group 2");
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
	}
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, User> factory = new  ConcurrentKafkaListenerContainerFactory<String, User>();
		 factory.setConsumerFactory(userConsumerFactory());
		 return factory;
	}
}
