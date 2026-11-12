from decimal import Decimal
from unittest import TestCase

from shopping.checkout import Checkout
from shopping.cart import Cart
from shopping.product import Product


class TestCheckout(TestCase):

    def setUp(self):
        self.cart = Cart()
        self.checkout = Checkout(self.cart)

    def test_checkout_returns_cart_and_total(self):
        product = Product("biscuit", "500.00")
        self.cart.add_to_cart(product, 2)

        cart, total = self.checkout.checkout()

        self.assertEqual({product: 2}, cart)
        self.assertEqual(Decimal("1000.00"), total)

    def test_checkout_calculates_total_for_multiple_products(self):
        biscuit = Product("biscuit", "500.00")
        milk = Product("milk", "300.00")

        self.cart.add_to_cart(biscuit, 2)
        self.cart.add_to_cart(milk, 3)

        cart, total = self.checkout.checkout()

        self.assertEqual({biscuit: 2, milk: 3}, cart)
        self.assertEqual(Decimal("1900.00"), total)

    def test_checkout_throws_exception_if_cart_is_empty(self):
        with self.assertRaises(ValueError) as exception:
            self.checkout.checkout()

        self.assertEqual("cart is empty", str(exception.exception))