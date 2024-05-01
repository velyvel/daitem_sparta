package com.daitem.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductDetail is a Querydsl query type for ProductDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductDetail extends EntityPathBase<ProductDetail> {

    private static final long serialVersionUID = -1462678419L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductDetail productDetail = new QProductDetail("productDetail");

    public final EnumPath<com.daitem.domain.product.enumset.Colors> color = createEnum("color", com.daitem.domain.product.enumset.Colors.class);

    public final EnumPath<com.daitem.domain.product.enumset.DisplayType> displayType = createEnum("displayType", com.daitem.domain.product.enumset.DisplayType.class);

    public final EnumPath<com.daitem.domain.common.enumset.YN> isDeleted = createEnum("isDeleted", com.daitem.domain.common.enumset.YN.class);

    public final EnumPath<com.daitem.domain.common.enumset.YN> isSaleTerms = createEnum("isSaleTerms", com.daitem.domain.common.enumset.YN.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final QProduct product;

    public final NumberPath<Long> productDetailId = createNumber("productDetailId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> saleFromDate = createDateTime("saleFromDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> saleToDate = createDateTime("saleToDate", java.time.LocalDateTime.class);

    public final EnumPath<com.daitem.domain.product.enumset.Size> size = createEnum("size", com.daitem.domain.product.enumset.Size.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QProductDetail(String variable) {
        this(ProductDetail.class, forVariable(variable), INITS);
    }

    public QProductDetail(Path<? extends ProductDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductDetail(PathMetadata metadata, PathInits inits) {
        this(ProductDetail.class, metadata, inits);
    }

    public QProductDetail(Class<? extends ProductDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product")) : null;
    }

}

