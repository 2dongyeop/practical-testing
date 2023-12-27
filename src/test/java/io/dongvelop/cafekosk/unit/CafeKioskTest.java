package io.dongvelop.cafekosk.unit;

import io.dongvelop.cafekosk.unit.beverage.Americano;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add() {

        final CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 이름 : " + cafeKiosk.getBeverages().get(0).getName());
    }
}