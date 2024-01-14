package io.dongvelop.cafekosk.spring.api.service.order;

import io.dongvelop.cafekosk.spring.api.controller.order.request.OrderCreateRequest;
import io.dongvelop.cafekosk.spring.api.service.order.response.OrderResponse;
import io.dongvelop.cafekosk.spring.domain.order.Order;
import io.dongvelop.cafekosk.spring.domain.order.OrderRepository;
import io.dongvelop.cafekosk.spring.domain.product.Product;
import io.dongvelop.cafekosk.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {

        List<String> productNumbers = request.getProductNumbers();

        // get product
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        // create order
        Order order = Order.create(products, registeredDateTime);

        return OrderResponse.of(orderRepository.save(order));
    }
}
