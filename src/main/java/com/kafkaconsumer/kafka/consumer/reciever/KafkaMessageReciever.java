package com.kafkaconsumer.kafka.consumer.reciever;

import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafkaconsumer.kafka.consumer.entity.User;

@RestController
@RequestMapping("/consumer")
public class KafkaMessageReciever {
	
	List<String> messages = new ArrayList<>();
	private User userFromTopic;
	
	@KafkaListener(groupId = "Group 1", topics = "STRING_TOPIC", containerFactory ="kafkaListenerContainerFactory" )
	public List<String> getMessageFromTopic(String data) {
		messages.add(data);
		return messages;
	}
	
	@GetMapping("/getMessages")
	public List<String> getMessage() {
		return messages;
	}
	
	@KafkaListener(groupId = "Group 2", topics = "OBJECT_TOPIC", containerFactory ="userKafkaListenerContainerFactory" )
	public User getUserMessageFromTopic(User user) {
		userFromTopic = user;
		return userFromTopic;
	}
	
	@GetMapping("/getUserMessages")
	public User getUserMessage() {
		return userFromTopic;
	}

}
