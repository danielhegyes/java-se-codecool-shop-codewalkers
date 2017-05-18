package com.codecool.shop.model;



import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoWithJdbc;
import spark.Request;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tahin on 2017.05.02..
 */
public class Order {

    private static int currentId = 0;

    private int id;
    private Set<Lineitem> orderLines = new LinkedHashSet<>();
    private int totalQuantity = 0;
    private double total = 0;


    public Order(){
        this.id = currentId;
        currentId++;
    }

    public static Order getOrder(Request request){
        Order myOrder;
        if (request.session().attribute("order") != null){
            myOrder = request.session().attribute("order");
        } else {
            myOrder = new Order();
            request.session().attribute("order", myOrder);
        }
        return myOrder;
    }

    public Set<Lineitem> getOrderLines() {
        return orderLines;
    }

    public Lineitem getLine(int id)  {
        return orderLines.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public void addToTotal(float total) {
        this.total += total;
    }
    public void addToTotalQuantity(int total){
        totalQuantity += total;
    }

    public void subFromTotal(float total) {
        this.total -= total;
    }

    public boolean addLine(Lineitem line){
        for (Lineitem l: orderLines){
            if (line.getProduct().equals(l.getProduct())){
                return false;
            }
        }
        orderLines.add(line);
        total += line.getLinePrice();
        totalQuantity += line.getQuantity();
        return true;
    }

    public void removeLine (Lineitem line) {
        orderLines.remove(line);
        totalQuantity -= line.getQuantity();
    }

    public int getTotalQuantity(){
        return  totalQuantity;
    }

    public void addItem(int id){
        ProductDao productDataStore = ProductDaoWithJdbc.getInstance();
        Lineitem line = new Lineitem(productDataStore.find(id));
        addLine(line);
    }

    public int getId() {
        return id;
    }

    public double getTotal() {
        return Math.round(total * 100d) / 100d;
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
