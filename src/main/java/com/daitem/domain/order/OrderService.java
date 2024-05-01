package com.daitem.domain.order;

import com.daitem.domain.common.SearchDto;
import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.order.dto.OrderListDto;
import com.daitem.domain.order.dto.OrderProductDetailDto;
import com.daitem.domain.order.dto.OrderProductDto;
import com.daitem.domain.order.dto.OrderSaveDto;
import com.daitem.domain.order.entity.Order;
import com.daitem.domain.order.entity.OrderDetail;
import com.daitem.domain.order.enumset.OrderStatus;
import com.daitem.domain.product.ProductDetailRepository;
import com.daitem.domain.product.ProductDetailService;
import com.daitem.domain.product.ProductRepository;
import com.daitem.domain.product.ProductService;
import com.daitem.domain.product.entity.Product;
import com.daitem.domain.product.entity.ProductDetail;
import com.daitem.domain.product.enumset.DisplayType;
import com.daitem.domain.user.UserRepository;
import com.daitem.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

//    public void orderAdd(OrderSaveDto dto) {
//        User user = userRepository.findById(dto.getUserId()).orElseThrow();
//        if(dto.getTotalAmount() == 0) {
//            throw new RuntimeException("상품은 한 개 이상 주문하시길 바랍니다");
//        }
//
//        // 주문 정보 저장
//        Order order = Order.builder()
//                .user(user)
//                .orderDate(LocalDateTime.now())
//                .orderStatus(OrderStatus.ORDERED)
//                .paymentMethod(dto.getPaymentMethod())
//                .shippingAddress1(dto.getShippingAddress1())
//                .shippingAddress2(dto.getShippingAddress2())
//                .shippingAddress3(dto.getShippingAddress3())
//                .totalAmount(dto.getTotalAmount())
//                .isDelivered(YN.N)
//                .build();
//        orderRepository.save(order);
//
//        for (OrderProductDto productDto : dto.getProducts()) {
//            Product product = productRepository.findById(productDto.getProductId()).orElseThrow();
//            for (OrderProductDetailDto productDetailDto : productDto.getProductDetails()) {
//                // 각 상품의 수량을 주문 상세에 추가
//                ProductDetail productDetail = productDetailRepository.findById(productDetailDto.getProductDetailId()).orElseThrow();
//                // 수량 validation
//                amountValidation(productDetail, productDetailDto);
//                OrderDetail orderDetail = OrderDetail.builder()
//                        .order(order)
//                        .product(product) // 상품을 주문 상세에 추가
//                        .productDetailId(productDetail.getProductDetailId())
//                        .quantity(productDetailDto.getAmount())// 상품의 수량을 추가
//                        .productPrice(productDetail.getPrice())
//                        .build();
//                orderDetailRepository.save(orderDetail);
//                // 수량 빼주기
//                productDetail.setStock(productDetail.getStock() - productDetailDto.getAmount());
//                productDetailRepository.save(productDetail);
//            }
//        }
//    }


    @Transactional
    public void orderAdd(OrderSaveDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        if (dto.getTotalAmount() == 0) {
            throw new RuntimeException("상품은 한 개 이상 주문하셔야 합니다");
        }

        Order order = createOrderFromDto(dto, user);
        orderRepository.save(order);

        for (OrderProductDto productDto : dto.getProducts()) {
            Product product = getProductFromDto(productDto);
            for (OrderProductDetailDto productDetailDto : productDto.getProductDetails()) {
                ProductDetail productDetail = getProductDetailFromDto(productDetailDto);
                validateAndProcessOrderDetail(order, product, productDetail, productDetailDto.getAmount());
            }
        }
    }

    private Order createOrderFromDto(OrderSaveDto dto, User user) {
        return Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDERED)
                .paymentMethod(dto.getPaymentMethod())
                .shippingAddress1(dto.getShippingAddress1())
                .shippingAddress2(dto.getShippingAddress2())
                .shippingAddress3(dto.getShippingAddress3())
                .totalAmount(dto.getTotalAmount())
                .isDelivered(YN.N)
                .build();
    }

    private Product getProductFromDto(OrderProductDto productDto) {
        return productRepository.findById(productDto.getProductId())
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));
    }

    private ProductDetail getProductDetailFromDto(OrderProductDetailDto productDetailDto) {
        return productDetailRepository.findById(productDetailDto.getProductDetailId())
                .orElseThrow(() -> new RuntimeException("상품 상세 정보를 찾을 수 없습니다"));
    }

    private void validateAndProcessOrderDetail(Order order, Product product, ProductDetail productDetail, int amount) {
        if (amount <= 0) {
            throw new RuntimeException("상품 수량은 0보다 커야 합니다");
        }

        if (productDetail.getStock() < amount) {
            throw new RuntimeException("상품의 재고가 부족합니다. 수량을 조절해 주세요");
        }

        // 품절
        if(productDetail.getDisplayType() == DisplayType.SOLDOUT) {
            throw new RuntimeException("품절된 상품입니다. 다음에 다시 주문해 주세요.");
        }

        OrderDetail orderDetail = createOrderDetail(order, product, productDetail, amount);
        orderDetailRepository.save(orderDetail);

        productDetail.setStock(productDetail.getStock() - amount);
        productDetailRepository.save(productDetail);
    }

    private OrderDetail createOrderDetail(Order order, Product product, ProductDetail productDetail, int amount) {
        return OrderDetail.builder()
                .order(order)
                .product(product)
                .productDetailId(productDetail.getProductDetailId())
                .quantity(amount)
                .productPrice(productDetail.getPrice())
                .build();
    }


    @Transactional
    public Page<OrderListDto> orderList(SearchDto condition, Pageable pageable, User user) {
        Long userId = user.getUserId();
        return orderRepository.orderList(condition,pageable,userId);
    }

    @Transactional
    public void orderCanceled(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다"));

        if (order.getOrderDate().plusDays(1L).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("주문 취소 기간이 지났습니다.");
        }
        // 주문 상세 조회하기
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);

        //주문 상세에 있는 재고 -> 돌려놓기
        // 내부매서드
        productService.putStocksBack(orderDetails);

        // 주문 상세를 삭제
        orderDetailRepository.deleteByOrder(order);

        // 주문 삭제
        orderRepository.deleteById(id);
    }
}
