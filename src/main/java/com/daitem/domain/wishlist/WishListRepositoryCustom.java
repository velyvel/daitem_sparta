package com.daitem.domain.wishlist;

import com.daitem.domain.wishlist.dto.WishListListDto;

import java.util.List;

public interface WishListRepositoryCustom {
    List<WishListListDto> findByUserId(Long userId);
}
