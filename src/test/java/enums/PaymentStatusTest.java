// src/test/java/enums/PaymentStatusTest.java
package enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentStatusTest {
    @Test
    void testPaymentStatusValues() {
        assertEquals("PENDING", PaymentStatus.PENDING.getValue());
        assertEquals("SUCCESS", PaymentStatus.SUCCESS.getValue());
        assertEquals("REJECTED", PaymentStatus.REJECTED.getValue());
    }

    @Test
    void testPaymentStatusContains() {
        assertTrue(PaymentStatus.contains("PENDING"));
        assertTrue(PaymentStatus.contains("SUCCESS"));
        assertTrue(PaymentStatus.contains("REJECTED"));
        assertFalse(PaymentStatus.contains("INVALID"));
    }
}