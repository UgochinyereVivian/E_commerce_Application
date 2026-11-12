from types import MappingProxyType

from product import Product


class Inventory:

    def __init__(self):
        self.stock = {}

    def search_product(self,name):
        self.__validate_product_exist(name)
        entry = {}
        for key, value in self.stock.items():
            if name.casefold() == key.get_name().casefold():
                entry.update({key:value})
        return entry

    def view_available_stock(self):
        return MappingProxyType(self.stock)

    def add_stock(self, name, price, quantity):
        product = Product(name, price)
        self.__validate_product_does_not_exist(product)
        self.__get_stock().setdefault(product,quantity)


    def change_product_price(self,name, amount):
        product = self.__validate_product_exist(name)
        product.set_price(amount)

    def update_product_quantity(self, name, quantity):
        product = self.__validate_product_exist(name)
        self.__get_stock().update({product:quantity})

    def remove_product(self, name):
        product = self.__validate_product_exist(name)
        self.__get_stock().pop(product)

    def __get_stock(self):
        return self.stock

    def __validate_product_exist(self,name):
        for product in self.__get_stock().keys():
            if name.casefold() == product.get_name().casefold():
                return product
        raise ValueError(f"Product {name} does not exist")

    def __validate_product_does_not_exist(self,product):
        if product in self.stock:
            raise ValueError(f"Product {product.get_name()} already exist")


