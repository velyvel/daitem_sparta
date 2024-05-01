package com.daitem.domain.order;


import com.daitem.domain.order.dto.OrderDetailListDto;
import com.daitem.domain.order.entity.Order;
import com.daitem.domain.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface OrderDetailRepository extends
        JpaRepository<OrderDetail, Long>,
        QuerydslPredicateExecutor<OrderDetail>,
        OrderDetailRepositoryCustom{

    void deleteByOrder(Order order);

    List<OrderDetail> findByOrder(Order order);
}
