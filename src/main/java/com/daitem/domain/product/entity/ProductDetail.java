package com.daitem.domain.product.entity;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.order.entity.Order;
import com.daitem.domain.order.entity.OrderDetail;
import com.daitem.domain.product.enumset.Colors;
import com.daitem.domain.product.enumset.DisplayType;
import com.daitem.domain.product.enumset.Size;
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
@Table(name = "product_detail")
public class ProductDetail {

    //상품 디테일 시퀀스
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long productDetailId;

    // 상품 시퀀스
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Convert(converter = Colors.ColorsConverter.class)
    private Colors color;
    @Convert(converter = Size.SizeConverter.class)
    private Size size;

    private YN isSaleTerms;
    private LocalDateTime saleFromDate;
    private LocalDateTime saleToDate;
    private int stock;
    private int price;

    // 진열여부
    @Convert(converter = DisplayType.DisplayConverter.class)
    private DisplayType displayType;

    //삭제여부 : 기본값 N, 진열여부가 delete 가 되면 바뀜
    @Convert(converter = YN.YNConverter.class)
    private YN isDeleted;

}
