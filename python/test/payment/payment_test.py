from unittest import TestCase
from decimal import Decimal
from payment import Payment


class FakePaymentGateway:

    def __init__(self, result):
        self.result = result
        self.value = None

    def process_payment(self, value):
        self.value = value
        return self.result


class TestPayment(TestCase):

    def test_constructor_raises_exception_if_payment_gateway_is_none(self):
        with self.assertRaises(ValueError) as exception:
            Payment(None)

        self.assertEqual(
            "payment gateway cannot be null",
            str(exception.exception)
        )

    def test_make_payment_returns_true_if_payment_is_successful(self):
        gateway = FakePaymentGateway(True)
        payment = Payment(gateway)

        self.assertTrue(payment.make_payment(Decimal("500.00")))

    def test_make_payment_returns_false_if_payment_fails(self):
        gateway = FakePaymentGateway(False)
        payment = Payment(gateway)

        self.assertFalse(payment.make_payment(Decimal("500.00")))

    def test_make_payment_passes_payment_amount_to_payment_gateway(self):
        gateway = FakePaymentGateway(True)
        payment = Payment(gateway)
        value = Decimal("500.00")

        payment.make_payment(value)

        self.assertEqual(value, gateway.value)