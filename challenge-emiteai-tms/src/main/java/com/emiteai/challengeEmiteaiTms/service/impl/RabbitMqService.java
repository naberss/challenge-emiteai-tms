package com.emiteai.challengeEmiteaiTms.service.impl;

import com.emiteai.challengeEmiteaiTms.configuration.RabbitMqConfig;
import com.rabbitmq.client.AMQP;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@PropertySource("classpath:application.properties")
@Data
public class RabbitMqService  {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitMqConfig config;
    @Value("${batch.size}")
    public int BATCH_SIZE;
    @Value("${spring.rabbitmq.template.default-receive-queue-dlq}")
    public String QUEUE_DLQ;
    private static final Logger log = LoggerFactory.getLogger(RabbitMqService.class);

    public void sendMessage(long orderId) {
        rabbitTemplate.convertAndSend(config.EXCHANGE, config.ROUTING_KEY, orderId);
    }

    public Message receiveMessage(String queue){
        return (Objects.isNull(queue)?rabbitTemplate.receive():rabbitTemplate.receive(queue));
    }

    //retorna 0 caso 'getMessageCount' ou 'declareOk' forem nulos :)
    public int getDlqQueueSize() {
    return rabbitTemplate.execute(channel -> {
        try {
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclarePassive(QUEUE_DLQ);
            return declareOk.getMessageCount();
        } catch (Exception e) {
            return 0;
        }
    });
}
}
