package com.emiteai.challengeEmiteaiTms.service.impl;

import com.emiteai.challengeEmiteaiTms.configuration.RabbitMqConfig;
import com.emiteai.challengeEmiteaiTms.service.RabbitMqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class RabbitMqServiceImpl implements RabbitMqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMqConfig config;
    private static final Logger log = LoggerFactory.getLogger(RabbitMqServiceImpl.class);

    @Override
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(config.EXCHANGE, config.ROUTING_KEY, message);
    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void receiveMessage(String message) {
        log.info("Received message: " + message);
    }
}
