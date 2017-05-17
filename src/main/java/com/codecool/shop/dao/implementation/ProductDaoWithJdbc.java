package com.codecool.shop.dao.implementation;/*
Created by tahin on 2017.05.16..
*/

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoWithJdbc implements ProductDao {

    private static ProductDaoWithJdbc instance = null;

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = readConfigFile().get(0);
    private static final String DB_PASSWORD = readConfigFile().get(1);

    private ProductCategoryDaoJDBC prodCatDaoJDBC = ProductCategoryDaoJDBC.getInstance();
    private SupplierDaoJDBC supplierDaoJDBC = SupplierDaoJDBC.getInstance();

    private static List<String> readConfigFile() {
        try {
            return Files.readAllLines(Paths.get("src/dbConfig.txt"), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Config file not found");
        }
        return null;
    }


    public static ProductDaoWithJdbc getInstance() {
        if (instance == null) {
            instance = new ProductDaoWithJdbc();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        String query = "INSERT INTO product (ID," +
                                            "NAME," +
                                            "description, " +
                                            "default_price, " +
                                            "default_currency, " +
                                            "product_category, " +
                                            "supplier)" +
                        "VALUES (" + product.getId() + "," +
                                "'" + product.getName() + "' ," +
                                "'" + product.getDescription() + "'," +
                                "" + product.getDefaultPrice() + "," +
                                "'" + product.getDefaultCurrency() + "'," +
                                "(select id from product_category where name = '" + product.getProductCategory().getName() + "'), " +
                                "(select id from supplier where name = '" + product.getSupplier().getName() + "'));";
        executeQuery(query);
    }

    @Override
    public Product find(int id) {

        String query = "SELECT * FROM product WHERE id ='" + id + "';";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            if (resultSet.next()) {
                return instantiateProductFromQuery(resultSet);
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
        String query = "DELETE FROM product WHERE id="+ id +";";
        executeQuery(query);
    }

    @Override
    public List<Product> getAll() {

        String query = "SELECT * FROM product";
        return makeListFromResultSet(query);
    }

    @Override
    public List<Product> getBy(Supplier supplier) {

        String query = "SELECT * FROM product WHERE supplier =" + supplier.getId() + ";";
        return makeListFromResultSet(query);
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {

        String query = "SELECT * FROM product WHERE product_category =" + productCategory.getId() + ";";
        return makeListFromResultSet(query);
    }

    public Product instantiateProductFromQuery(ResultSet resultSet) throws SQLException {
        Product result = new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getFloat("default_price"),
                resultSet.getString("default_currency"),
                resultSet.getString("description"),
                prodCatDaoJDBC.find(resultSet.getInt("product_category")),
                supplierDaoJDBC.find(resultSet.getInt("supplier")));
        return result;
    }

    public List<Product> makeListFromResultSet(String query){
        List<Product> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()){
                Product result = instantiateProductFromQuery(resultSet);
                resultList.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
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
//        ProductCategory phone = new ProductCategory("Phone", "Hardware", "A smartphone is a cellular telephone with an integrated computer and other features not originally associated with telephones, such as an operating system, Web browsing and the ability to run software applications.");
//        Supplier lenovo = new Supplier("Lenovo", "Computers");
//        Product prod = new Product("Lenovo Laptop",380,"USD", "Great laptop!!!", phone, lenovo);
//        Product prod2 = new Product("Lenovo Laptop",380,"USD", "Great laptop!!!", phone, lenovo);
        ProductDaoWithJdbc prodJdbc = ProductDaoWithJdbc.getInstance();
        SupplierDaoJDBC suppJdbc = SupplierDaoJDBC.getInstance();
        ProductCategoryDaoJDBC catJdbc = ProductCategoryDaoJDBC.getInstance();
//        pdwj.add(prod);
//        pdwj.add(prod2);
        System.out.println(prodJdbc.find(11));
        System.out.println(prodJdbc.getAll());
        Supplier sup = suppJdbc.find(12);
        System.out.println(sup);
        ProductCategory cat = catJdbc.find(12);
        System.out.println(prodJdbc.getBy(cat));
    }

}


