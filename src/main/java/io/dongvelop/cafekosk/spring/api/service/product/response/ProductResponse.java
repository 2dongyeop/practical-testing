package io.dongvelop.cafekosk.spring.api.service.product.response;

import io.dongvelop.cafekosk.spring.domain.product.Product;
import io.dongvelop.cafekosk.spring.domain.product.ProductSellingStatus;
import io.dongvelop.cafekosk.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;
    private final String productNumber;
    private final ProductType type;
    private final ProductSellingStatus sellingStatus;
    private final String name;
    private final int price;

    @Builder
    private ProductResponse(Long id, String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productNumber(product.getProductNumber())
                .type(product.getType())
                .sellingStatus(product.getSellingStatus())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
