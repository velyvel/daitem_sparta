package com.daitem.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailListDto {
    private Long productDetailId;
    private Long productId;
    private Long orderId;
    private int quantity;
    private int productPrice;
    // quantity * price
    // private int totalPrice;
    //private int totalSumPrice;

}
