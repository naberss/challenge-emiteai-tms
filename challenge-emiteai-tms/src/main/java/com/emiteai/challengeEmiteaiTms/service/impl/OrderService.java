package com.emiteai.challengeEmiteaiTms.service.impl;

import com.emiteai.challengeEmiteaiTms.data.domain.Order;
import com.emiteai.challengeEmiteaiTms.data.domain.Product;
import com.emiteai.challengeEmiteaiTms.data.repositories.OrderRepository;
import com.emiteai.challengeEmiteaiTms.dto.ProcessResponseDto;
import com.emiteai.challengeEmiteaiTms.dto.ProductDto;
import com.emiteai.challengeEmiteaiTms.enums.OrderStatus;
import com.emiteai.challengeEmiteaiTms.exception.FailedOrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private RabbitMqService mqService;
    @Value("${order.description.sucessfully.completed}")
    String  SUCESSFULLY_COMPLETED;
    @Value("${order.description.partially.completed}")
    String  PARTIALY_COMPLETED;
    @Value("${order.description.error}")
    String  IGNORED;

    public ProcessResponseDto doProcess(List<ProductDto> products) throws FailedOrderException {

        final AtomicReference<List<Long>> unregistered = new AtomicReference<>(new ArrayList<Long>());
        final AtomicReference<List<Product>> registered = new AtomicReference<>(new ArrayList<Product>());
        AtomicReference<BigDecimal> totalValue = new AtomicReference<BigDecimal>(BigDecimal.ZERO);

        products.parallelStream().forEach(productDto -> {
            Product product = productService.findById(productDto.getProductId());
            if (Objects.isNull(product)) {
                unregistered.get().add(productDto.getProductId());
            } else {
                registered.get().add(product);
                totalValue.updateAndGet(currentValue ->
                        currentValue.add((product.getUnitValue().multiply(BigDecimal.valueOf(productDto.getQuantity())))));
            }
        });

        return validateProcess(unregistered.get(),registered.get(), totalValue.get());
    }

    public ProcessResponseDto validateProcess(List<Long> unregistered, List<Product> registered, BigDecimal registeredTotalValue)
            throws FailedOrderException {

        if (unregistered.isEmpty()) {
            if (registered.isEmpty()) {
                return ProcessResponseDto.buildUndetailedResponse(
                        false,
                        IGNORED,
                        OrderStatus.NAO_PROCESSADO.toString());
            } else {
                createOrder(registered,registeredTotalValue,OrderStatus.ENVIO_PENDENTE.toString());
                return ProcessResponseDto.buildUndetailedResponse(
                        true,
                        SUCESSFULLY_COMPLETED,
                        OrderStatus.ENVIO_PENDENTE.toString());
            }
        } else {
            createOrder(registered,registeredTotalValue,OrderStatus.ENVIO_PENDENTE.toString());
            return ProcessResponseDto.buildDetailedResponse(
                    true,
                    PARTIALY_COMPLETED,
                    OrderStatus.PARCIALMENTE_PROCESSADO.toString(),
                    unregistered);
        }
    }

    private Order createOrder(List<Product> registered,BigDecimal registeredTotalValue,String status)
            throws FailedOrderException {
        try{
           Order order = orderRepository.save(
                        Order.builder()
                                .creationDate(LocalDateTime.now())
                                .products(new Gson().toJson(registered))
                                .status(status)
                                .totalValue(addCharge(registeredTotalValue)).build() );
           mqService.sendMessage(order.getId());
           return order;
        } catch (Exception e) {
            throw new FailedOrderException(e);
        }
    }


    private BigDecimal addCharge(BigDecimal Oldvalue){
        return Oldvalue
                .multiply(BigDecimal.valueOf(1.1));
    }
}
