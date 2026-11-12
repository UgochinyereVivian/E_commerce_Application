package shopping;

import java.math.BigDecimal;
import java.util.Map;

public class Checkout {

    private final Cart cart;

    public Checkout(Cart cart) {
        validateCart(cart);
        this.cart = cart;
    }

    public Map.Entry<Map<Product, Integer>, BigDecimal> checkout() throws Exception {
        if (cart.viewCart().isEmpty()) throw new Exception("cart is empty");

        Map<Product, Integer> products = cart.viewCart();
        BigDecimal total = calculateTotal();

        return Map.entry(products, total);
    }


    private BigDecimal calculateTotal(){
        Map<Product, Integer> products = cart.viewCart();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            BigDecimal price = entry.getKey().getPrice();
            int quantity = entry.getValue();

            total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
        }

        return total;
    }

    private void validateCart(Cart cart){
        if(cart == null) throw new IllegalArgumentException("cart cant be null");
    }


}
