package com.daitem.domain.delivery;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.order.OrderDetailRepository;
import com.daitem.domain.order.OrderRepository;
import com.daitem.domain.order.entity.Order;
import com.daitem.domain.order.entity.OrderDetail;
import com.daitem.domain.order.enumset.OrderStatus;
import com.daitem.domain.product.ProductService;
import com.daitem.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    @Transactional
    public void CanceledDelivery(User user, Long id) {
        if(user == null) {
          throw new RuntimeException("회원정보가 올바르지 않습니다.");
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("올바르지 않은 주문 정보입니다"));
        // 반품조건 만족하는 메서드
        canDelivered(order);
    }

    private void canDelivered(Order order) {

        //배송 완료, 배송 상태값 delivered, 2024.04.26 00 : 01 배송 완료
        //하루의 개념 ?
        if(order.getIsDelivered().equals(YN.Y)
                && order.getOrderStatus().equals(OrderStatus.DELIVERED)
                && order.getDeliveryDate().plusDays(1L).isBefore(LocalDateTime.now())){
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("반품기간이 지났습니다.");
        }
    }
}
