class Payment:

    def __init__(self, payment_gateway):
        if payment_gateway is None:
            raise ValueError("payment gateway cannot be null")

        self.__payment_gateway = payment_gateway

    def make_payment(self, value):
        return self.__payment_gateway.process_payment(value)