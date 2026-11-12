from typing import override


from decimal import Decimal
class Product:

    def __init__(self,name,price):
        self.__name = name
        self.__price = Decimal(str(price))

    def get_name(self):
        return self.__name

    def get_price(self):
        return self.__price

    def set_price(self,price):
        price = Decimal(str(price))
        if price <= 0: raise ValueError("Price must be greater than 0")
        self.__price = price

    @override
    def __eq__(self,product):
        if self is product:
            return True
        if product is None or type(self) is not type(product):
            return False

        return self.__name.casefold() == product.get_name().casefold()
    @override
    def __hash__(self):
        return hash(self.__name.casefold())

