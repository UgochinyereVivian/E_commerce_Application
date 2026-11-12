package customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shopping.*;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;

public class CustomerTest {
    Customer customer;
    ProductInventory inventory;
    Cart cart;
    Checkout checkout;


    @BeforeEach
    public void startWith(){
        inventory = new ProductInventory();
        cart = new Cart();
        this.checkout = new Checkout(cart);
        customer = new Customer(inventory, cart,checkout);

    }

    @Test
    public void testCustomerCannotBeCreatedWithNullInventory() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Customer(null, cart,checkout)
        );
    }

    @Test
    public void testViewProductsReturnsAllProductsInStock()throws Exception{
            inventory.addStock("Biscuit", 500, 10);
            inventory.addStock("Milk", 800, 5);

            Map<Product, Integer> products = customer.viewProducts();

            assertEquals(inventory.viewAvailableGoods().size(),products.size());
            assertEquals(Map.of(new Product("Biscuit", 500), 10, new Product("Milk", 800), 5), products);
    }


    @Test
    public void testSearchProduct()throws Exception{
        inventory.addStock("Biscuit",500.0,10);
        Map.Entry<Product, Integer> products = customer.searchProduct("biscuit");

        assertEquals(Map.entry(new Product("Biscuit", 500.0), 10), products);
        assertEquals(0,products.getKey().getPrice().compareTo(new BigDecimal("500.0")));
    }

    @Test
    public void testSearchProductThrowsExceptionWhenProductDoesNotExist() throws Exception {
        inventory.addStock("Biscuit", 500.0, 10);

        Exception exception = assertThrows(Exception.class, () -> customer.searchProduct("Milk"));

        assertEquals("product not in stock", exception.getMessage());
    }

    @Test
    public void testAddToCartAddsProductToCart() throws Exception {
        inventory.addStock("Biscuit", 500.0, 10);

        customer.addToCart("Biscuit", 3);

        assertEquals(Map.of(new Product("Biscuit", 500.0), 3),customer.viewCart());
    }


    @Test
    public void testAddToCartCausesStockQuantityReductionInInventory() throws Exception {
        inventory.addStock("Biscuit", 500.0, 10);

        customer.addToCart("Biscuit", 3);

        assertEquals(7,inventory.viewAvailableGoods().get(new Product("Biscuit", 500.0)));
    }



    @Test
    public void testAddToCartThrowsExceptionWhenQuantityExceedsAvailableStock() throws Exception {
        inventory.addStock("Biscuit", 500.0, 10);
        Exception exception = assertThrows(
                Exception.class,
                () -> customer.addToCart("Biscuit", 15)
        );

        assertEquals(
                "requested quantity exceeds available stock",
                exception.getMessage()
        );

        assertTrue(customer.viewCart().isEmpty());
    }

    @Test
    public void testViewCartReturnsProductsAddedToCart() throws Exception {
        inventory.addStock("Biscuit", 500.0, 10);
        inventory.addStock("Milk", 500.0, 10);

        customer.addToCart("Biscuit", 3);
        customer.addToCart("Milk", 2);

        Map<Product, Integer> products = customer.viewCart();

        assertEquals(2, products.size());
        assertEquals(Map.of(new Product("Biscuit", 500), 3, new Product("Milk", 800), 2), products);
    }

    @Test
    public void testCheckoutReturnsCartContentsAndTotal() throws Exception {
        inventory.addStock("Biscuit", 500.0, 10);
        inventory.addStock("Milk", 500.0, 10);

        customer.addToCart("Biscuit", 2);
        customer.addToCart("Milk", 1);

        Map.Entry<Map<Product, Integer>, BigDecimal> invoice = customer.checkout();

        assertEquals(2, invoice.getKey().size());
        assertEquals(Map.of(new Product("Biscuit", 500.0),2,new Product("Milk", 500.0),1), invoice.getKey());

        assertEquals(0, invoice.getValue().compareTo(new BigDecimal("1500.00")));
    }

    @Test
    public void testRemoveFromCartRemovesProduct() throws Exception {
        inventory.addStock("Biscuit", 500.0, 10);
        customer.addToCart("Biscuit", 2);

        customer.removeFromCart("Biscuit");

        assertTrue(customer.viewCart().isEmpty());
    }

    @Test
    public void testRemoveFromCartThrowsExceptionWhenProductDoesNotExist() throws Exception {
        inventory.addStock("Biscuit", 500.0, 10);
        Exception exception = assertThrows(
                Exception.class,
                () -> customer.removeFromCart("Bread")
        );

        assertEquals("product not in stock", exception.getMessage());
    }

//    @Test
//    public void testPayThrowsExceptionWhenPaymentGatewayIsNull() throws Exception {
//
//        assertThrows(
//                IllegalArgumentException.class,
//                () -> customer.pay(null)
//        );
//    }
}
