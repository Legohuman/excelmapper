package com.github.excelmapper.samples.domain;

/**
 * User: Dmitry Levin
 * Date: 16.05.14
 */
public class Product {

    private String art;

    private ProductColor color;

    private ProductMaterial material;

    public Product() {
    }

    public Product(String art, ProductColor color, ProductMaterial material) {
        this.art = art;
        this.color = color;
        this.material = material;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public ProductColor getColor() {
        return color;
    }

    public void setColor(ProductColor color) {
        this.color = color;
    }

    public ProductMaterial getMaterial() {
        return material;
    }

    public void setMaterial(ProductMaterial material) {
        this.material = material;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("art='").append(art).append('\'');
        sb.append(", color=").append(color);
        sb.append(", material=").append(material);
        sb.append('}');
        return sb.toString();
    }
}

