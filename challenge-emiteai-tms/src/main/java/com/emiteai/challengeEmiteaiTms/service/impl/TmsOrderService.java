package com.emiteai.challengeEmiteaiTms.service.impl;

import com.emiteai.challengeEmiteaiTms.data.domain.Order;
import com.emiteai.challengeEmiteaiTms.data.domain.TmsOrder;
import com.emiteai.challengeEmiteaiTms.data.repositories.TmsOrderRepository;
import com.emiteai.challengeEmiteaiTms.data.repositories.OrderRepository;
import com.emiteai.challengeEmiteaiTms.enums.TmsOrderStatus;
import com.emiteai.challengeEmiteaiTms.enums.OrderStatus;
import com.google.gson.Gson;
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
    private OrderRepository orderRepository;
    @Autowired
    private RabbitMqService mqService;

    public void doProcess() {
        log.info("started tms process");
        batchProcess(mqService.BATCH_SIZE, mqService.QUEUE_DLQ);
        log.info("ended tms process");
    }

    public void batchProcess(int BATCH_SIZE, String DLQ_QUEUE_NAME) {

        List<Message> batch = new ArrayList<>();
        TmsOrder tmsOrder = tmsOrderRepository.save(
                        TmsOrder.builder()
                                .status(String.valueOf(TmsOrderStatus.EM_PROCESSAMENTO))
                                .build());

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

        List<Long> processedOrders =  new ArrayList<>();
        batch.parallelStream().forEach(n -> {
                    try {
                        processedOrders.add(
                                (long) orderRepository.updateOrderStatusById(String.valueOf(OrderStatus.ENVIADO),
                                        Long.valueOf(new String(n.getBody())))
                        );
                    } catch (Exception e) {
                        orderRepository.updateOrderStatusById(String.valueOf(OrderStatus.NAO_PROCESSADO),
                                                                            Long.valueOf( new String(n.getBody()) ));
                    }
                }
        );
        // Atualiza lista apenas com as ordens processadas
        tmsOrder.setOrderId(new Gson().toJson(processedOrders));
        tmsOrderRepository.save(tmsOrder);
    }
}
