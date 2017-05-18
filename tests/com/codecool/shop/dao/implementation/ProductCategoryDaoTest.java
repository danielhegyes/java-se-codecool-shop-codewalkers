package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
/**
 * Created by judit on 17.05.17.
 */
class ProductCategoryDaoTest {

    public static ProductCategoryDao productCategoryDao = ProductCategoryDaoJDBC.getInstance();
    public static ProductCategory vegetable = new ProductCategory(34, "vegetable", "grocery", "description");
    public static ProductCategory fruit = new ProductCategory(35, "fruit", "grocery", "description");
    public static ProductCategory vegetableGet = productCategoryDao.find(vegetable.getId());
    public static ProductCategory fruitGet = productCategoryDao.find(vegetable.getId());
    public static Integer meaningOfLife = 42;

    @BeforeAll
    private static void testDataUploader() {

        productCategoryDao.remove(vegetable.getId());
        productCategoryDao.remove(fruit.getId());
        productCategoryDao.add(vegetable);
        productCategoryDao.add(fruit);
        System.out.println(vegetable);
        System.out.println(vegetableGet);
    }
    
    @Test
    public void checkIfIdValid() {

        assertEquals(vegetable.getId(), vegetableGet.getId());


    }

    @Test
    public void checkIfNameValid() {

        assertEquals(vegetable.getName(), vegetableGet.getName());
    }

    @Test
    public void removeFromDbOrMem() {

        productCategoryDao.remove(fruit.getId());

        assertNull(productCategoryDao.find(fruit.getId()));

    }

    @Test
    public void checkIfIdIsInt() {

        assertEquals(vegetable.getId(), (int)vegetable.getId());

    }


}