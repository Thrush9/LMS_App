//package com.lmsapp.main.kafka;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaConsumer {
//	
//	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
//	
//	@KafkaListener(topics = "login_userdetails", groupId = "loginDetails")
//	public void consumeLoginDetails(String deatils) {
//		LOGGER.info(deatils);
//	}
//
//}
//
