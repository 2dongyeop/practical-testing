package io.dongvelop.cafekosk.spring.api.service.product;

import io.dongvelop.cafekosk.spring.api.service.product.response.ProductResponse;
import io.dongvelop.cafekosk.spring.domain.product.Product;
import io.dongvelop.cafekosk.spring.domain.product.ProductRepository;
import io.dongvelop.cafekosk.spring.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .toList();
    }
}
