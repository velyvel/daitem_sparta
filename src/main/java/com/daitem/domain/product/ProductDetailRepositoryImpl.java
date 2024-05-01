package com.daitem.domain.product;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.product.dto.ProductDetailDtoOriginal;
import com.daitem.domain.product.dto.ProductListDto;
import com.daitem.domain.product.entity.ProductDetail;
import com.daitem.domain.product.enumset.DisplayType;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.daitem.domain.product.entity.QProduct.product;
import static com.daitem.domain.product.entity.QProductDetail.productDetail;

@Repository
@RequiredArgsConstructor
public class ProductDetailRepositoryImpl implements ProductDetailRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductDetailDtoOriginal> findByProductId(Long productId) {
        List<ProductDetailDtoOriginal> content = queryFactory
                .select(Projections.fields(ProductDetailDtoOriginal.class,
                        productDetail.product.productId,
                        productDetail.productDetailId,
                        productDetail.color,
                        productDetail.size,
                        productDetail.stock,
//                        productDetail.isSaleTerms,
//                        productDetail.saleFromDate,
//                        productDetail.saleToDate,
                        productDetail.price,
                        productDetail.displayType,
                        productDetail.isDeleted
                ))
                .from(productDetail)
                .where(productDetail.displayType.eq(DisplayType.DISPLAYED)
                        .or(productDetail.displayType.eq(DisplayType.SOLDOUT))
                        .and(productDetail.isDeleted.eq(YN.N)))
                .leftJoin(product).on(product.productId.eq(productDetail.product.productId))
                .fetch();

        return content;
    }


    /**where 절 공통으로 뺄 내용
     * displayType :  SOLDOUT, DISPLAYED 일 때만 조회
     * saleTerms formDate ~ toDate 까지만 조회하기
     */


}
