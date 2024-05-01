package com.daitem.domain.order.entity;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.order.enumset.OrderStatus;
import com.daitem.domain.order.enumset.PaymentMethod;
import com.daitem.domain.product.entity.ProductDetail;
import com.daitem.domain.product.enumset.Categories;
import com.daitem.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime orderDate;

    // 주문한 상품 총 개수 -> 상품 하나에 대한 개수랑은 다름
    private int totalAmount;

    @Convert(converter = OrderStatus.OrdersConverter.class)
    private OrderStatus orderStatus;

    @Convert(converter = PaymentMethod.PaymentMethodConverter.class)
    private PaymentMethod paymentMethod;

    private String shippingAddress1;
    private String shippingAddress2;
    private String shippingAddress3;

    private YN isDelivered;

    private LocalDateTime deliveryDate;

    // lazy type 으로 제공되는데, eager 도 성능상 이점이 있는지 고려하기
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

}
