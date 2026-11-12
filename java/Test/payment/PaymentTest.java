package payment;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    public void testConstructorThrowsExceptionIfPaymentGatewayIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Payment(null)
        );

        assertEquals("payment gateway cannot be null", exception.getMessage());
    }

    @Test
    public void testMakePaymentReturnsTrueIfPaymentIsSuccessful() {
        PaymentGateway gateway = value -> true;

        Payment payment = new Payment(gateway);

        assertTrue(payment.makePayment(new BigDecimal("500.00")));
    }

    @Test
    public void testMakePaymentReturnsFalseIfPaymentFails() {
        PaymentGateway gateway = new PaymentGateway() {
            public boolean processPayment(BigDecimal value) {
                return false;
            }
        };

        Payment payment = new Payment(gateway);

        assertFalse(payment.makePayment(new BigDecimal("500.00")));
    }

    @Test
    public void testMakePaymentPassesPaymentAmountToPaymentGateway() {
        BigDecimal expectedValue = new BigDecimal("500.00");

        PaymentGateway gateway = new PaymentGateway() {
            public boolean processPayment(BigDecimal value) {
                assertEquals(expectedValue, value);
                return true;
            }
        };

        Payment payment = new Payment(gateway);

        assertTrue(payment.makePayment(expectedValue));
    }
}