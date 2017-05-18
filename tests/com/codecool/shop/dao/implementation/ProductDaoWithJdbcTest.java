package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by judit on 18.05.17.
 */
class ProductDaoWithJdbcTest {

    public static ProductCategory chair = new ProductCategory(99, "chair", "furniture", "you sit on these");
    public static Supplier IKEA = new Supplier("IKEA", "Sweden, wood, and.. stuff");

    public static ProductDao productDao = ProductDaoWithJdbc.getInstance();
    public static Product chairOne = new Product(42, "börje", 79, "euro", "description of chair", chair, IKEA);
    public static Product chairTwo = new Product(33, "bjursta", 29, "euro", "description of chair", chair, IKEA);
    public static Product chairOneGet = productDao.find(chairOne.getId());
    public static Product chairTwoGet = productDao.find(chairTwo.getId());


    @BeforeAll
    private static void testDataUploader() {

        productDao.remove(chairOne.getId());
        productDao.remove(chairTwo.getId());
        productDao.add(chairOne);
        productDao.add(chairTwo);
        System.out.println(chairOne);
        System.out.println(chairOneGet);
    }

    @Test
    public void checkSupplier() {

        assertEquals(chairOne.getSupplier(), chairOneGet.getSupplier());

    }

}