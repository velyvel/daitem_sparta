package com.daitem.domain.order.dto;

import com.daitem.domain.product.enumset.Colors;
import com.daitem.domain.product.enumset.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductDetailDto {
    private Long productDetailId;
    private int amount;
}
