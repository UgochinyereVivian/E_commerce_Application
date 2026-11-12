
from decimal import Decimal
from unittest import TestCase
from unittest.mock import Mock

from shopping.checkout import Checkout
from customer.customer import Customer
from shopping.inventory import Inventory
from shopping.cart import Cart
from shopping.product import Product


class TestCustomer(TestCase):

    def setUp(self):
        self.inventory = Inventory()
        self.cart = Cart()
        self.checkout = Mock(spec=Checkout)
        self.inventory.add_stock("biscuit", 500.00, 10)
        self.customer = Customer(self.inventory,self.cart,self.checkout)

    def test_constructor_throws_exception_if_inventory_is_none(self):
        with self.assertRaises(ValueError) as exception:
            Customer(None, self.cart, self.checkout)

        self.assertEqual("product inventory cannot be null",str(exception.exception))

    def test_view_products_returns_available_products(self):
        result = self.customer.view_products()

        self.assertEqual(1, len(result))
        self.assertEqual(10, next(iter(result.values())))

    def test_search_product_returns_product(self):
        result = self.customer.search_product("biscuit")

        product, quantity = next(iter(result.items()))

        self.assertEqual("biscuit", product.get_name())
        self.assertEqual(Decimal("500.0"), product.get_price())
        self.assertEqual(10, quantity)

    def test_add_to_cart_adds_product_to_cart(self):
        self.customer.add_to_cart("biscuit", 3)

        cart = self.customer.view_cart()
        product, quantity = next(iter(cart.items()))

        self.assertEqual("biscuit", product.get_name())
        self.assertEqual(3, quantity)

    def test_add_to_cart_updates_inventory_quantity(self):
        self.customer.add_to_cart("biscuit", 3)

        inventory = self.customer.view_products()

        self.assertEqual(7, next(iter(inventory.values())))

    def test_add_to_cart_throws_exception_if_quantity_exceeds_available_stock(self):
        with self.assertRaises(Exception) as exception:
            self.customer.add_to_cart("biscuit", 11)

        self.assertEqual(
            "requested quantity exceeds available stock",
            str(exception.exception)
        )

    def test_view_cart_returns_cart_contents(self):
        self.customer.add_to_cart("biscuit", 3)

        result = self.customer.view_cart()

        self.assertEqual(3, next(iter(result.values())))
        self.assertEqual(Decimal(500.0), next(iter(result.keys())).get_price())

    def test_customercheckout_returns_invoice(self):
        cart = {Product("biscuit", 500.00): 2}
        total = Decimal("1000.00")

        self.checkout.checkout.return_value = (cart, total)

        invoice = self.customer.checkout()
        self.checkout.checkout.assert_called_once()

        self.assertEqual(cart, invoice[0])
        self.assertEqual(total, invoice[1])

    def test_pay_calls_payment_make_payment_with_checkout_total(self):
        cart = {Product("biscuit", 500.00): 2}
        total = Decimal("1000.00")

        self.checkout.checkout.return_value = (cart, total)

        payment = Mock()
        payment_gateway = Mock()

        self.customer.pay(payment, payment_gateway)

        payment.make_payment.assert_called_once_with(total)

    def test_pay_throws_exception_if_payment_is_none(self):
        with self.assertRaises(ValueError) as exception:
            self.customer.pay(None, Mock())

        self.assertEqual("payment cannot be null",str(exception.exception))

    def test_remove_from_cart_removes_product_from_cart(self):
        self.customer.add_to_cart("biscuit", 3)

        self.customer.remove_from_cart("biscuit")

        self.assertEqual({}, self.customer.view_cart())

    def test_remove_from_cart_restores_inventory_quantity(self):
        self.customer.add_to_cart("biscuit", 3)

        self.customer.remove_from_cart("biscuit")

        inventory = self.customer.view_products()

        self.assertEqual(10, next(iter(inventory.values())))
