package io.dongvelop.cafekosk.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {

    @Test
    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
    void isQuantityLessThan() throws Exception {

        // given
        int quantity = 2;
        Stock stock = Stock.create("001", 1);

        // when
        boolean result = stock.isQuantityLessThan(quantity);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
    void deductQuantity() throws Exception {

        // given
        int quantity = 2;
        Stock stock = Stock.create("001", 2);

        // when
        stock.deductQuantity(quantity);

        // then
        assertThat(stock.getQuantity()).isZero();
    }

    @Test
    @DisplayName("재고보다 많은 수량으로 차감 시도하는 경우 예외가 발생한다.")
    void deductQuantity2() throws Exception {

        // given
        int quantity = 2;
        Stock stock = Stock.create("001", 1);

        // expected
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("다이나믹 테스트를 이용한 재고 차감 시나리오 작성")
    @TestFactory
    Collection<DynamicTest> stockDeductionDynamicTest() {

        Stock stock = Stock.create("001", 1);

        return List.of(
                DynamicTest.dynamicTest("재고를 주어진 개수만큼 차감할 수 있다.", () -> {

                    // given
                    int quantity = 1;

                    // when
                    stock.deductQuantity(quantity);

                    // then
                    assertThat(stock.getQuantity()).isZero();
                }),
                DynamicTest.dynamicTest("재고보다 많은 수의 수량으로 차감을 시도하는 경우 예외가 발생한다.", () ->{

                    // given
                    int quantity = 1;

                    // when
                    // then
                    assertThatThrownBy(() -> stock.deductQuantity(quantity))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("차감할 재고 수량이 없습니다.");
                })
        );
    }
}