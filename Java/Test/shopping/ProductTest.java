package shopping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product product;

    @BeforeEach
    public void setUp() throws Exception {
        product = new Product("biscuit", 500.00);
    }

    @Test
    public void testGetProductNameReturnsProductName() {
        assertEquals("biscuit", product.getProductName());
    }

    @Test
    public void testGetPriceReturnsProductPrice() {
        assertEquals(new BigDecimal("500.0"), product.getPrice());
    }

    @Test
    public void testProductsWithSameNameAreEqual() throws Exception {
        Product otherProduct = new Product("BiScuit", 700.00);

        assertEquals(product, otherProduct);
    }

    @Test
    public void testProductsWithDifferentNamesAreNotEqual() throws Exception {
        Product otherProduct = new Product("milk", 500.00);

        assertNotEquals(product, otherProduct);
    }

    @Test
    public void testProductIsEqualToItself() {
        assertEquals(product, product);
    }

    @Test
    public void testProductIsNotEqualToNull() {
        assertNotEquals(product, null);
    }

    @Test
    public void testProductsWithSameNameHaveSameHash() throws Exception {
        Product otherProduct = new Product("biscuit", 700.00);

        assertEquals(product.hashCode(), otherProduct.hashCode());
    }

    @Test
    public void testUpdateProductPriceUpdatesProductPrice() throws Exception {
        product.updateProductPrice(700.00);

        assertEquals(new BigDecimal("700.0"), product.getPrice());
    }

    @Test
    public void testUpdateProductPriceThrowsExceptionIfPriceIsZero() {
        Exception exception = assertThrows(
                Exception.class,
                () -> product.updateProductPrice(0)
        );

        assertEquals("price must be greater than zero", exception.getMessage());
    }

    @Test
    public void testUpdateProductPriceThrowsExceptionIfPriceIsNegative() {
        Exception exception = assertThrows(
                Exception.class,
                () -> product.updateProductPrice(-100.00)
        );

        assertEquals("negative price is not allowed", exception.getMessage());
    }

    @Test
    public void testUpdateProductPriceThrowsExceptionIfPriceIsNaN() {
        Exception exception = assertThrows(
                Exception.class,
                () -> product.updateProductPrice(Double.NaN)
        );

        assertEquals("invalid product price", exception.getMessage());
    }

    @Test
    public void testUpdateProductPriceThrowsExceptionIfPriceIsInfinite() {
        Exception exception = assertThrows(
                Exception.class,
                () -> product.updateProductPrice(Double.POSITIVE_INFINITY)
        );

        assertEquals("invalid product price", exception.getMessage());
    }

    @Test
    public void testConstructorThrowsExceptionIfPriceIsZero() {
        Exception exception = assertThrows(
                Exception.class,
                () -> new Product("biscuit", 0)
        );

        assertEquals("price must be greater than zero", exception.getMessage());
    }

    @Test
    public void testConstructorThrowsExceptionIfPriceIsNegative() {
        Exception exception = assertThrows(
                Exception.class,
                () -> new Product("biscuit", -100.00)
        );

        assertEquals("negative price is not allowed", exception.getMessage());
    }
}