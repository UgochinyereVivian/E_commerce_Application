package shopping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private final Map<Product, Integer> cartItems = new HashMap<>();

    public void addToCart(Product product, int quantity) {

        if (quantity <= 0) throw new IllegalArgumentException("quantity must be greater than zero");
        cartItems.merge(product, quantity, Integer::sum);
    }

    public Map<Product, Integer> viewCart() {
        return Collections.unmodifiableMap(cartItems);
    }


    public void removeFromCart(String name) throws Exception {
        for (Product product : cartItems.keySet()) {
            if (product.getProductName().equalsIgnoreCase(name)) {
                cartItems.remove(product);
                return;
            }
        }

        throw new Exception("product not found in cart");
    }


}
