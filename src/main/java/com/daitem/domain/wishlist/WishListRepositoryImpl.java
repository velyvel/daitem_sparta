package com.daitem.domain.wishlist;

import com.daitem.domain.product.entity.QProductDetail;
import com.daitem.domain.product.enumset.DisplayType;
import com.daitem.domain.wishlist.dto.WishListListDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.daitem.domain.product.entity.QProductDetail.productDetail;
import static com.daitem.domain.wishlist.entity.QWishList.wishList;

@Repository
@RequiredArgsConstructor
public class WishListRepositoryImpl implements WishListRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<WishListListDto> findByUserId(Long userId) {
        // 기록해두기 : 해결
        //QProductDetail d2 = new QProductDetail(productDetail);
        List<WishListListDto> content = queryFactory
                .select(Projections.fields(WishListListDto.class,
                        wishList.product.productId,
                        wishList.user.userId,
                        wishList.product.productName,
                        wishList.product.description,
                        wishList.product.productCategory,
                        wishList.productDetail.productDetailId,
                        wishList.productDetail.color,
                        wishList.productDetail.size,
                        wishList.productDetail.price,
                        wishList.count,
                        wishList.productDetail.displayType

//                        ExpressionUtils.as(
//                                JPAExpressions
//                                        .select(wishList.count())
//                                        .from(wishList)
//                                        .where(wishList.productDetail.productDetailId
//                                                .eq(productDetail.productDetailId)
//                                                .and(wishList.user.userId.eq(userId))),
//                                "count"
//                        )
                ))
                .from(wishList)
                //.join(d2).on(d2.productDetailId.eq(wishList.productDetail.productDetailId))
                .where(productDetail.displayType.eq(DisplayType.DISPLAYED)
                        .or(productDetail.displayType.eq(DisplayType.SOLDOUT))
                        .and(wishList.user.userId.eq(userId)))
//                .groupBy(wishList.product.productId,
//                        wishList.user.userId,
//                        wishList.product.productName,
//                        wishList.product.description,
//                        wishList.product.productCategory,
//                        wishList.productDetail.productDetailId,
//                        wishList.productDetail.color,
//                        wishList.productDetail.size,
//                        wishList.productDetail.price,
//                        wishList.productDetail.displayType)
                .fetch();

        return content;
    }
}
