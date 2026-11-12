package shopping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartTest {

    private ProductInventory inventory;
    private Cart cart;
    private Product biscuit;
    private Product milk;

    @BeforeEach
    public void setUp() throws Exception {
        inventory = new ProductInventory();
        cart = new Cart();

        inventory.addStock("Biscuit", 500.00, 10);
        inventory.addStock("Milk", 800.00, 5);

        biscuit = inventory.searchProduct("Biscuit").getKey();
        milk = inventory.searchProduct("Milk").getKey();
    }

    @Test
    public void testAddToCart() {
        cart.addToCart(biscuit, 3);

        assertEquals(Map.of(biscuit, 3), cart.viewCart());
    }

    @Test
    public void testAddSameProductToCartIncreasesQuantity() {

        cart.addToCart(biscuit, 3);
        cart.addToCart(biscuit, 2);

        assertEquals(Map.of(biscuit, 5), cart.viewCart());
    }

    @Test
    public void testAddToCartThrowsExceptionWhenQuantityIsZero() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> cart.addToCart(biscuit, 0));

        assertEquals("quantity must be greater than zero", exception.getMessage());

        assertTrue(cart.viewCart().isEmpty());
    }

    @Test
    public void testAddToCartThrowsExceptionWhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> cart.addToCart(biscuit, -2));
        assertTrue(cart.viewCart().isEmpty());
    }

    @Test
    public void testViewCartReturnsAllProducts() {
        cart.addToCart(biscuit, 3);
        cart.addToCart(milk, 2);

        Map<Product, Integer> products = cart.viewCart();

        assertEquals(Map.of(biscuit, 3, milk, 2), products);
    }

    @Test
    public void testViewCartCannotBeModified() {

        cart.addToCart(biscuit, 3);

        assertThrows(UnsupportedOperationException.class, () -> cart.viewCart().put(biscuit, 10));
    }

    @Test
    public void testRemoveFromCart() throws Exception {

        cart.addToCart(biscuit, 3);

        cart.removeFromCart("Biscuit");

        assertTrue(cart.viewCart().isEmpty());
    }

    @Test
    public void testRemoveFromCartThrowsExceptionWhenProductDoesNotExist() {

        cart.addToCart(biscuit, 3);

        Exception exception = assertThrows(
                Exception.class,
                () -> cart.removeFromCart("Milk")
        );

        assertEquals("product not found in cart", exception.getMessage());
    }

    @Test
    public void testRemoveFromCartIgnoresProductNameCase()throws Exception {

        cart.addToCart(biscuit, 3);

        cart.removeFromCart("bISCUIT");

        assertTrue(cart.viewCart().isEmpty());
    }
}
