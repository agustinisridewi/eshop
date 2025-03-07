package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Toyota");

        mockMvc.perform(post("/car/createCar")
                        .flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    void testCarListPage() throws Exception {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carService.findAll()).thenReturn(cars);

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CarList"))
                .andExpect(model().attributeExists("cars"));

        verify(carService, times(1)).findAll();
    }

    @Test
    void testEditCarPage() throws Exception {
        Car car = new Car();
        car.setCarId("123");

        when(carService.findById("123")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditCar"))
                .andExpect(model().attributeExists("car"));

        verify(carService, times(1)).findById("123");
    }

    @Test
    void testEditCarPost() throws Exception {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Honda");

        mockMvc.perform(post("/car/editCar")
                        .flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).update(eq("123"), any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).deleteCarById("123");
    }
}
