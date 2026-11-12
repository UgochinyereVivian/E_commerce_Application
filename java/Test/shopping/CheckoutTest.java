package shopping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckoutTest {

    private ProductInventory inventory;
    private Cart cart;
    private Checkout checkout;
    private Product biscuit;
    private Product milk;

    @BeforeEach
    public void setUp() throws Exception {
        inventory = new ProductInventory();
        cart = new Cart();
        checkout = new Checkout(cart);

        inventory.addStock("Biscuit", 500.00, 10);
        inventory.addStock("Milk", 800.00, 5);

        biscuit = inventory.searchProduct("Biscuit").getKey();
        milk = inventory.searchProduct("Milk").getKey();
    }

    @Test
    public void testCheckoutReturnsCartContentsAndTotal() throws Exception {

        cart.addToCart(biscuit, 2);
        cart.addToCart(milk, 1);

        Map.Entry<Map<Product, Integer>, BigDecimal> invoice = checkout.checkout();

        assertEquals(Map.of(biscuit, 2, milk, 1), invoice.getKey());

        assertEquals(0, invoice.getValue().compareTo(new BigDecimal("1800.00")));
    }

    @Test
    public void testCheckoutCalculatesTotalForMultipleQuantities() throws Exception {

        cart.addToCart(biscuit, 5);
        cart.addToCart(milk, 3);

        Map.Entry<Map<Product, Integer>, BigDecimal> invoice = checkout.checkout();

        assertEquals(0, invoice.getValue().compareTo(new BigDecimal("4900.00")));
    }


    @Test
    public void testCheckoutThrowsExceptionWhenCartIsEmpty() {

        Exception exception = assertThrows(
                Exception.class,
                () -> checkout.checkout()
        );

        assertEquals("cart is empty", exception.getMessage());
    }

    @Test
    public void testCheckoutCannotBeCreatedWithNullCart() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Checkout(null)
        );
    }
}
