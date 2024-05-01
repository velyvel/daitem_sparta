package com.daitem.domain.order;

import com.daitem.domain.common.SearchDto;
import com.daitem.domain.order.dto.OrderListDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
    Page<OrderListDto> orderList(SearchDto condition, Pageable pageable, Long userId);
}
