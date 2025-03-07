package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import enums.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VoucherPaymentServiceImpl implements VoucherPaymentService {
    @Autowired
    private PaymentService paymentService;

    @Override
    public Payment createPayment(Order order, Map<String, String> paymentData) {
        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);

        String voucherCode = paymentData.get("voucherCode");

        if (isValidVoucher(voucherCode)) {
            paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        } else {
            paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());
        }

        return payment;
    }

    private boolean isValidVoucher(String voucherCode) {
        if (voucherCode == null) {
            return false;
        }

        // Check if length is 16
        if (voucherCode.length() != 16) {
            return false;
        }

        // Check if starts with "ESHOP"
        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        // Check if contains 8 numerical characters
        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }

        return digitCount == 8;
    }
}