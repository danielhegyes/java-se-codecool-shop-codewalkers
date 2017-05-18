package com.codecool.shop.dao.implementation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by judit on 18.05.17.
 */
public class InitTestShop {

    public String DATABASE = "jdbc:postgresql://localhost:5432/testshop";
    private final String DB_USER = readConfigFile().get(0);
    private final String DB_PASSWORD = readConfigFile().get(1);

    public void initDbForTestshop() {
        String query =
                "\n" +
                        "DROP TABLE if EXISTS product, product_category,supplier;\n" +
                        "\n" +
                        "\n" +
                        "CREATE TABLE product_category\n" +
                        "(\n" +
                        "  id INTEGER PRIMARY KEY,\n" +
                        "  name varchar(40),\n" +
                        "  description varchar(50),\n" +
                        "  department VARCHAR(40)\n" +
                        ");\n" +
                        "\n" +
                        "CREATE TABLE supplier\n" +
                        "(\n" +
                        "  id INTEGER PRIMARY KEY,\n" +
                        "  name varchar(40),\n" +
                        "  description varchar(50)\n" +
                        ");\n" +
                        "\n" +
                        "CREATE TABLE product\n" +
                        "(\n" +
                        "  id INTEGER PRIMARY KEY,\n" +
                        "  name varchar(40),\n" +
                        "  description varchar(150),\n" +
                        "  default_price DOUBLE PRECISION,\n" +
                        "  default_currency varchar(3),\n" +
                        "  product_category INTEGER REFERENCES product_category(id),\n" +
                        "  supplier INTEGER REFERENCES supplier(id)\n" +
                        ");\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n";
        executeQuery(query);
    }

    private List<String> readConfigFile() {
        try {
            return Files.readAllLines(Paths.get("src/dbConfig.txt"), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Config file not found");
        }
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
}
