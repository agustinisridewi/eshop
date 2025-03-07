package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarServiceImplTest {

    @Mock
    private CarRepository carRepository; // Mock repository

    @InjectMocks
    private CarServiceImpl carService; // Inject mock repository into service

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testCreateCar() {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        when(carRepository.create(car)).thenReturn(car); // Mock behavior

        Car createdCar = carService.create(car);

        assertNotNull(createdCar);
        assertEquals("Toyota", createdCar.getCarName());
        verify(carRepository, times(1)).create(car); // Ensure repository method is called
    }

    @Test
    void testFindAllCars() {
        Car car1 = new Car(); car1.setCarId("123"); car1.setCarName("Toyota");
        Car car2 = new Car(); car2.setCarId("456"); car2.setCarName("Honda");

        Iterator<Car> iterator = Arrays.asList(car1, car2).iterator();

        when(carRepository.findAll()).thenReturn(iterator); // Mock repository

        List<Car> cars = carService.findAll();

        assertEquals(2, cars.size());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindCarById() {
        Car car = new Car();
        car.setCarId("123");
        when(carRepository.findById("123")).thenReturn(car);

        Car foundCar = carService.findById("123");

        assertNotNull(foundCar);
        assertEquals("123", foundCar.getCarId());
        verify(carRepository, times(1)).findById("123");
    }

    @Test
    void testUpdateCar() {
        Car updatedCar = new Car();
        updatedCar.setCarId("123");
        updatedCar.setCarName("Updated Name");

        when(carRepository.update("123", updatedCar)).thenReturn(updatedCar);

        carService.update("123", updatedCar);

        verify(carRepository, times(1)).update("123", updatedCar);
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carRepository).delete("123");

        carService.deleteCarById("123");

        verify(carRepository, times(1)).delete("123");
    }
}
