package io.dongvelop.cafekosk.unit;

import io.dongvelop.cafekosk.unit.beverage.Americano;
import io.dongvelop.cafekosk.unit.beverage.Latte;
import io.dongvelop.cafekosk.unit.order.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add_manual_test() {

        final CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 이름 : " + cafeKiosk.getBeverages().get(0).getName());
    }

//    @DisplayName("음료 1개 추가 테스트") // bad case
    @DisplayName("음료 1개를 추가하면 주문 목록에 담긴다") // good case
    @Test
    void add() {

        final CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void addSeveralBeverages() {

        final CafeKiosk cafeKiosk = new CafeKiosk();
        final Americano americano = new Americano();

        cafeKiosk.add(americano, 2);

        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @Test
    void addZeroBeverages() {

        final CafeKiosk cafeKiosk = new CafeKiosk();
        final Americano americano = new Americano();

        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");

        assertThrows(IllegalArgumentException.class, () -> cafeKiosk.add(americano, 0));
    }

    @Test
    void remove() {

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void calculateTotalPrice() {

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        int totalPrice = cafeKiosk.calculateTotalPrice();
        assertThat(totalPrice).isEqualTo(8500);
    }

    @DisplayName("테스트하기 어려운 케이스 - 성공 여부가 현재 시간에 의존")
    @Test
    void createOrder() {

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

//        Order order = cafeKiosk.createOrder();
//
//        assertThat(order.getBeverages()).hasSize(1);
//        assertThat(order.getBeverages().get(0)).isEqualTo(americano);
    }

    @DisplayName("테스트하기 어려운 영역을 분리하기")
    @Test
    void createOrderWithCurrentTime() {

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder(LocalDateTime.of(2024, 1, 6, 21, 36));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0)).isEqualTo(americano);
    }

    /**
     * DisplayName 작성시 도메인 용어를 사용하자.
     *  - 특정 시간              -> bad
     *  - 영업 종료 시간         -> good
     * 메서드 자체의 관점보다 도메인 정책 관점으로
     * 테스트의 현상을 중점으로 기술하지 말 것
     *  - "~~~ 하면 실패한다."         -> bad
     *  - "~~~ 주문을 생성할 수 없다." -> good
     */
    @Test
    @DisplayName("영업 종료 시간 이후에는 주문을 생성할 수 없다.")
    void createOrderWithOutsideOpenTime() {

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2024, 1, 6, 22, 36)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }
}