package com.daitem.domain.order;

import com.daitem.domain.order.entity.Order;
import com.daitem.domain.order.enumset.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface OrderRepository extends
        JpaRepository<Order, Long>,
        QuerydslPredicateExecutor<Order>,
        OrderRepositoryCustom{

    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
