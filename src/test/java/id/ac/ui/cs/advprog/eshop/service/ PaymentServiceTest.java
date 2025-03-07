package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import enums.OrderStatus;
import enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("order-123", products, System.currentTimeMillis(), "user123");

        paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
    }

    @Test
    void testAddPayment() {
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        assertNotNull(result);
        assertEquals(order, result.getOrder());
        assertEquals("BANK_TRANSFER", result.getMethod());
        assertEquals(PaymentStatus.PENDING.getValue(), result.getStatus());
        assertEquals(paymentData, result.getPaymentData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = new Payment("payment-123", order, "BANK_TRANSFER", paymentData);

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderService.updateStatus(eq("order-123"), eq(OrderStatus.SUCCESS.getValue()))).thenReturn(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());

        verify(paymentRepository, times(1)).save(payment);
        verify(orderService, times(1)).updateStatus("order-123", OrderStatus.SUCCESS.getValue());
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("payment-123", order, "BANK_TRANSFER", paymentData);

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderService.updateStatus(eq("order-123"), eq(OrderStatus.FAILED.getValue()))).thenReturn(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());

        verify(paymentRepository, times(1)).save(payment);
        verify(orderService, times(1)).updateStatus("order-123", OrderStatus.FAILED.getValue());
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment("payment-123", order, "BANK_TRANSFER", paymentData);

        when(paymentRepository.findById("payment-123")).thenReturn(payment);

        Payment result = paymentService.getPayment("payment-123");

        assertEquals(payment, result);

        verify(paymentRepository, times(1)).findById("payment-123");
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("payment-123", order, "BANK_TRANSFER", paymentData));

        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(payments, result);

        verify(paymentRepository, times(1)).findAll();
    }
}