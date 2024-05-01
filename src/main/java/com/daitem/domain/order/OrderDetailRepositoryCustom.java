package com.daitem.domain.order;

import com.daitem.domain.order.dto.OrderDetailListDto;

import java.util.List;

public interface OrderDetailRepositoryCustom {
    List<OrderDetailListDto> findByOrderIdAndUserId(Long orderId, Long userId);
}
