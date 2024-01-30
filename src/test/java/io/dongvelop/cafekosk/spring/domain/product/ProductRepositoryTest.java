package io.dongvelop.cafekosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static io.dongvelop.cafekosk.spring.domain.product.ProductSellingStatus.*;
import static io.dongvelop.cafekosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

//@SpringBootTest
@DataJpaTest // JPA 관련 Bean들만 띄워서 스프링부트를 실행
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    void findAllBySellingStatusIn() throws Exception {

        // given
        Product product1 = createProduct("001", "아메리카노", HANDMADE, SELLING, 4000);

        Product product3 = createProduct("002", "카페라떼", HANDMADE, HOLD, 4500);

        Product product2 = createProduct("003", "팥빙수", HANDMADE, STOP_SELLING, 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @Test
    @DisplayName("상품번호 리스트로 상품들을 조회한다.")
    void findAllByProductNumberIn() throws Exception {

        // given
        Product product1 = createProduct("001", "아메리카노", HANDMADE, SELLING, 4000);

        Product product3 = createProduct("002", "카페라떼", HANDMADE, HOLD, 4500);

        Product product2 = createProduct("003", "팥빙수", HANDMADE, STOP_SELLING, 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다.")
    void findLatestProductNumber() throws Exception {

        // given
        Product product1 = createProduct("001", "아메리카노", HANDMADE, SELLING, 4000);

        Product product2 = createProduct("002", "카페라떼", HANDMADE, HOLD, 4500);

        String targetProductNumber = "003";
        Product product3 = createProduct(targetProductNumber, "팥빙수", HANDMADE, STOP_SELLING, 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        String latestProductNumber = productRepository.findLatestProductNumber();

        // then
        assertThat(latestProductNumber).isEqualTo(targetProductNumber);
    }

    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때, 상품이 하나도 없는 경우에는 null을 반환한다.")
    void findLatestProductNumberWhenProductIsEmpty() throws Exception {

        // given

        // when
        String latestProductNumber = productRepository.findLatestProductNumber();

        // then
        assertThat(latestProductNumber).isNull();
    }

    private Product createProduct(String productNumber, String name, ProductType productType, ProductSellingStatus productSellingStatus, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(productType)
                .sellingStatus(productSellingStatus)
                .name(name)
                .price(price)
                .build();
    }


}