package com.codecool.shop.model;



/**
 * Created by tahin on 2017.05.02..
 */
public class Lineitem {

    private int id;
    private Product product;
    private int quantity = 1;
    private float linePrice;

    public Lineitem(Product product){
        this.product = product;
        this.linePrice = product.getDefaultPrice();
    }

    public float getLinePrice() {
        return linePrice;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Lineitem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", linePrice=" + linePrice +
                '}';
    }

    public void addOneToQuantity(){
        this.quantity += 1;
        this.linePrice += this.product.getDefaultPrice();
    }

    public void subOneFromQuantity(){

        if (this.quantity == 1) {
            this.quantity = 1;
        } else {
            this.quantity -= 1;
            this.linePrice -= this.product.getDefaultPrice();
        }
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
