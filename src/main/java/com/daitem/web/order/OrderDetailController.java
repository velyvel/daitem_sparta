package com.daitem.web.order;

import com.daitem.domain.common.SearchDto;
import com.daitem.domain.order.OrderDetailService;
import com.daitem.domain.order.OrderService;
import com.daitem.domain.order.dto.OrderDetailListDto;
import com.daitem.domain.order.dto.OrderListDto;
import com.daitem.domain.order.dto.OrderSaveDto;
import com.daitem.domain.user.entity.User;
import com.daitem.web.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@RestController
@RequiredArgsConstructor
@Tag(name = "주문 상세", description = "주문 상세 API")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;


    /**
     * 주문 상세 api
     * @Param id 주문 id
     * */
    @GetMapping("/api/v1/order_detail/{id}")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    public CommonResponse<?> orderList(@AuthenticationPrincipal User user, @PathVariable Long id) {

        List<OrderDetailListDto> orderDetails = orderDetailService.getOrderDetails(user, id);
        return CommonResponse.ok(orderDetails);
    }

    // 추가할 것 : 주문 상세에 대한 취소

}
