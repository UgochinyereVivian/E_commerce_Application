from decimal import Decimal

from payment_gateway import PaymentGateway

class Flutterwave(PaymentGateway):
    def process_payment(self, value: Decimal):
        if value is None or value <= 0:
            raise ValueError("payment amount must be greater than 0")

        return True