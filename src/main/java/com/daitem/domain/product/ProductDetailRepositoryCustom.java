package com.daitem.domain.product;

import com.daitem.domain.product.dto.ProductDetailDtoOriginal;
import com.daitem.domain.product.entity.ProductDetail;

import java.util.List;

public interface ProductDetailRepositoryCustom {
    List<ProductDetailDtoOriginal> findByProductId(Long productId);

}
