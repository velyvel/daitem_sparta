package com.daitem.domain.product.dto;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.product.enumset.Categories;
import com.daitem.domain.product.enumset.Colors;

import com.daitem.domain.product.enumset.DisplayType;
import com.daitem.domain.product.enumset.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class ProductSaveDto {
    /**
     * 상품 페이지에 등록, 옵션들은 row 로 productDetailDto 저장
     * */
    private Long productId;
    private String productName;
    private Categories categories;
    private String description;

    private List<ProductDetailDto> productDetails;


    @Getter
    @Setter
    public static class ProductDetailDto {
        private Colors color;
        private Size size;
        private int stock;
        private int productDetailId;

        private YN isSaleTerms;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime salesFromDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime salesToDate;

        private int price;
        private DisplayType displayType;
        private YN isDeleted;
    }
}
