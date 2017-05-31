package com.codecool.shop.model;



import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoWithJdbc;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class contains methods which are modifies or read the content
 * of the order of the user.
 */
public class Order {

    private static int currentId = 0;
    private static final Logger logger = LoggerFactory.getLogger(Order.class);

    private int id;
    private Set<Lineitem> orderLines = new LinkedHashSet<>();
    private int totalQuantity = 0;
    private double total = 0;

    /**
     * This is the constructor. It uses the static {@code currentId} as the
     * id of the instance and than increment it so every order has a
     * different id.
     * <b>Note:</b> This is bad practice as database should handle the id.
     */
    public Order(){
        this.id = currentId;
        currentId++;
    }

    /**
     * This method gets the {@code Order} instance of a current session
     * or create a new {@code Order} instance if it doesn't created yet.
     * @param request A Spark request
     * @return An {@code Order} instance from the request session.
     */
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

    /**
     * This method gets one {@code Lineitem} instance from the {@code Order}
     * based on its id.
     * @param id The id attribute of a {@code Lineitem} instance
     * @return {@code Lineitem} instance
     */
    public Lineitem getLine(int id)  {
        return orderLines.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    /**
     * This method increments the total price of the {@code Order}.
     * @param total A float to add to the price (The {@code linePrice}
     *  attribute of a {@code Lineitem})
     */
    public void addToTotal(float total) {
        this.total += total;
    }

    /**
     * This method just left here, we dont use it lol.
     * @param total The {@code quantity} attribute of a {@code Lineitem} instance.
     */
    public void addToTotalQuantity(int total){
        totalQuantity += total;
    }

    public void subFromTotal(float total) {
        this.total -= total;
    }

    /**
     * This method adds a new {@code Lineitem} to the {@code Order}.
     * It also increase the {@code total} price and the {@code totalQuantity}
     * attribute of the {@code Order}.
     * @param line A {@code Lineitem} instance
     * @return a boolean but we don't use the value of it
     */
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

    /**
     * This method creates a new Lineitem when a Product is added to cart.
     * @param id The id of a Product instance
     */
    public void addItem(int id){
        ProductDao productDataStore = ProductDaoWithJdbc.getInstance();
        Product itemToAdd = productDataStore.find(id);
        Lineitem line = new Lineitem(itemToAdd);
        logger.debug("Item added: {}", itemToAdd);
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
