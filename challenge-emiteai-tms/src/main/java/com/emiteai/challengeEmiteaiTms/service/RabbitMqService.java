package com.emiteai.challengeEmiteaiTms.service;
import java.io.Serializable;

public interface RabbitMqService extends Serializable {

    public void sendMessage(String message);

    public void receiveMessage(String message);
}
