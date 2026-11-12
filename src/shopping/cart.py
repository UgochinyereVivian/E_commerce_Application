from types import MappingProxyType

from product import Product


class Cart:

    def __init__(self):
        self.cart_items = {}

    def add_to_cart(self, product:Product, quantity):
        if quantity <= 0: raise ValueError("quantity cant be zero")
        self.cart_items[product] = self.cart_items.get(product, 0) + quantity

    def view_cart(self):
        return MappingProxyType(self.cart_items)

    def remove_from_cart(self,name:str):
        for product, quantity in self.cart_items.items():
            if product.get_name().casefold() == name.casefold():
                self.cart_items.pop(product)
                # del self.cart_items[product]
                return

        raise ValueError("product not found in cart")