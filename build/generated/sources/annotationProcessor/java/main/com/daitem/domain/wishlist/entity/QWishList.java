package com.daitem.domain.wishlist.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWishList is a Querydsl query type for WishList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWishList extends EntityPathBase<WishList> {

    private static final long serialVersionUID = -36027488L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWishList wishList = new QWishList("wishList");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final com.daitem.domain.product.entity.QProduct product;

    public final com.daitem.domain.product.entity.QProductDetail productDetail;

    public final com.daitem.domain.user.entity.QUser user;

    public final NumberPath<Long> wishId = createNumber("wishId", Long.class);

    public QWishList(String variable) {
        this(WishList.class, forVariable(variable), INITS);
    }

    public QWishList(Path<? extends WishList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWishList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWishList(PathMetadata metadata, PathInits inits) {
        this(WishList.class, metadata, inits);
    }

    public QWishList(Class<? extends WishList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.daitem.domain.product.entity.QProduct(forProperty("product")) : null;
        this.productDetail = inits.isInitialized("productDetail") ? new com.daitem.domain.product.entity.QProductDetail(forProperty("productDetail"), inits.get("productDetail")) : null;
        this.user = inits.isInitialized("user") ? new com.daitem.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

