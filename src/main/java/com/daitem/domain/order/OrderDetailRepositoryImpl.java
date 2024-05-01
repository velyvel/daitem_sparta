package com.daitem.domain.order;

import com.daitem.domain.order.dto.OrderDetailListDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.daitem.domain.order.entity.QOrder.order;
import static com.daitem.domain.order.entity.QOrderDetail.orderDetail;
import static com.daitem.domain.product.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class OrderDetailRepositoryImpl implements OrderDetailRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderDetailListDto> findByOrderIdAndUserId(Long orderId, Long userId) {
        List<OrderDetailListDto> content;
        content = queryFactory
                .select(Projections.fields(OrderDetailListDto.class,
                        orderDetail.productDetailId,
                        orderDetail.product.productId,
                        order.orderId,
                        orderDetail.quantity,
                        orderDetail.productPrice
//                        ExpressionUtils.as(
//                                orderDetail.productPrice.multiply(orderDetail.quantity).sum(),
//                                "totalPrice"
//                        )
                    ))
                .from(orderDetail)
                .leftJoin(order).on(order.orderId.eq(orderDetail.order.orderId))
                .where(order.user.userId.eq(userId)
                        .and(order.orderId.eq(orderId)))
                .orderBy(orderDetail.productDetailId.desc())
                .fetch();

        return content;
    }
}
