package com.emiteai.challengeEmiteaiTms.service.impl;

import com.emiteai.challengeEmiteaiTms.data.domain.TmsOrder;
import com.emiteai.challengeEmiteaiTms.data.repositories.TmsOrderRepository;
import com.emiteai.challengeEmiteaiTms.enums.TmsOrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TmsOrderService {

    private static final Logger log = LoggerFactory.getLogger(TmsOrderService.class);
    @Autowired
    private TmsOrderRepository tmsOrderRepository;
    @Autowired
    private RabbitMqService mqService;

    public void doProcess() {
        log.info("started tms process");
        batchProcess(mqService.BATCH_SIZE, mqService.QUEUE_DLQ);
        log.info("ended tms process");
    }

    public void batchProcess(int BATCH_SIZE, String DLQ_QUEUE_NAME) {

        List<TmsOrder> tmsOrders = new ArrayList<>();
        List<Message> batch = new ArrayList<>();

        tmsOrders.add(
                tmsOrderRepository.save(
                        TmsOrder.builder()
                                .status(String.valueOf(TmsOrderStatus.EM_PROCESSAMENTO))
                                .build()));

        // popula o batch
        while (batch.size() < BATCH_SIZE) {
            Message nextMessage = mqService.receiveMessage(null);
            if (nextMessage == null) {
                break;
            }
            batch.add(nextMessage);
        }

        // Adiciona as mensagens represadas na dlq
        int dlqSize = mqService.getDlqQueueSize();
        if (dlqSize > 0) {
            List<Message> dlqMessages = new ArrayList<>();
            for (int i = 0; i < dlqSize && i < BATCH_SIZE - batch.size(); i++) {
                dlqMessages.add(mqService.receiveMessage(DLQ_QUEUE_NAME));
            }
            batch.addAll(0, dlqMessages);
        }

        batch.parallelStream().forEach(n -> {


                    try {
                        //todo - converter body da mensagem corretamente em numerico
                        log.info("mock processing of order with id - "+n.getBody().toString());
                        //todo - Para atualizar a referencia no db será necessario extrair o id do body corretamente
                    } catch (Exception e) {
                       //todo - Atualizar a referencia com id do body apontando erro.
                    }
                }
        );
    }
}
