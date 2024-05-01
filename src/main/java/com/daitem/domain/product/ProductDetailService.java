package com.daitem.domain.product;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.order.entity.OrderDetail;
import com.daitem.domain.product.dto.*;
import com.daitem.domain.product.entity.Product;
import com.daitem.domain.product.entity.ProductDetail;

import com.daitem.domain.product.enumset.DisplayType;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;

    @Transactional
    public List<ProductDetailDtoOriginal> getProductDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품이 조회되지 않습니다."));

        return productDetailRepository.findByProductId(product.getProductId());
    }

    @Transactional
    public void deleteProductDetail(Long id) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상세 상품이 없어요"));
        productDetail.setIsDeleted(YN.Y);
        productDetail.setDisplayType(DisplayType.DELETED);
    }

}

