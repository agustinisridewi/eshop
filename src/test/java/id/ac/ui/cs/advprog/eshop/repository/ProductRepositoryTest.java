package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> products = productRepository.findAll();
        assertTrue(products.hasNext());
        Product savedProduct = products.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> products = productRepository.findAll();
        assertFalse(products.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        Iterator<Product> products = productRepository.findAll();
        assertTrue(products.hasNext());
        Product savedProduct = products.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = products.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(products.hasNext());
    }


    @Test
    void testUpdateProduct_Success() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        String id = product.getProductId();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Baru");
        updatedProduct.setProductQuantity(150);
        Product result = productRepository.edit(id, updatedProduct);

        assertNotNull(result);
        assertEquals("Sampo Cap Baru", result.getProductName());
        assertEquals(150, result.getProductQuantity());
    }

    @Test
    void testUpdateProduct_NotFound() {
        String id = "non-existent-id"; // Use a valid but non-existent ID
        Product updatedProduct = new Product();
        updatedProduct.setProductId(id);
        updatedProduct.setProductName("Sampo Cap Baru");
        updatedProduct.setProductQuantity(150);

        Product result = productRepository.edit(id, updatedProduct);

        assertNull(result, "Updating a non-existent product should return null");
    }

    @Test
    void testDeleteProduct_Success() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);
        productRepository.delete(product);

        Iterator <Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProduct_NotFound() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.delete(product);

        Iterator <Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

}