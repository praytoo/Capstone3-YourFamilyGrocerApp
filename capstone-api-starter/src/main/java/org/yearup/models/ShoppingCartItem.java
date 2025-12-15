package org.yearup.models;

import java.math.BigDecimal;

public class ShoppingCartItem
{
    private Product product;
    private int quantity = 1;
    private BigDecimal discountPercent = BigDecimal.ZERO;
    private BigDecimal price;

    public ShoppingCartItem(Product product, int quantity, BigDecimal discountPercent, BigDecimal price) {
        this.product = product;
        this.quantity = quantity;
        this.discountPercent = discountPercent;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ShoppingCartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public BigDecimal getDiscountPercent()
    {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent)
    {
        this.discountPercent = discountPercent;
    }

    public int getProductId() {
        return this.product.getProductId();
    }

    public void setProductId(){
        this.product = product;
    }

    public BigDecimal getLineTotal()
    {
        BigDecimal basePrice = product.getPrice();
        BigDecimal quantity = new BigDecimal(this.quantity);

        BigDecimal subTotal = basePrice.multiply(quantity);
        BigDecimal discountAmount = subTotal.multiply(discountPercent);

        return subTotal.subtract(discountAmount);
    }
}
