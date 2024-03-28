package io.dongvelop.cafekosk.spring.api.service.order;

import io.dongvelop.cafekosk.spring.client.mail.MailSendClient;
import io.dongvelop.cafekosk.spring.domain.mail.MailSendHistory;
import io.dongvelop.cafekosk.spring.domain.mail.MailSendHistoryRepository;
import io.dongvelop.cafekosk.spring.domain.order.Order;
import io.dongvelop.cafekosk.spring.domain.order.OrderRepository;
import io.dongvelop.cafekosk.spring.domain.order.OrderStatus;
import io.dongvelop.cafekosk.spring.domain.orderproduct.OrderProductRepository;
import io.dongvelop.cafekosk.spring.domain.product.Product;
import io.dongvelop.cafekosk.spring.domain.product.ProductRepository;
import io.dongvelop.cafekosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static io.dongvelop.cafekosk.spring.domain.product.ProductSellingStatus.SELLING;
import static io.dongvelop.cafekosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author 이동엽(Lee Dongyeop)
 * @date 2024. 03. 28
 * @description
 */
@SpringBootTest
class OrderStatisticsServiceTest {

    @Autowired
    private OrderStatisticsService orderStatisticsService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @MockBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    void sendOrderStatisticsMail() {

        // given
        LocalDateTime now = LocalDateTime.of(2024, 3, 28, 0, 0);

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        /* 경계값 테스트 */
        createPaymentCompletedOrder(products, now.minusMinutes(1));
        createPaymentCompletedOrder(products, now);
        createPaymentCompletedOrder(products, now.plusDays(1).minusMinutes(1));
        createPaymentCompletedOrder(products, now.plusDays(1));

        /**
         * stubbing : 목킹 처리
         *
         * when -> Mokcito
         * given -> BDDMockito
         * 동작은 동일.
         */
        when(mailSendClient.sendEmail(any(), any(), any(), any())).thenReturn(true);
//        given(mailSendClient.sendEmail(any(), any(), any(), any())).willReturn(true);

        // when
        Boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2024, 3, 28), "test@test.com");

        // then
        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1);
    }

    private Order createPaymentCompletedOrder(List<Product> products, LocalDateTime now) {
        return orderRepository.save(Order.builder()
                .products(products)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(now)
                .build());
    }

    private Product createProduct(ProductType type, String productNumber, int price) {

        return Product.builder()
                .type(type)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }
}