package com.daitem.domain.wishlist;

import com.daitem.domain.product.ProductDetailRepository;
import com.daitem.domain.product.ProductRepository;
import com.daitem.domain.product.entity.Product;
import com.daitem.domain.product.entity.ProductDetail;
import com.daitem.domain.user.UserRepository;
import com.daitem.domain.user.entity.User;
import com.daitem.domain.wishlist.dto.WishListListDto;
import com.daitem.domain.wishlist.dto.WishListSaveDto;
import com.daitem.domain.wishlist.entity.WishList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final UserRepository userRepository;

    public void addWishList(WishListSaveDto dto, User user) {

        Product product = productRepository.findById(dto.getProductId()).orElse(null);
        ProductDetail productDetail = productDetailRepository.findById(dto.getProductDetailId()).orElse(null);

        if (user == null) {
            // 레디스 세션에 위시리스트 저장하기
        }

        if (productDetail != null) {
            // 해당 상품 상세 정보가 이미 위시리스트에 있는지 체크
            WishList existingWishListItem = wishListRepository.findByUserAndProductDetail(user, productDetail);

            if (existingWishListItem != null) {
                // 이미 위시리스트에 해당 상품 상세 정보가 있는 경우: 수량 증가
                existingWishListItem.setCount(existingWishListItem.getCount() + 1);
                wishListRepository.save(existingWishListItem);
            } else {
                // 위시리스트에 해당 상품 상세 정보가 없는 경우: 신규 추가
                WishList wishList = WishList.builder()
                        .product(product)
                        .user(user)
                        .productDetail(productDetail)
                        .count(1)
                        .build();
                wishListRepository.save(wishList);
            }
        }
    }

    public List<WishListListDto> listWishList(User user) {

        Long userId = user.getUserId();
        return wishListRepository.findByUserId(userId);
    }

    public void updateWishListPlus(Long id) {
        ProductDetail productDetail = productDetailRepository.findById(id).orElse(null);
        WishList wishList = wishListRepository.findByProductDetail(productDetail);

        wishList.setCount(wishList.getCount() + 1);
        wishListRepository.save(wishList);
    }

    public void updateWishListMinus(Long id) {
        ProductDetail productDetail = productDetailRepository.findById(id).orElse(null);
        WishList wishList = wishListRepository.findByProductDetail(productDetail);
        wishList.setCount(wishList.getCount() - 1);
        wishListRepository.save(wishList);
    }

    public void deleteWishList(Long id) {
        ProductDetail productDetail = productDetailRepository.findById(id).orElse(null);
        WishList wishList = wishListRepository.findByProductDetail(productDetail);
        wishListRepository.delete(wishList);
    }

    public void deleteWishListAll(User user) {
        List<WishList> wishListList = wishListRepository.findByUser(user);
        for(WishList wishList : wishListList) {
            wishListRepository.delete(wishList);
        }
    }
}
