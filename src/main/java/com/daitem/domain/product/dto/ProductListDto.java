package com.daitem.domain.product.dto;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.product.enumset.Categories;
import com.daitem.domain.product.enumset.Colors;

import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.logging.log4j2.ColorConverter;


@Getter
@Setter
public class ProductListDto {

    private Long productId;
    private String productName;
    @Convert(converter = ColorConverter.class)
    private Categories productCategory;
//    @Convert(converter = ColorConverter.class)
//    private Colors color;
    private String colors;
}
