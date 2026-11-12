from unittest import TestCase
from decimal import Decimal
from shopping import Product
from shopping import Cart


class TestCart(TestCase):

    def setUp(self):
        self.cart = Cart()
        self.product = Product("biscuit", Decimal("500.00"))

    def test_add_to_cart_adds_product(self):
        self.cart.add_to_cart(self.product, 10)
        self.assertEqual({self.product: 10}, self.cart.view_cart())

    def test_add_to_cart_increases_quantity_if_product_already_exists(self):
        self.cart.add_to_cart(self.product, 2)
        self.cart.add_to_cart(self.product, 3)

        self.assertEqual({self.product: 5},dict(self.cart.view_cart()))

    def test_add_to_cart_raises_exception_if_quantity_is_zero(self):
        with self.assertRaises(ValueError) as exception:
            self.cart.add_to_cart(self.product, 0)

        self.assertEqual("quantity cant be zero", str(exception.exception))

    def test_add_to_cart_raises_exception_if_quantity_is_negative(self):
        with self.assertRaises(ValueError) as exception:
            self.cart.add_to_cart(self.product, -2)

        self.assertEqual("quantity cant be zero", str(exception.exception))

    def test_view_cart_returns_cart_items(self):
        self.cart.add_to_cart(self.product, 10)

        self.assertEqual({self.product: 10}, dict(self.cart.view_cart()))

    def test_view_cart_returns_unmodifiable_map(self):
        self.cart.add_to_cart(self.product, 10)

        with self.assertRaises(TypeError):
            self.cart.view_cart()[self.product] = 20

    def test_remove_from_cart_removes_product(self):
        self.cart.add_to_cart(self.product, 10)

        self.cart.remove_from_cart("biscuit")

        self.assertEqual({}, dict(self.cart.view_cart()))

    def test_remove_from_cart_is_case_insensitive(self):
        self.cart.add_to_cart(self.product, 10)

        self.cart.remove_from_cart("BISCUIT")

        self.assertEqual({}, dict(self.cart.view_cart()))

    def test_remove_from_cart_raises_exception_if_product_is_not_in_cart(self):
        with self.assertRaises(ValueError) as exception:
            self.cart.remove_from_cart("milk")

        self.assertEqual("product not found in cart", str(exception.exception))