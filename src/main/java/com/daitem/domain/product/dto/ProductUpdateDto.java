package com.daitem.domain.product.dto;

import com.daitem.domain.common.converter.JsonListConverter;
import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.product.enumset.Categories;
import com.daitem.domain.product.enumset.Colors;
import com.daitem.domain.product.enumset.DisplayType;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class ProductUpdateDto {

    private String productName;
    private String description;
    private Categories categories;
    private List<ProductDetailUpdateDto> productDetails;
//    private int price;
//    private int stock;
//    private YN isSalesTerms;
//    private LocalDate saleFromDate;
//    private LocalDate saleToDate;
//
//    private DisplayType displayType;
//    private YN isDeleted;
//    private Colors color;

}
