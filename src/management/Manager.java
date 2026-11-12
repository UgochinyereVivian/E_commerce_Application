package management;

import shopping.Product;
import shopping.ProductInventory;

import java.util.Map;

public class Manager {
    ProductInventory productInventory;

    public Manager(ProductInventory productInventory) {
        validateProductInventory(productInventory);
        this.productInventory = productInventory;
    }

    public void addNewStock(String name, double price, int quantity) throws Exception{
        getProductInventory().addStock(name, price,quantity);
    }

    public Map<Product, Integer> viewAllProducts() {
        return getProductInventory().viewAvailableGoods();
    }

    public void updateStockQuantity(String name, int quantity) throws Exception {
        getProductInventory().updateProductQuantity(name, quantity);
    }

    public void updateProductPrice(String name, int price) throws Exception {
        getProductInventory().changeProductPrice(name,price);
    }


    private void validateProductInventory(ProductInventory productInventory) {
        if (productInventory == null) {
            throw new IllegalArgumentException("product inventory cannot be null");
        }
    }
    private ProductInventory getProductInventory() {
        return productInventory;
    }



}
