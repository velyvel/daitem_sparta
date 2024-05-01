package com.daitem.domain.product.dto;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.product.enumset.Colors;
import com.daitem.domain.product.enumset.DisplayType;
import com.daitem.domain.product.enumset.Size;
import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDetailDeleteDto {

        private Long productDetailId;
        @Convert(converter = Colors.ColorsConverter.class)
        private Colors color;
        private Size size;
        private int stock;

//        private YN isSaleTerms;
//        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//        private LocalDateTime saleFromDate;
//        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//        private LocalDateTime saleToDate;

        private int price;
        private DisplayType displayType;
        private YN isDeleted;
}

