package payment;

import java.math.BigDecimal;

public interface PaymentGateway {
    boolean processPayment(BigDecimal value);
}
