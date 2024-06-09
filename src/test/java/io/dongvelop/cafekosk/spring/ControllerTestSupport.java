package io.dongvelop.cafekosk.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dongvelop.cafekosk.spring.api.controller.order.OrderController;
import io.dongvelop.cafekosk.spring.api.controller.product.ProductController;
import io.dongvelop.cafekosk.spring.api.service.order.OrderService;
import io.dongvelop.cafekosk.spring.api.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author 이동엽(Lee Dongyeop)
 * @date 2024. 06. 09
 * @description
 */
@WebMvcTest(controllers = {
        OrderController.class,
        ProductController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected ProductService productService;
}
