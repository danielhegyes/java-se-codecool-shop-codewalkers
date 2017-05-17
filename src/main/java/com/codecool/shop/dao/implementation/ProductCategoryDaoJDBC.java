package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * Created by judit on 16.05.17.
 */
public class ProductCategoryDaoJDBC implements ProductCategoryDao {

    //?
    private static final String DATABASE = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = readConfigFile().get(0);
    private static final String DB_PASSWORD = readConfigFile().get(1);


    private static ProductCategoryDaoJDBC instance = null;

    private static List<String> readConfigFile() {
        try {
            return Files.readAllLines(Paths.get("src/dbConfig.txt"), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Config file not found");
        }
        return null;
    }

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoJDBC() {
    }

    public static ProductCategoryDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory productCategory) {
        String query = "INSERT INTO product_category (id, name, description, department) " +
                "VALUES ('" + productCategory.getId() + "','" + productCategory.getName() + "', '" + productCategory.getDescription() + "', '" + productCategory.getDepartment() + "');";
        executeQuery(query);
    }

    @Override
    public ProductCategory find(int id) {

        String query = "SELECT * FROM product_category WHERE id ='" + id + "';";

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            if (resultSet.next()){
                return instantiateProductCategoryFromQuery(resultSet);
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
        String query = "DELETE FROM product_category WHERE id = '" + id +"';";
        executeQuery(query);
    }

    @Override
    public List<ProductCategory> getAll() {
        String query = "SELECT * FROM product_category;";

        List<ProductCategory> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                ProductCategory actProdCat = instantiateProductCategoryFromQuery(resultSet);
                resultList.add(actProdCat);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public ProductCategory instantiateProductCategoryFromQuery(ResultSet resultSet) throws SQLException {
        ProductCategory result = new ProductCategory(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("department"));
        return result;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    private void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
        ){
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
