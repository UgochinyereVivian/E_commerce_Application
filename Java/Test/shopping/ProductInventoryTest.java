package shopping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductInventoryTest {
    ProductInventory inventory;

    @BeforeEach
    public void startWith(){
        inventory = new ProductInventory();
    }

    @Test
    public void testThatAvailableStockIsAccuratelyDisplayed() throws Exception {
        Map<Product,Integer> result = inventory.viewAvailableGoods();
        assertEquals(Map.of(),result);

        inventory.addStock("biscuit", 500.00,10);

        Product product = result.keySet().iterator().next();

        assertEquals(Map.of(new Product("biscuit", 500.00),10),result);
        assertEquals(0, product.getPrice().compareTo(new BigDecimal("500.00")));
    }

    @Test
    public void testThatViewAvailableStockDoesNotAllowStockModification(){
        Map<Product,Integer> result = inventory.viewAvailableGoods();
        assertEquals(Map.of(),result);

        assertThrows(UnsupportedOperationException.class, ()-> result.put(new Product("biscuit", 500.00), 10));
        assertEquals(Map.of(),result);

    }

    @Test
    public void testAddStockAddsProductAndQuantity() throws Exception {
        Map<Product,Integer> result = inventory.viewAvailableGoods();
        assertEquals(Map.of(),result);

        inventory.addStock("biscuit",500.00,10);
        assertEquals(Map.of(new Product("biscuit", 500.00),10),result);

    }

    @Test
    public void testThatIfStockExistInInventory_StockIsNotAdded_ExceptionIsThrown()throws Exception{

        inventory.addStock("biscuit",500.00,10);
        assertEquals(1,inventory.viewAvailableGoods().size());

        assertThrows(Exception.class, () -> inventory.addStock("biscuit",  500.00, 10));
    }

    @Test
    public void testThatIfAddStockIsTriggeredAndStockExistInInventory_ExceptionMessageIsShown()throws Exception{
        Map<Product,Integer> result = inventory.viewAvailableGoods();
        assertEquals(Map.of(),result);

        inventory.addStock("biscuit", 500.00,10);
        assertEquals(1,inventory.viewAvailableGoods().size());

        Exception exception = assertThrows(Exception.class, () -> inventory.addStock("biscuit", 500.00, 10));
        assertEquals("product exist in stock, you can update product quantity", exception.getMessage());
    }

    @Test
    public void testUpdateProductQuantityUpdatesExistingProduct() throws Exception {
        inventory.addStock("biscuit", 500.00, 10);

        inventory.updateProductQuantity("biscuit", 20);

        Map<Product, Integer> result = inventory.viewAvailableGoods();

        assertEquals(20, result.get(new Product("biscuit", 500.00)));
    }


    @Test
    public void testThatIfProductDoesNotExistInInventory_ExceptionIsThrown() throws Exception {
        inventory.addStock("biscuit",500.00, 10);

        assertThrows(Exception.class, () -> inventory.updateProductQuantity("rice", 20));
    }

    @Test
    public void testThatIfProductDoesNotExistInInventory_ExceptionMessageIsShown() throws Exception {
        inventory.addStock("biscuit", 500.00, 10);

        Exception exception = assertThrows(Exception.class,
                () -> inventory.updateProductQuantity("rice", 20));

        assertEquals("product not in stock", exception.getMessage());
    }

    @Test
    public void testRemoveProductRemovesProductFromStock() throws Exception {
        Map<Product,Integer> result = inventory.viewAvailableGoods();
        inventory.addStock("biscuit", 500.00,10);
        assertEquals(Map.of(new Product("biscuit", 500.00),10),result);

        inventory.removeProduct("biscuit");
        assertEquals(Map.of(),result);

    }

    @Test
    public void testRemoveProductThrowsExceptionIfProductIsNotInStock() throws Exception {
        Map<Product,Integer> result = inventory.viewAvailableGoods();
        inventory.addStock("biscuit", 500.00,10);

        assertThrows(Exception.class, () -> inventory.removeProduct("milk"));

    }

    @Test
    public void testRemoveProductThrowsExceptionMessage() throws Exception {
        Map<Product,Integer> result = inventory.viewAvailableGoods();
        inventory.addStock("biscuit", 500.00,10);

        Exception exception = assertThrows(Exception.class, () -> inventory.removeProduct("milk"));
        assertEquals("product not in stock",exception.getMessage());

    }

    @Test
    public void testChangeProductPriceChangesExistingProductPrice() throws Exception {
        inventory.addStock("biscuit", 500.00, 10);

        inventory.changeProductPrice("biscuit", 700);

        Map<Product, Integer> result = inventory.viewAvailableGoods();
        Product product = result.keySet().iterator().next();

        assertEquals(0, BigDecimal.valueOf(700.0).compareTo(product.getPrice())
        );
    }

    @Test
    public void testThatUpdatePriceThrowsExceptionIfProductDoesNotExistInInventory() throws Exception {
        inventory.addStock("biscuit", 500.00, 10);

        assertThrows(Exception.class,
                () -> inventory.changeProductPrice("rice", 700));
    }

    @Test
    public void testThatIfProductDoesNotExistInInventory_UpdatePriceShowsExceptionMessage() throws Exception {
        inventory.addStock("biscuit", 500.00, 10);

        Exception exception = assertThrows(Exception.class,
                () -> inventory.changeProductPrice("rice", 700));

        assertEquals("product not in stock", exception.getMessage());
    }

    @Test
    public void testSearchProduct() throws Exception {
        inventory.addStock("biscuit", 500.00, 10);
        Map.Entry<Product, Integer> productSearched = inventory.searchProduct("Biscuit");
        assertEquals(Map.entry(new Product("biscuit", 500.0), 10), productSearched);
        assertEquals(0,productSearched.getKey().getPrice().compareTo(new BigDecimal("500.00")));
    }

    @Test
    public void testSearchProductThrowsExceptionWhenProductDoesNotExist() throws Exception {
        inventory.addStock("biscuit", 500.00, 10);

        Exception exception = assertThrows(Exception.class, () -> {
            inventory.searchProduct("Milk");
        });

        assertEquals("product not in stock", exception.getMessage());
    }

}
