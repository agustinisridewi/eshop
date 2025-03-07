package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Payment payment;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("order-123", products, System.currentTimeMillis(), "user123");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");

        payment = new Payment("payment-123", order, "BANK_TRANSFER", paymentData);
    }

    @Test
    void testSavePayment() {
        Payment savedPayment = paymentRepository.save(payment);

        assertEquals(payment, savedPayment);
        assertEquals(payment, paymentRepository.findById("payment-123"));
    }

    @Test
    void testUpdatePayment() {
        paymentRepository.save(payment);

        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put("bankName", "BNI");
        newPaymentData.put("referenceCode", "REF789012");

        Payment updatedPayment = new Payment("payment-123", order, "BANK_TRANSFER", newPaymentData);
        Payment result = paymentRepository.save(updatedPayment);

        assertEquals(updatedPayment, result);
        assertEquals("BNI", paymentRepository.findById("payment-123").getPaymentData().get("bankName"));
    }

    @Test
    void testFindByIdNonExistent() {
        assertNull(paymentRepository.findById("non-existent-id"));
    }

    @Test
    void testFindByIdNull() {
        assertNull(paymentRepository.findById(null));
    }

    @Test
    void testFindByIdExists() {
        paymentRepository.save(payment);
        Payment found = paymentRepository.findById("payment-123");

        assertNotNull(found);
        assertEquals(payment, found);
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment);

        Order order2 = new Order("order-456", new ArrayList<>() {{
            Product product = new Product();
            product.setProductName("Another Product");
            product.setProductQuantity(2);
            add(product);
        }}, System.currentTimeMillis(), "user456");

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("bankName", "Mandiri");
        paymentData2.put("referenceCode", "REF789012");

        Payment payment2 = new Payment("payment-456", order2, "BANK_TRANSFER", paymentData2);
        paymentRepository.save(payment2);

        List<Payment> allPayments = paymentRepository.findAll();

        assertEquals(2, allPayments.size());
        assertTrue(allPayments.contains(payment));
        assertTrue(allPayments.contains(payment2));
    }

    @Test
    void testFindByIdEmptyRepository() {
        assertNull(paymentRepository.findById("random-id"));
    }

    @Test
    void testFindAllEmptyRepository() {
        assertTrue(paymentRepository.findAll().isEmpty());
    }

}