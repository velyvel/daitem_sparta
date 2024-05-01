package com.daitem.domain.product.dto;

import com.daitem.domain.product.enumset.Categories;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto {
    private Long productId;
    private String productName;
    @Convert(converter = Categories.CategoriesConverter.class)
    private Categories productCategory;
    private String description;

    private List<ProductDetailDto> productDetails;
}

