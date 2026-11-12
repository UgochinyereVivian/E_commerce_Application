package customer;

import payment.Payment;
import payment.PaymentGateway;
import shopping.Cart;
import shopping.Checkout;
import shopping.Product;
import shopping.ProductInventory;

import java.math.BigDecimal;
import java.util.Map;

public class Customer {

    ProductInventory inventory;
    Cart cart;
    private Checkout checkout;

    public Customer(ProductInventory inventory, Cart cart, Checkout checkout){
        validateProductInventory(inventory);
        this.inventory = inventory;
        this.cart = cart;
        this.checkout = checkout;
    }
    public Map<Product, Integer> viewProducts() {
        return  inventory.viewAvailableGoods();
    }


    public Map.Entry<Product, Integer> searchProduct(String name) throws Exception {
        return inventory.searchProduct(name);
    }

    public void addToCart(String name, int quantity) throws Exception {

        Map.Entry<Product, Integer> productEntry = inventory.searchProduct(name);

        Product product = productEntry.getKey();
        int availableQuantity = productEntry.getValue();

        if (quantity > availableQuantity) throw new Exception("requested quantity exceeds available stock");

        cart.addToCart(product, quantity);
        inventory.updateProductQuantity(name, availableQuantity - quantity);
    }

    public Map<Product, Integer> viewCart() {
        return cart.viewCart();
    }

    public Map.Entry<Map<Product, Integer>, BigDecimal> checkout() throws Exception {
        return checkout.checkout();
    }
    public void pay(Payment payment, PaymentGateway paymentGateway) throws Exception {

        if (payment == null) throw new IllegalArgumentException("payment cannot be null");
        if (paymentGateway == null) throw new IllegalArgumentException("payment gateway cannot be null");

        Map.Entry<Map<Product, Integer>, BigDecimal> invoice = checkout.checkout();

        payment.makePayment(invoice.getValue());
    }

    public void removeFromCart(String name) throws Exception {
        int quantityToRestore = 0;

        for (Map.Entry<Product, Integer> entry : cart.viewCart().entrySet()) {
            if (entry.getKey().getProductName().equalsIgnoreCase(name)) {
                quantityToRestore = entry.getValue();
                break;
            }
        }
        Map.Entry<Product, Integer> inventoryProduct = inventory.searchProduct(name);

        cart.removeFromCart(name);

        inventory.updateProductQuantity(name, inventoryProduct.getValue() + quantityToRestore);
    }

    private void validateProductInventory(ProductInventory productInventory) {
        if (productInventory == null) throw new IllegalArgumentException("product inventory cannot be null");
    }

}
