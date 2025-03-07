package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    @Test
    public void testCarGettersAndSetters() {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        assertEquals("123", car.getCarId());
        assertEquals("Toyota", car.getCarName());
        assertEquals("Red", car.getCarColor());
        assertEquals(5, car.getCarQuantity());
    }
}
