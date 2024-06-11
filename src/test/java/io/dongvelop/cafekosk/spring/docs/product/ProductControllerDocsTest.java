package io.dongvelop.cafekosk.spring.docs.product;

import io.dongvelop.cafekosk.spring.api.controller.product.ProductController;
import io.dongvelop.cafekosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import io.dongvelop.cafekosk.spring.api.service.product.ProductService;
import io.dongvelop.cafekosk.spring.docs.RestDocsSupport;
import io.dongvelop.cafekosk.spring.domain.product.ProductSellingStatus;
import io.dongvelop.cafekosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 이동엽(Lee Dongyeop)
 * @date 2024. 06. 11
 * @description
 */
public class ProductControllerDocsTest extends RestDocsSupport {

    private final ProductService productService = mock(ProductService.class);

    @Override
    protected Object initController() {
        return new ProductController(productService);
    }

    @Test
    @DisplayName("신규 상품을 등록하는 API")
    void createProduct() throws Exception {

        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        // expected
        mockMvc.perform(post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
//                .andDo(document("product-create", ))

        ;
    }
}
