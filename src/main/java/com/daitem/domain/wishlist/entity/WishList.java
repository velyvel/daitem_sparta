package com.daitem.domain.wishlist.entity;

import com.daitem.domain.product.entity.Product;
import com.daitem.domain.product.entity.ProductDetail;
import com.daitem.domain.user.entity.User;
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
@Table(name = "wish_list")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishId;

    private int count;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "productDetailId")
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 이건 디비에 넣지 않음
    // private Long sessionId;
}
