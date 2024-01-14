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
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Product> products = findProductsBy(productNumbers);

        // create order
        Order order = Order.create(products, registeredDateTime);

        return OrderResponse.of(orderRepository.save(order));
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        // 중복이 제거된 products 조회됨
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        // product를 빨리 찾을 수 있는 Map을 생성
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, product -> product));

        // 중복된 음료도 주문이 가능하도록 처리
        return productNumbers.stream()
                .map(productMap::get)
                .toList();
    }
}