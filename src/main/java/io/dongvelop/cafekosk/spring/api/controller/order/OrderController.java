package io.dongvelop.cafekosk.spring.api.controller.order;

import io.dongvelop.cafekosk.spring.api.ApiResponse;
import io.dongvelop.cafekosk.spring.api.controller.order.request.OrderCreateRequest;
import io.dongvelop.cafekosk.spring.api.service.order.OrderService;
import io.dongvelop.cafekosk.spring.api.service.order.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderCreateRequest request) {
        LocalDateTime registeredDateTime = LocalDateTime.now();
        return ApiResponse.ok(orderService.createOrder(request.toServiceRequest(), registeredDateTime));
    }
}
