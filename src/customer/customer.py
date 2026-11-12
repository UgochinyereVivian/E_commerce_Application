from decimal import Decimal

class Customer:

    def __init__(self, inventory, cart, checkout):
        self.__validate_product_inventory(inventory)

        self.__inventory = inventory
        self.__cart = cart
        self.__checkout = checkout

    def view_products(self):
        return self.__inventory.view_available_stock()

    def search_product(self, name):
        return self.__inventory.search_product(name)

    def add_to_cart(self, name, quantity):

        product_entry = self.__inventory.search_product(name)

        product, available_quantity = next(iter(product_entry.items()))

        if quantity > available_quantity:
            raise Exception("requested quantity exceeds available stock")

        self.__cart.add_to_cart(product, quantity)

        self.__inventory.update_product_quantity(name, available_quantity - quantity)

    def view_cart(self):
        return self.__cart.view_cart()

    def checkout(self):
        return self.__checkout.checkout()

    def pay(self, payment, payment_gateway):

        if payment is None:
            raise ValueError("payment cannot be null")

        invoice = self.checkout()

        cart, total = invoice

        payment.make_payment(total)

    def remove_from_cart(self, name):

        quantity_to_restore = 0

        for product, quantity in self.__cart.view_cart().items():
            if product.get_name().casefold() == name.casefold():
                quantity_to_restore = quantity
                break

        inventory_product = self.__inventory.search_product(name)

        product, available_quantity = next(iter(inventory_product.items()))

        self.__cart.remove_from_cart(name)

        self.__inventory.update_product_quantity(
            name, available_quantity + quantity_to_restore
        )

    def __validate_product_inventory(self, inventory):
        if inventory is None:
            raise ValueError("product inventory cannot be null")