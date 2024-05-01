package com.daitem.domain.product;

import com.daitem.domain.common.SearchDto;
import com.daitem.domain.product.dto.ProductDetailDto;
import com.daitem.domain.product.dto.ProductDto;
import com.daitem.domain.product.dto.ProductListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    Page<ProductListDto> productList(SearchDto condition, Pageable pageable);

    //ProductDto productDetail(Long id);

    ProductDto getProductsWithDetails(Long id);
}
