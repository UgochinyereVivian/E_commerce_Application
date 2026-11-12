from unittest import TestCase
from unittest.mock import Mock
from decimal import Decimal
from management import Manager
from shopping import Product


class TestManager(TestCase):

    def setUp(self):
        self.inventory = Mock()
        self.manager = Manager(self.inventory)

    def test_constructor_raises_exception_if_product_inventory_is_none(self):
        with self.assertRaises(ValueError) as exception:
            Manager(None)

        self.assertEqual("product inventory cannot be null", str(exception.exception))

    def test_view_all_products_returns_all_products_in_inventory(self):

        expected_products = {Product("biscuit", Decimal("500.00")): 10,Product("milk", Decimal("1000.00")): 5}

        self.inventory.view_available_goods.return_value = expected_products

        self.assertEqual(expected_products,self.manager.view_all_products())

        self.inventory.view_available_goods.assert_called_once()

    def test_add_new_stock_adds_product_to_inventory(self):
        self.manager.add_new_stock("biscuit", Decimal("500.00"), 10)

        self.inventory.add_stock.assert_called_once_with("biscuit", Decimal("500.00"), 10)

        self.inventory.view_available_goods.return_value = self.added_products
        self.assertEqual(self.added_products, self.manager.view_all_products())



    def test_update_stock_quantity_updates_product_quantity(self):
        self.manager.update_stock_quantity("biscuit", 20)

        self.inventory.update_product_quantity.assert_called_once_with("biscuit", 20)

    def test_update_product_price_updates_product_price(self):
        self.manager.update_product_price("biscuit", Decimal("700.00"))

        self.inventory.change_product_price.assert_called_once_with("biscuit", Decimal("700.00"))