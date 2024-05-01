package com.daitem.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 1030322396L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final DateTimePath<java.time.LocalDateTime> deliveryDate = createDateTime("deliveryDate", java.time.LocalDateTime.class);

    public final EnumPath<com.daitem.domain.common.enumset.YN> isDelivered = createEnum("isDelivered", com.daitem.domain.common.enumset.YN.class);

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final ListPath<OrderDetail, QOrderDetail> orderDetails = this.<OrderDetail, QOrderDetail>createList("orderDetails", OrderDetail.class, QOrderDetail.class, PathInits.DIRECT2);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final EnumPath<com.daitem.domain.order.enumset.OrderStatus> orderStatus = createEnum("orderStatus", com.daitem.domain.order.enumset.OrderStatus.class);

    public final EnumPath<com.daitem.domain.order.enumset.PaymentMethod> paymentMethod = createEnum("paymentMethod", com.daitem.domain.order.enumset.PaymentMethod.class);

    public final StringPath shippingAddress1 = createString("shippingAddress1");

    public final StringPath shippingAddress2 = createString("shippingAddress2");

    public final StringPath shippingAddress3 = createString("shippingAddress3");

    public final NumberPath<Integer> totalAmount = createNumber("totalAmount", Integer.class);

    public final com.daitem.domain.user.entity.QUser user;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.daitem.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

