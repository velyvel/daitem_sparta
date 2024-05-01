package com.daitem.domain.product;

import com.daitem.domain.common.SearchDto;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.product.dto.ProductDto;
import com.daitem.domain.product.dto.ProductListDto;

import com.daitem.domain.product.enumset.DisplayType;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;


import static com.daitem.domain.product.entity.QProduct.product;
import static com.daitem.domain.product.entity.QProductDetail.productDetail;


@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<ProductListDto> productList(SearchDto condition, Pageable pageable) {
        List<ProductListDto> content = queryFactory
                .select(Projections.fields(ProductListDto.class,
                        product.productId,
                        product.productName,
                        product.productCategory,
                        ExpressionUtils.as(
                                        JPAExpressions
                                                .select(Expressions.stringTemplate("GROUP_CONCAT(DISTINCT {0})", productDetail.color))
                                                //.from(productDetail)
                                                //.where(productDetail.product.productId.eq(product.productId))
                                , "colors")
                        ))
                .from(product)
                .leftJoin(productDetail).on(productDetail.product.productId.eq(product.productId))
                .where(productDetail.displayType.eq(DisplayType.DISPLAYED).and(product.isRealDelete.eq(YN.N)))
                .groupBy(product.productId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = queryFactory
                .select(Wildcard.count)
                .from(product)
                .leftJoin(productDetail).on(productDetail.product.productId.eq(product.productId))
                .where(productDetail.displayType.eq(DisplayType.DISPLAYED))
                .groupBy(product.productId);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    @Override
    public ProductDto getProductsWithDetails(Long id) {

        return queryFactory
                .select(Projections.fields(ProductDto.class,
                        product.productId,
                        product.productName,
                        product.productCategory,
                        product.description,
                        product.productDetails))
                .from(product)
                .where(product.productId.eq(id))
                .fetchOne();
    }
}
