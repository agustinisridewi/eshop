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
class VoucherPaymentServiceTest {
    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private VoucherPaymentServiceImpl voucherPaymentService;

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
    void testCreatePaymentWithValidVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment mockPayment = new Payment("payment-123", order, "VOUCHER", paymentData);

        when(paymentService.addPayment(any(Order.class), anyString(), any(Map.class))).thenReturn(mockPayment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(mockPayment);

        Payment result = voucherPaymentService.createPayment(order, paymentData);

        assertNotNull(result);
        assertEquals(mockPayment, result);

        verify(paymentService, times(1)).addPayment(order, "VOUCHER", paymentData);
        verify(paymentService, times(1)).setStatus(mockPayment, PaymentStatus.SUCCESS.getValue());
    }

    @Test
    void testCreatePaymentWithInvalidVoucherNotStartingWithESHOP() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "SHOP12345678ABCD");

        Payment mockPayment = new Payment("payment-123", order, "VOUCHER", paymentData);

        when(paymentService.addPayment(any(Order.class), anyString(), any(Map.class))).thenReturn(mockPayment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(mockPayment);

        Payment result = voucherPaymentService.createPayment(order, paymentData);

        assertNotNull(result);
        assertEquals(mockPayment, result);

        verify(paymentService, times(verify(paymentService, times(1)).addPayment(order, "VOUCHER", paymentData);
        verify(paymentService, times(1)).setStatus(mockPayment, PaymentStatus.REJECTED.getValue());
    }

    @Test
    void testCreatePaymentWithInvalidVoucherIncorrectLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC567");  // Only 15 characters

        Payment mockPayment = new Payment("payment-123", order, "VOUCHER", paymentData);

        when(paymentService.addPayment(any(Order.class), anyString(), any(Map.class))).thenReturn(mockPayment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(mockPayment);

        Payment result = voucherPaymentService.createPayment(order, paymentData);

        assertNotNull(result);
        assertEquals(mockPayment, result);

        verify(paymentService, times(1)).addPayment(order, "VOUCHER", paymentData);
        verify(paymentService, times(1)).setStatus(mockPayment, PaymentStatus.REJECTED.getValue());
    }

    @Test
    void testCreatePaymentWithInvalidVoucherNotEnoughDigits() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123ABCDEF45");  // Only 4 digits

        Payment mockPayment = new Payment("payment-123", order, "VOUCHER", paymentData);
        when(paymentService.addPayment(any(Order.class), anyString(), any(Map.class))).thenReturn(mockPayment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(mockPayment);

        Payment result = voucherPaymentService.createPayment(order, paymentData);

        assertNotNull(result);
        assertEquals(mockPayment, result);

        verify(paymentService, times(1)).addPayment(order, "VOUCHER", paymentData);
        verify(paymentService, times(1)).setStatus(mockPayment, PaymentStatus.REJECTED.getValue());
    }

    @Test
    void testCreatePaymentWithNullVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();

        Payment mockPayment = new Payment("payment-123", order, "VOUCHER", paymentData);

        when(paymentService.addPayment(any(Order.class), anyString(), any(Map.class))).thenReturn(mockPayment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(mockPayment);

        Payment result = voucherPaymentService.createPayment(order, paymentData);

        assertNotNull(result);
        assertEquals(mockPayment, result);

        verify(paymentService, times(1)).addPayment(order, "VOUCHER", paymentData);
        verify(paymentService, times(1)).setStatus(mockPayment, PaymentStatus.REJECTED.getValue());
    }
}