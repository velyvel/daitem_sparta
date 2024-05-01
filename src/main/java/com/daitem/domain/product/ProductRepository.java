package com.daitem.domain.product;

import com.daitem.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository
        extends
        JpaRepository<Product, Long>,
        QuerydslPredicateExecutor<Product>,
        ProductRepositoryCustom {
}
