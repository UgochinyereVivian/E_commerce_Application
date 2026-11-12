package management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shopping.Product;
import shopping.ProductInventory;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    ProductInventory inventory;
    Manager manager;

    @BeforeEach
    public void startWith(){
         inventory = new ProductInventory();
         manager = new Manager(inventory);
    }

    @Test
    public void testConstructorThrowsExceptionWhenProductInventoryIsNull() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Manager(null)
        );

        assertEquals("product inventory cannot be null", exception.getMessage());
    }

    @Test
    public void testAddNewStock() throws Exception {

        manager.addNewStock("Biscuit", 500, 10);
        Map<Product, Integer> products = manager.viewAllProducts();

        assertEquals(1, products.size());

        Product product = products.keySet().iterator().next();

        assertEquals("Biscuit", product.getProductName());
        assertEquals(new BigDecimal("500.0"), product.getPrice());
        assertEquals(10, products.get(product));
    }

    @Test
    public void testAddNewStockThrowsExceptionWhenProductAlreadyExists() throws Exception {

        manager.addNewStock("Biscuit", 500, 10);

        Exception exception = assertThrows(
                Exception.class,
                () -> manager.addNewStock("Biscuit", 500, 5)
        );

        assertEquals(
                "product exist in stock, you can update product quantity",
                exception.getMessage()
        );

        assertEquals(1, manager.viewAllProducts().size());
    }

    @Test
    public void testViewAllProductsReturnsAllProducts() throws Exception {

        manager.addNewStock("Biscuit", 500, 10);
        manager.addNewStock("Milk", 800, 5);

        Map<Product, Integer> products = manager.viewAllProducts();

        assertEquals(Map.of(new Product("Biscuit", 500), 10,
                        new Product("Milk", 800),
                        5), products
        );
    }

    @Test
    public void testUpdateStockQuantity() throws Exception {
        manager.addNewStock("Biscuit", 500, 10);

        manager.updateStockQuantity("Biscuit", 20);

        Product product = manager.viewAllProducts().keySet().iterator().next();

        assertEquals(20, manager.viewAllProducts().get(product));
    }

    @Test
    public void testUpdateStockQuantityThrowsExceptionWhenProductDoesNotExist() {
        Exception exception = assertThrows(
                Exception.class,
                () -> manager.updateStockQuantity("Biscuit", 20)
        );

        assertEquals("product not in stock", exception.getMessage());
    }

    @Test
    public void testUpdateProductPrice() throws Exception {
        manager.addNewStock("Biscuit", 500, 10);

        manager.updateProductPrice("Biscuit", 750);

        Product product = manager.viewAllProducts().keySet().iterator().next();

        assertEquals(new BigDecimal("750.0"), product.getPrice());
    }

    @Test
    public void testUpdateProductPriceThrowsExceptionWhenProductDoesNotExist() {
        Exception exception = assertThrows(
                Exception.class,
                () -> manager.updateProductPrice("Biscuit", 750)
        );

        assertEquals("product not in stock", exception.getMessage());
    }
}
