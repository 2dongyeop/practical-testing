package io.dongvelop.cafekosk.spring.domain.orderproduct;

import io.dongvelop.cafekosk.spring.domain.BaseEntity;
import io.dongvelop.cafekosk.spring.domain.order.Order;
import io.dongvelop.cafekosk.spring.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public OrderProduct( Order order, Product product) {
        this.order = order;
        this.product = product;
    }
}
