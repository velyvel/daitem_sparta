package com.daitem.web.order;

import com.daitem.domain.common.SearchDto;
import com.daitem.domain.order.OrderService;
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

import static org.springframework.util.StringUtils.hasText;

@RestController
@RequiredArgsConstructor
@Tag(name = "주문", description = "주문 API")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 api,
     * totalAmount : 주문하는 상품의 총 개수, 0개 이하 입력 시 RuntimeException : 상품 한 개 이상 주문하세요
     * */
    @PostMapping("/api/v1/order")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    public CommonResponse<?> productAdd(@RequestBody OrderSaveDto dto,
                                        @AuthenticationPrincipal User user) {
        try {
            if(user == null) {
                throw new RuntimeException("회원가입을 먼저 해 주세요");
            }
            dto.setUserId(user.getUserId());
            orderService.orderAdd(dto);
            return CommonResponse.ok("주문에 성공했습니다!");
        } catch (Exception e) {
            return new CommonResponse<>(500, e.getMessage());
        }
    }

    /**
     * 주문 리스트 api
     * */
    @GetMapping("/api/v1/order")
    @Operation(parameters = {
            @Parameter(name = "category", description = "카테고리"),
            @Parameter(name = "field", description = "검색어"),
            @Parameter(name = "page", description = "페이지 번호"),
            @Parameter(name = "size", description = "한 페이지의 보여지는 목록 수")
    })
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    public CommonResponse<?> orderList(@Parameter(hidden = true) SearchDto condition,
                                       @AuthenticationPrincipal User user) {

        if(user == null) {
            throw new RuntimeException("잘못된 접근입니다. 다시 로그인 해 주세요");
        }
        int setPage = 1;
        int setSize = 10;

        String page = condition.getPage();
        String size = condition.getSize();

        if (hasText(page) && page.matches("^\\d+$")) setPage = Math.max(Integer.parseInt(page), 1);
        if (hasText(size) && size.matches("^\\d+$")) setSize = Math.max(Integer.parseInt(size), 1);

        Pageable pageable = PageRequest.of(setPage - 1, setSize, Sort.by("id").descending());
        Page<OrderListDto> orderList = orderService.orderList(condition, pageable, user);

        return CommonResponse.ok(orderList);

    }

    /***
     * 주문 취소 api
     * @Param id 주문 id
     * */
    @DeleteMapping("/api/v1/order/{id}")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    public CommonResponse<?> orderCanceled(@AuthenticationPrincipal User user,
                                       @PathVariable Long id) {

        if(user == null) {
            throw new RuntimeException("권한이 없습니다.");
        }

        try {
            orderService.orderCanceled(id);
            return CommonResponse.ok("주문 취소 성공!!");
        } catch (Exception e) {
            return new CommonResponse<>(500, e.getMessage());
        }
    }

}
