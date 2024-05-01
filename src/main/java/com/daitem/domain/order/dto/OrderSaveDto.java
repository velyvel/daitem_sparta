package com.daitem.domain.order.dto;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.order.enumset.OrderStatus;
import com.daitem.domain.order.enumset.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderSaveDto {
    private Long userId;
    private LocalDateTime orderDate;
    private int totalAmount;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;

    private String shippingAddress1;
    private String shippingAddress2;
    private String shippingAddress3;
    private YN isDelivered;
    //주문 상세 리스트
    private List<OrderProductDto> products;
}
