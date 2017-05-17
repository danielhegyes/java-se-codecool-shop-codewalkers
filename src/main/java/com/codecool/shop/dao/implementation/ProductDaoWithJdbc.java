package com.codecool.shop.dao.implementation;/*
Created by tahin on 2017.05.16..
*/

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.List;

public class ProductDaoWithJdbc implements ProductDao {

    private static ProductDaoWithJdbc instance = null;

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "gombaspeteer";
    private static final String DB_PASSWORD = "187818851953";

    public static ProductDaoWithJdbc getInstance() {
        if (instance == null) {
            instance = new ProductDaoWithJdbc();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        String query = "INSERT INTO product (ID,NAME,description, default_price, default_currency, product_category, supplier)\n" +
                "VALUES (" + product.getId() + ", '" + product.getName() + "' ,'" + product.getDescription() + "', " + product.getDefaultPrice() + ", '" + product.getDefaultCurrency() + "',\n" +
                "        (select id from product_category where name = '" + product.getProductCategory().getName() + "'), (select id from supplier where name = '" + product.getSupplier().getName() + "'));";
        executeQuery(query);
    }

    @Override
    public Product find(int id) {
        String query = "SELECT * FROM product WHERE id ='" + id + "';";
        ProductCategoryDaoJDBC prodJdbc = ProductCategoryDaoJDBC.getInstance();
        SupplierDaoJDBC supJdbc = SupplierDaoJDBC.getInstance();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                Product result = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("default_currency"),
                        resultSet.getString("description"),
                        prodJdbc.find(resultSet.getInt("product_category")),
                        supJdbc.find(resultSet.getInt("supplier")));
                return result;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public void remove(int id) {
        
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    private void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
        ) {
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "A smartphone is a cellular telephone with an integrated computer and other features not originally associated with telephones, such as an operating system, Web browsing and the ability to run software applications.");
        Supplier lenovo = new Supplier("Lenovo", "Computers");
     ProductDaoWithJdbc pdwj = ProductDaoWithJdbc.getInstance();
        Product prod = new Product("Lenovo Laptop",380,"USD", "Great laptop!!!", phone, lenovo);
        Product prod2 = new Product("Lenovo Laptop",380,"USD", "Great laptop!!!", phone, lenovo);
//        pdwj.add(prod);
//        pdwj.add(prod2);
        System.out.println(pdwj.find(11));
        System.out.println(prod);
    }

}


