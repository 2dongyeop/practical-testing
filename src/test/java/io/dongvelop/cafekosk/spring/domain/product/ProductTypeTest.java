package io.dongvelop.cafekosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    void containsStockType() throws Exception {

        // given
        ProductType givenType = ProductType.HANDMADE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    void containsStockType2() throws Exception {

        // given
        ProductType givenType = ProductType.BAKERY;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("csv 파일로 파라미터를 주입해 테스트를 진행한다.")
    @CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY,true"})
    @ParameterizedTest
    void containsStockType4(ProductType productType, boolean expected) {

        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("메서드에서 정의한 파라미터를 주입해 테스트를 진행한다.")
    @MethodSource("provideProductTypesForCheckingStockType")
    @ParameterizedTest
    void containsStockType5(ProductType productType, boolean expected) {

        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideProductTypesForCheckingStockType() {
        return Stream.of(
                Arguments.of(ProductType.HANDMADE, false),
                Arguments.of(ProductType.BOTTLE, true),
                Arguments.of(ProductType.BAKERY, true)
        );
    }
}