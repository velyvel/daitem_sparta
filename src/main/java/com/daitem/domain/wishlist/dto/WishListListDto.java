package com.daitem.domain.wishlist.dto;

import com.daitem.domain.product.enumset.Categories;
import com.daitem.domain.product.enumset.Colors;
import com.daitem.domain.product.enumset.DisplayType;
import com.daitem.domain.product.enumset.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishListListDto {
    private Long productId;
    private Long userId;
    private String productName;
    private String description;
    private Categories productCategory;
    private Long productDetailId;
    private Colors color;
    private Size size;
    private int price;
    private DisplayType displayType;
    private int count;

}
