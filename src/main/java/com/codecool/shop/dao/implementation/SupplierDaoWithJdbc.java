package com.codecool.shop.dao.implementation;/*
Created by tahin on 2017.05.17..
*/

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.List;

import static org.postgresql.jdbc.EscapedFunctions.DATABASE;

public class SupplierDaoWithJdbc implements SupplierDao {

    private static SupplierDaoWithJdbc instance = null;

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "tahin";
    private static final String DB_PASSWORD = "asdfg";

    public static SupplierDaoWithJdbc getInstance() {
        if (instance == null) {
            instance = new SupplierDaoWithJdbc();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        String query = "INSERT INTO supplier (ID,NAME,description)\n" +
                "VALUES (" + supplier.getId() + ", '" + supplier.getName() + "' ,'" + supplier.getDescription() + "');";
        executeQuery(query);
    }

    @Override
    public Supplier find(int id) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
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
             Statement statement = connection.createStatement()

        ) {
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SupplierDaoWithJdbc sdwj = SupplierDaoWithJdbc.getInstance();
        Supplier supp = new Supplier("Huawei", "Cheap good gadets");
        Supplier supp2 = new Supplier("Samsung", "Expensive good gadets");
        sdwj.add(supp);
        sdwj.add(supp2);
    }

}
