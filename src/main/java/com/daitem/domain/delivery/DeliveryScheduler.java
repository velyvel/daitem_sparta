package com.daitem.domain.delivery;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.order.OrderDetailRepository;
import com.daitem.domain.order.OrderRepository;
import com.daitem.domain.order.entity.Order;
import com.daitem.domain.order.entity.OrderDetail;
import com.daitem.domain.order.enumset.OrderStatus;
import com.daitem.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DeliveryScheduler {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;

    // 매일 오전 12:01
    @Scheduled(cron = "1 0 0 * * ?")
    // 테스트 : 1분마다
    //@Scheduled(cron = "1 * * * * ?")
    public void myScheduledMethod() {

        //CANCEL 이면 ? 하루 뒤 재고 돌려놓기
        List<Order> canceledOrders = orderRepository.findByOrderStatus(OrderStatus.CANCELLED);
        for(Order order : canceledOrders) {
            LocalDate deliveredDate = order.getDeliveryDate().toLocalDate();
            if(deliveredDate.plusDays(1L).isEqual(LocalDate.now())
                    && !(order.getOrderStatus().equals(OrderStatus.CANCELLED))){
                List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
                // 재고 돌려놓기 재활용
                productService.putStocksBack(orderDetails);
            }
        }

        //DELIVERED -> DETERMINE 으로 바꾸기
        List<Order> determineOrders = orderRepository.findByOrderStatus(OrderStatus.DELIVERING);
        for (Order order : determineOrders) {
            LocalDate deliveredDate = order.getDeliveryDate().toLocalDate();

            // 주문 맞으면 ?
            if (deliveredDate.plusDays(1L).isEqual(LocalDate.now())
                    && !(order.getOrderStatus().equals(OrderStatus.CANCELLED))) {
                order.setOrderStatus(OrderStatus.DETERMINE);
                orderRepository.save(order);
            }
        }

        //DELIVERING -> DELIVERED 로 바꾸기
        List<Order> orderedDelivering = orderRepository.findByOrderStatus(OrderStatus.DELIVERING);

        for (Order order : orderedDelivering) {
            LocalDate orderDate = order.getOrderDate().toLocalDate();
            if (orderDate.plusDays(2L).isEqual(LocalDate.now())) {
                order.setOrderStatus(OrderStatus.DELIVERED);
                order.setIsDelivered(YN.Y);
                order.setDeliveryDate(LocalDateTime.now());
                orderRepository.save(order);
            }
        }

        // ORDERED -> DELIVERING
        List<Order> orderedOrders = orderRepository.findByOrderStatus(OrderStatus.ORDERED);
        for (Order order : orderedOrders) {
            LocalDate orderDate = order.getOrderDate().toLocalDate();

            if (orderDate.plusDays(1L).isEqual(LocalDate.now())) {
                order.setOrderStatus(OrderStatus.DELIVERING);
                orderRepository.save(order);
            }
        }

        System.out.println("============ 배송상태 변경 완료 ==========");
    }
}
