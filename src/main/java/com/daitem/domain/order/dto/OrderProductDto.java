package com.daitem.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class OrderProductDto {
    private Long productId;
    private List<OrderProductDetailDto> productDetails;
}
