package com.codecool.shop.model;

import java.util.ArrayList;

public class ProductCategory extends BaseModel {

    private static int currentId = 0;

    private String department;
    private ArrayList<Product> products;

    public ProductCategory(String name, String department, String description) {
        super(name);
        this.id = currentId;
        currentId++;
        this.description = description;
        this.products = new ArrayList<>();
        this.department = department;
    }

    public ProductCategory(int id, String name, String department, String description) {
        this(name, description, department);
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public String toString() {
        return String.format(
                "id: %1$d," +
                        "name: %2$s, " +
                        "department: %3$s, " +
                        "description: %4$s",
                this.id,
                this.name,
                this.department,
                this.description);
    }
}