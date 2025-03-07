package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankTransferPaymentServiceTest {
    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private BankTransferPaymentServiceImpl bankTransferPaymentService;

    private Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("order-123", products, System.currentTimeMillis(), "user123");
    }

    @Test
    void testCreatePaymentSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");

        Payment mockPayment = new Payment("payment-123", order, "BANK_TRANSFER", paymentData);

        when(paymentService.addPayment(any(Order.class), anyString(), any(Map.class))).thenReturn(mockPayment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(mockPayment);

        Payment result = bankTransferPaymentService.createPayment(order, paymentData);

        assertNotNull(result);
        assertEquals(mockPayment, result);

        verify(paymentService, times(1)).addPayment(order, "BANK_TRANSFER", paymentData);
        verify(paymentService, times(1)).setStatus(mockPayment, PaymentStatus.SUCCESS.getValue());
    }

    @Test
    void testCreatePaymentRejectEmptyBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF123456");

        Payment mockPayment = new Payment("payment-123", order, "BANK_TRANSFER", paymentData);

        when(paymentService.addPayment(any(Order.class), anyString(), any(Map.class))).thenReturn(mockPayment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(mockPayment);

        Payment result = bankTransferPaymentService.createPayment(order, paymentData);

        assertNotNull(result);
        assertEquals(mockPayment, result);

        verify(paymentService, times(1)).addPayment(order, "BANK_TRANSFER", paymentData);
        verify(paymentService, times(1)).setStatus(mockPayment, PaymentStatus.REJECTED.getValue());
    }

    @Test
    void testCreatePaymentRejectNullBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("referenceCode", "REF123456");

        Payment mockPayment = new Payment("payment-123", order, "BANK_TRANSFER", paymentData);

        when(paymentService.addPayment(any(Order.class), anyString(), any(Map.class))).thenReturn(mockPayment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(mockPayment);

        Payment result = bankTransferPaymentService.createPayment(order, paymentData);

        assertNotNull(result);
        assertEquals(mockPayment, result);

        verify(paymentService, times(1)).addPayment(order, "BANK_TRANSFER", paymentData);
        verify(paymentService, times(1)).setStatus(mockPayment, PaymentStatus.REJECTED.getValue());
    }

    @Test
    void testCreatePaymentRejectEmptyReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");

        Payment mockPayment = new Payment("payment-123", order, "BANK_TRANSFER", paymentData);

        when(paymentService.addPayment(any(Order.class), anyString(), any(Map.class))).thenReturn(mockPayment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(mockPayment);

        Payment result = bankTransferPaymentService.createPayment(order, paymentData);

        assertNotNull(result);
        assertEquals(mockPayment, result);

        verify(paymentService, times(1)).addPayment(order, "BANK_TRANSFER", paymentData);
        verify(paymentService, times(1)).setStatus(mockPayment, PaymentStatus.REJECTED.getValue());
    }

    @Test
    void testCreatePaymentRejectNullReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");

        Payment mockPayment = new Payment("payment-123", order, "BANK_TRANSFER", paymentData);

        when(paymentService.addPayment(any(Order.class), anyString(), any(Map.class))).thenReturn(mockPayment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(mockPayment);

        Payment result = bankTransferPaymentService.createPayment(order, paymentData);

        assertNotNull(result);
        assertEquals(mockPayment, result);

        verify(paymentService, times(1)).addPayment(order, "BANK_TRANSFER", paymentData);
        verify(paymentService, times(1)).setStatus(mockPayment, PaymentStatus.REJECTED.getValue());
    }
}