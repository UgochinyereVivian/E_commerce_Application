from decimal import Decimal

class Checkout:

    def __init__(self, cart):
        self.__cart = cart

    def checkout(self):
        if not self.__cart.view_cart():
            raise ValueError("cart is empty")

        products = self.__cart.view_cart()
        total = self.__calculate_total()

        return products, total

    def __calculate_total(self):
        products = self.__cart.view_cart()
        total = Decimal("0")

        for product, quantity in products.items():
            price = product.get_price()
            total += price * Decimal(quantity)

        return total

