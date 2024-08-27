package bicyclemodel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BicycleTest {
    @Test
    void startsAtRest() {
        Bicycle bike = new Bicycle();
        assertEquals(0, bike.getSpeed());
        assertEquals(0, bike.getDirection());
    }

    @Test
    void accelerate() {
        Bicycle bike = new Bicycle();
        bike.pedalHarder();
        assertEquals(1.8, bike.getSpeed(), 0.00001);
        bike.pedalHarder();
        assertEquals(3.42, bike.getSpeed(), 0.00001);
    }

    @Test
    void decelerate() {
        Bicycle bike = new Bicycle();
        bike.pedalHarder();
        bike.pedalHarder();
        bike.brake();
        assertEquals(0.378, bike.getSpeed(), 0.00001);
        bike.brake();
        assertEquals(0, bike.getSpeed(), 0.00001);
        bike.brake();
        assertEquals(0, bike.getSpeed(), 0.00001);
    }

    @Test
    void changeDirection() {
        Bicycle bike = new Bicycle();
        bike.setDirection(70);
        assertEquals(70, bike.getDirection());
        bike.setDirection(1000000);
        assertEquals(90, bike.getDirection());
        bike.setDirection(-1000000);
        assertEquals(-90, bike.getDirection());
    }

    @Test
    void customBicycle() {
        Bicycle bike = new Bicycle(0.5, 6, 2);
        bike.pedalHarder();
        assertEquals(3, bike.getSpeed());
        bike.brake();
        assertEquals(0.5, bike.getSpeed());
    }
}
