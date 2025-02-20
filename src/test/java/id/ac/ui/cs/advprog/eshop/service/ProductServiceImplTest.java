package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
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

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Product product = new Product();
        product.setProductName("Laptop");
        product.setProductQuantity(10);
        when(productRepository.create(product)).thenReturn(product);

        Product result = productService.create(product);

        assertNotNull(result);
        assertEquals("Laptop", result.getProductName());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        Product product1 = new Product();
        product1.setProductName("Laptop");
        Product product2 = new Product();
        product2.setProductName("Phone");
        List<Product> productList = Arrays.asList(product1, product2);
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals("Phone", result.get(1).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductById() {
        Product product = new Product();
        product.setProductName("Laptop");
        when(productRepository.findProductById(anyString())).thenReturn(product);

        Product result = productService.findProductById("some-id");

        assertNotNull(result);
        assertEquals("Laptop", result.getProductName());
        verify(productRepository, times(1)).findProductById(anyString());
    }

    @Test
    void testEdit() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Laptop Pro");
        updatedProduct.setProductQuantity(20);
        when(productRepository.edit(anyString(), eq(updatedProduct))).thenReturn(updatedProduct);

        Product result = productService.edit("some-id", updatedProduct);

        assertNotNull(result);
        assertEquals("Laptop Pro", result.getProductName());
        assertEquals(20, result.getProductQuantity());
        verify(productRepository, times(1)).edit(anyString(), eq(updatedProduct));
    }

    @Test
    void testDelete() {
        doNothing().when(productRepository).delete(anyString());

        assertDoesNotThrow(() -> productService.delete("some-id"));

        verify(productRepository, times(1)).delete(anyString());
    }
}