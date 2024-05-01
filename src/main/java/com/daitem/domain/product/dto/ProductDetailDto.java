package com.daitem.domain.product.dto;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.product.enumset.Categories;
import com.daitem.domain.product.enumset.Colors;
import com.daitem.domain.product.enumset.DisplayType;
import com.daitem.domain.product.enumset.Size;
import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.logging.log4j2.ColorConverter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class ProductDetailDto {

        private Long productId;
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

