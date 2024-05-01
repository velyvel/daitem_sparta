package com.daitem.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 275182844L;

    public static final QProduct product = new QProduct("product");

    public final StringPath description = createString("description");

    public final EnumPath<com.daitem.domain.common.enumset.YN> isRealDelete = createEnum("isRealDelete", com.daitem.domain.common.enumset.YN.class);

    public final ListPath<com.daitem.domain.order.entity.OrderDetail, com.daitem.domain.order.entity.QOrderDetail> orderDetails = this.<com.daitem.domain.order.entity.OrderDetail, com.daitem.domain.order.entity.QOrderDetail>createList("orderDetails", com.daitem.domain.order.entity.OrderDetail.class, com.daitem.domain.order.entity.QOrderDetail.class, PathInits.DIRECT2);

    public final EnumPath<com.daitem.domain.product.enumset.Categories> productCategory = createEnum("productCategory", com.daitem.domain.product.enumset.Categories.class);

    public final ListPath<ProductDetail, QProductDetail> productDetails = this.<ProductDetail, QProductDetail>createList("productDetails", ProductDetail.class, QProductDetail.class, PathInits.DIRECT2);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final StringPath productName = createString("productName");

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

