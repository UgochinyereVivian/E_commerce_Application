package shopping;

import java.util.*;

public class ProductInventory {
   private Map<Product, Integer> stock = new HashMap<>();

    public void addStock(String name, double price, int quantity) throws Exception {
        Product product = createProduct(name,price);
        validateProductDoesNotExist(product);
        this.getStock().putIfAbsent(product, quantity);
    }


    public void changeProductPrice(String name, int amount) throws Exception {
        Product product = validateProductExists(name);
        product.updateProductPrice(amount);
    }

    public void updateProductQuantity(String name, int quantity) throws Exception {
            Product product = validateProductExists(name);
            this.getStock().replace(product, quantity);

    }

    public Map<Product, Integer> viewAvailableGoods() {
        return Collections.unmodifiableMap(getStock());

    }

    public void removeProduct(String name) throws Exception {
        Product product = validateProductExists(name);
        this.getStock().remove(product);

    }

    private Map<Product, Integer> getStock() {
        return stock;
    }

    private Product createProduct(String name, double price) throws Exception {
        return new Product(name,price);
    }


    private Product validateProductExists(String name) throws Exception {
        for (Product product : getStock().keySet()) {
            if (product.getProductName().equalsIgnoreCase(name)){
                return product;
            }
        }
        throw new Exception("product not in stock");
    }

    private void validateProductDoesNotExist(Product product) throws Exception {
        if (getStock().containsKey(product)) {
            throw new Exception("product exist in stock, you can update product quantity");
        }
    }

    public Map.Entry<Product, Integer> searchProduct(String name) throws Exception {
            for (Map.Entry<Product, Integer> entry : getStock().entrySet()) {
                if (entry.getKey().getProductName().equalsIgnoreCase(name))
                    return  Map.entry(entry.getKey(), entry.getValue());
            }

            throw new Exception("product not in stock");
    }
}
