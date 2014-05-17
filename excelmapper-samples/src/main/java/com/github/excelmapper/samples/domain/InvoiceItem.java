package com.github.excelmapper.samples.domain;

/**
 * User: Dmitry Levin
 * Date: 16.05.14
 */
public class InvoiceItem {

    private Product product;

    private float price;

    private int count;

    public InvoiceItem() {
    }

    public InvoiceItem(Product product, float price, int count) {
        this.product = product;
        this.price = price;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvoiceItem{");
        sb.append("product=").append(product);
        sb.append(", price=").append(price);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
