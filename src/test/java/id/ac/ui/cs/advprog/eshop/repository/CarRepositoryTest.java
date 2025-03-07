package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateCarWithNullId() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(3);

        Car createdCar = carRepository.create(car);

        assertNotNull(createdCar.getCarId());
        assertEquals("Toyota", createdCar.getCarName());
        assertEquals("Red", createdCar.getCarColor());
        assertEquals(3, createdCar.getCarQuantity());
    }

    @Test
    void testCreateCarWithExistingId() {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Honda");
        car.setCarColor("Blue");
        car.setCarQuantity(5);

        Car createdCar = carRepository.create(car);

        assertEquals("123", createdCar.getCarId());
        assertEquals("Honda", createdCar.getCarName());
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setCarId("123");
        Car car2 = new Car();
        car2.setCarId("456");

        carRepository.create(car1);
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();

        assertTrue(iterator.hasNext());
        assertEquals("123", iterator.next().getCarId());
        assertEquals("456", iterator.next().getCarId());
    }

    @Test
    void testFindByIdExisting() {
        Car car = new Car();
        car.setCarId("123");
        carRepository.create(car);

        Car foundCar = carRepository.findById("123");

        assertNotNull(foundCar);
        assertEquals("123", foundCar.getCarId());
    }

    @Test
    void testFindByIdNotFound() {
        Car foundCar = carRepository.findById("999");

        assertNull(foundCar);
    }

    @Test
    void testUpdateExistingCar() {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Toyota");
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Honda");
        updatedCar.setCarColor("Blue");
        updatedCar.setCarQuantity(10);

        Car result = carRepository.update("123", updatedCar);

        assertNotNull(result);
        assertEquals("Honda", result.getCarName());
        assertEquals("Blue", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdateNonExistingCar() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Suzuki");

        Car result = carRepository.update("999", updatedCar);

        assertNull(result);
    }

    @Test
    void testDeleteExistingCar() {
        Car car = new Car();
        car.setCarId("123");
        carRepository.create(car);

        carRepository.delete("123");

        assertNull(carRepository.findById("123"));
    }

    @Test
    void testDeleteNonExistingCar() {
        carRepository.delete("999"); // Should not throw exception
        assertNull(carRepository.findById("999"));
    }
}
