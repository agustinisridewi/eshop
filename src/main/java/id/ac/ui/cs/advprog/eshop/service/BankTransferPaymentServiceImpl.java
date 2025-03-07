package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import enums.PaymentMethod;
import enums.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BankTransferPaymentServiceImpl implements BankTransferPaymentService {
    @Autowired
    private PaymentService paymentService;

    @Override
    public Payment createPayment(Order order, Map<String, String> paymentData) {
        Payment payment = paymentService.addPayment(order, PaymentMethod.BANK_TRANSFER.getValue(), paymentData);

        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");

        if (bankName == null || bankName.isEmpty() || referenceCode == null || referenceCode.isEmpty()) {
            paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());
        } else {
            paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        }

        return payment;
    }
}