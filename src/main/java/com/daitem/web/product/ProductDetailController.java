package com.daitem.web.product;

import com.daitem.domain.common.SearchDto;
import com.daitem.domain.product.ProductDetailService;
import com.daitem.domain.product.ProductService;
import com.daitem.domain.product.dto.*;
import com.daitem.domain.product.entity.ProductDetail;
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
@Tag(name = "상품 상세", description = "상품 상세!!만 조정할 컨트롤러")
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    /**
     * 상품 상세 조회
     * @param id 상품 아이디
     * @return 상품 상세 정보 리스트
     */
    @GetMapping("/api/v1/product_detail/{id}")
    public CommonResponse<List<ProductDetailDtoOriginal>> getProductDetail(@PathVariable Long id) {
        List<ProductDetailDtoOriginal> productDetails = productDetailService.getProductDetail(id);
        return CommonResponse.ok(productDetails);
    }

    /**
     * 상품 상세 페이지에서 삭제 : 옵션들 삭제하는 경우
     * @param id 상품 상세 아이디
     * */
    @DeleteMapping("/api/v1/product_detail/{id}")
    @ResponseBody
    public CommonResponse<?> DeleteProductDetail(@PathVariable Long id) {
        try{
            productDetailService.deleteProductDetail(id);
            return new CommonResponse<>(200, "상품 옵션 삭제 완료!!.");
        }catch (Exception e) {
            return new CommonResponse<>(500, e.getMessage());
        }
    }


}
