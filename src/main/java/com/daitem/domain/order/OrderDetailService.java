package com.daitem.domain.order;

import com.daitem.domain.common.SearchDto;
import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.order.dto.*;
import com.daitem.domain.order.entity.Order;
import com.daitem.domain.order.entity.OrderDetail;
import com.daitem.domain.order.enumset.OrderStatus;
import com.daitem.domain.product.ProductDetailRepository;
import com.daitem.domain.product.ProductRepository;
import com.daitem.domain.product.entity.Product;
import com.daitem.domain.product.entity.ProductDetail;
import com.daitem.domain.product.enumset.DisplayType;
import com.daitem.domain.user.UserRepository;
import com.daitem.domain.user.entity.User;
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
public class OrderDetailService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    public List<OrderDetailListDto> getOrderDetails(User user, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 주문입니다."));

        return orderDetailRepository.findByOrderIdAndUserId(order.getOrderId(), user.getUserId());
    }
}
