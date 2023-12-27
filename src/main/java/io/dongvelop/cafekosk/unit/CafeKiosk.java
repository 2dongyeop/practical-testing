package io.dongvelop.cafekosk.unit;

import io.dongvelop.cafekosk.unit.beverage.Beverage;
import io.dongvelop.cafekosk.unit.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(final Beverage beverage) {
        beverages.add(beverage);
    }

    public void remove(final Beverage beverage) {
        beverages.remove(beverage);
    }

    public void clear() {
        beverages.clear();
    }

    public int calculateTotalPrice() {
        return beverages.stream()
                .mapToInt(Beverage::getPrice)
                .sum();
    }

    public Order createOrder() {
        return new Order(LocalDateTime.now(), beverages);
    }
}
