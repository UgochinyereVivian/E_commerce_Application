from decimal import Decimal
from unittest import TestCase
from shopping.inventory import Inventory
from shopping import Product


class TestInventory(TestCase):
    def setUp(self):
        self.inventory = Inventory()
        self.result = self.inventory.view_available_stock()
        self.product = Product("biscuit",500.0)

    def test_that_available_stock_is_accurately_displayed(self):
        self.assertEqual({},self.result)

        self.inventory.add_stock("biscuit", 500.0, 10)
        self.product = next(iter(self.result.keys()))
        self.assertEqual({self.product:10},self.result)
        self.assertEqual(Decimal(500.0), self.product.get_price())

    def test_add_stock(self):
        self.assertEqual({},self.result)

        self.inventory.add_stock("biscuit", 500.0, 10)
        self.assertEqual({self.product:10}, self.result)

    def test_add_stock_throws_exception_if_goods_exist(self):
        self.inventory.add_stock("biscuit", 500.0, 10)
        self.assertEqual(1,len(self.result))

        with self.assertRaises(ValueError) as exception:
            self.inventory.add_stock("biscuit", 500.0, 10)
        self.assertEqual("Product biscuit already exist", exception.exception.args[0])

    def test_update_product_quantity_updates_existing_product(self):
        self.inventory.add_stock("biscuit", "500.00", 10)

        self.inventory.update_product_quantity("biscuit", 20)

        self.assertEqual(20, self.result.get(self.product))


    def test_that_if_product_does_not_exist_in_inventory_exception_is_thrown(self):
        self.inventory.add_stock("biscuit","500.00", 10)

        with self.assertRaises(ValueError) as exception:
            self.inventory.update_product_quantity("rice", 20)

        self.assertEqual("Product rice does not exist", exception.exception.args[0])

    def test_change_product_price_changes_existing_product_price(self):
        self.inventory.add_stock("biscuit", Decimal("500.00"), 10)

        self.inventory.change_product_price("biscuit", Decimal("700.00"))

        product = next(iter(self.result))

        self.assertEqual(Decimal("700.00"), product.get_price())

    def test_that_change_price_throws_exception_if_product_does_not_exist(self):
        self.inventory.add_stock("biscuit", Decimal("500.00"), 10)

        with self.assertRaises(ValueError) as exception:
            self.inventory.change_product_price("rice", Decimal("700.00"))

        self.assertEqual("Product rice does not exist", str(exception.exception))

    def test_remove_product_removes_product_from_stock(self):
        self.inventory.add_stock("biscuit", Decimal("500.00"), 10)

        self.assertEqual({self.product: 10},self.result)

        self.inventory.remove_product("Biscuit")

        self.assertEqual({}, self.result)

    def test_remove_product_throws_exception_if_product_is_not_in_stock(self):
        self.inventory.add_stock("biscuit", Decimal("500.00"), 10)

        try:
            self.inventory.remove_product("milk")
            self.fail("ValueError was not raised")
        except ValueError as exception:
            self.assertEqual("Product milk does not exist", str(exception))

    def test_search_product(self):
        self.inventory.add_stock("biscuit","500.00", 10)

        product_searched = self.inventory.search_product("Biscuit")

        self.assertEqual({self.product:10},product_searched)

        self.assertEqual(Decimal("500.00"),next(iter(product_searched)).get_price())\


    def test_search_product_throws_exception_when_product_does_not_exist(self):
        self.inventory.add_stock("biscuit", Decimal("500.00"), 10)

        try:
            self.inventory.search_product("Milk")
            self.fail("Exception was not thrown")
        except ValueError as exception:
            self.assertEqual("Product Milk does not exist", str(exception))