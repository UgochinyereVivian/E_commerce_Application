class Manager:

    def __init__(self, product_inventory):
        self.__validate_product_inventory(product_inventory)
        self.__product_inventory = product_inventory

    def add_new_stock(self, name, price, quantity):
        self.__get_product_inventory().add_stock(name, price, quantity)

    def view_all_products(self):
        return self.__get_product_inventory().view_available_goods()

    def update_stock_quantity(self, name, quantity):
        self.__get_product_inventory().update_product_quantity(name, quantity)

    def update_product_price(self, name, price):
        self.__get_product_inventory().change_product_price(name, price)

    def __validate_product_inventory(self, product_inventory):
        if product_inventory is None:
            raise ValueError("product inventory cannot be null")

    def __get_product_inventory(self):
        return self.__product_inventory