package com.daitem.domain.product;

import com.daitem.domain.product.entity.Product;
import com.daitem.domain.product.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public interface ProductDetailRepository extends
        JpaRepository<ProductDetail, Long>,
        QuerydslPredicateExecutor<ProductDetail>,
        ProductDetailRepositoryCustom {

    //Optional<ProductDetail> findByProductDetailId(Long productDetailId);

    List<ProductDetail> findByProduct(Product product);
}
