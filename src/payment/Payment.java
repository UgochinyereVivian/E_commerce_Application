package payment;

import java.math.BigDecimal;

public class Payment {
    PaymentGateway paymentGateway;

    public Payment(PaymentGateway paymentGateway) {
        if (paymentGateway == null)throw new IllegalArgumentException("payment gateway cannot be null");

        this.paymentGateway = paymentGateway;
    }

    public boolean makePayment(BigDecimal value) {
        return paymentGateway.processPayment(value);
    }
}
