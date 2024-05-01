package com.daitem.domain.order.entity;

import com.daitem.domain.product.entity.Product;
import com.daitem.domain.product.entity.ProductDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Data
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long orderDetailId;

    // Eager type 으로 고려해 볼 것
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    private Long productDetailId;

    //각 상품에 대한 수량
    private int quantity;

    // 각 상품 가격
    private int productPrice;

    // 각 상품에 대한 가격(상품테이블) * amount 수량
    //private int detailTotalPrice;

    //private int detailTotalPriceSum;

}
