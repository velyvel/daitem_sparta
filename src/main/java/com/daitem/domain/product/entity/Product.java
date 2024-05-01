package com.daitem.domain.product.entity;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.order.entity.OrderDetail;
import com.daitem.domain.product.enumset.Categories;
import com.daitem.domain.product.enumset.Colors;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;

    @Convert(converter = Categories.CategoriesConverter.class)
    private Categories productCategory;

    private String description;

    private YN isRealDelete;

    @OneToMany(mappedBy = "product")
    private List<ProductDetail> productDetails;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    //size, color 은 참조만

}
