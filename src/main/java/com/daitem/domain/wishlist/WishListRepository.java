package com.daitem.domain.wishlist;

import com.daitem.domain.product.entity.ProductDetail;
import com.daitem.domain.user.entity.User;
import com.daitem.domain.wishlist.dto.WishListListDto;
import com.daitem.domain.wishlist.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface WishListRepository extends
        JpaRepository<WishList, Long>,
        QuerydslPredicateExecutor<WishList>,
        WishListRepositoryCustom{

    WishList findByProductDetail(ProductDetail productDetail);

    WishList findByUserAndProductDetail(User user, ProductDetail productDetail);

    List<WishList> findByUser(User user);
}
