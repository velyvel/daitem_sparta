package com.daitem.domain.order;

import com.daitem.domain.common.SearchDto;
import com.daitem.domain.order.dto.OrderListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.daitem.domain.order.entity.QOrder.order;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<OrderListDto> orderList(SearchDto condition, Pageable pageable, Long userId) {

        List<OrderListDto> content = queryFactory
                .select(Projections.fields(OrderListDto.class,
                        order.orderId,
                        order.orderDate,
                        order.totalAmount,
                        order.orderStatus,
                        order.paymentMethod,
                        order.shippingAddress1,
                        order.shippingAddress2,
                        order.shippingAddress3,
                        order.isDelivered,
                        order.deliveryDate
                ))
                .from(order)
                .where(order.user.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = queryFactory
                .select(Wildcard.count)
                .from(order);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

}
