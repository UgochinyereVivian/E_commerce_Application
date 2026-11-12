package shopping;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String name;
    private BigDecimal price;

    public Product(String name, double price) throws Exception {
        this.name = name;
        validateProductPrice(price);
        updateProductPrice(price);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void updateProductPrice(double price) throws Exception {
        validateProductPrice(price);
        this.price = BigDecimal.valueOf(price);
    }

    public String getProductName(){
        return name;
    }


    private void validateProductPrice(double amount) throws Exception {
        if (amount < 0) throw new Exception("negative price is not allowed");

        if (amount == 0) throw new Exception("price must be greater than zero");

        if (Double.isNaN(amount) || Double.isInfinite(amount)) throw new Exception("invalid product price");
    }

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null || this.getClass() != object.getClass()) return false;

        Product product = (Product) object;
        return Objects.equals(this.name.toLowerCase(), product.name.toLowerCase());
    }

    @Override
    public int hashCode(){
        return Objects.hash(name.toLowerCase());
    }
}
