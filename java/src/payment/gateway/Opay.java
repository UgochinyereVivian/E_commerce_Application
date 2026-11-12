package payment.gateway;

import payment.PaymentGateway;

import java.math.BigDecimal;

public class Opay implements PaymentGateway {
    public boolean processPayment(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("payment amount must be greater than zero");

        System.out.println("Processing payment of ₦" + value + " through OPay");

        return true;
    }
}
