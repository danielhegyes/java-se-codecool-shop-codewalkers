package com.codecool.shop.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tahin on 2017.05.02..
 */
public class Order {

    private static int currentId = 0;

    private int id;
    private Set<Lineitem> orderLines = new LinkedHashSet<>();
    private float total = 0;


    public Order(){
        this.id = currentId;
        currentId++;
    }

    public Set<Lineitem> getOrderLines() {
        return orderLines;
    }

    public void addLine(Lineitem line){
        this.orderLines.add(line);
        this.total += line.getLinePrice();
    }

    public int getId() {
        return id;
    }

    public float getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderLines=" + orderLines +
                ", total=" + total +
                '}';
    }
}
