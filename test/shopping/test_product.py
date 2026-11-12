from decimal import Decimal
from unittest import TestCase
from shopping.product import Product


class TestProduct(TestCase):

    def setUp(self):
        self.product = Product("biscuit", "500.00")

    def test_get_name_returns_product_name(self):
        self.assertEqual("biscuit", self.product.get_name())

    def test_get_price_returns_product_price(self):
        self.assertEqual(Decimal("500.00"), self.product.get_price())

    def test_products_with_same_name_are_equal(self):
        product = Product("BISCUIT", "700.00")

        self.assertEqual(self.product, product)

    def test_products_with_different_names_are_not_equal(self):
        product = Product("milk", "500.00")

        self.assertNotEqual(self.product, product)

    def test_product_is_equal_to_itself(self):
        self.assertEqual(self.product, self.product)

    def test_product_is_not_equal_to_none(self):
        self.assertNotEqual(self.product, None)

    def test_products_with_same_name_have_same_hash(self):
        product = Product("BISCUIT", "700.00")

        self.assertEqual(hash(self.product), hash(product))

    def test_set_price_updates_product_price(self):
        self.product.set_price(Decimal("700.00"))

        self.assertEqual(Decimal("700.00"), self.product.get_price())

    def test_set_price_throws_exception_if_price_is_zero(self):
        with self.assertRaises(ValueError) as exception:
            self.product.set_price(Decimal("0"))

        self.assertEqual("Price must be greater than 0", str(exception.exception))

    def test_set_price_throws_exception_if_price_is_negative(self):
        with self.assertRaises(ValueError) as exception:
            self.product.set_price(Decimal("-100.00"))

        self.assertEqual("Price must be greater than 0", str(exception.exception))