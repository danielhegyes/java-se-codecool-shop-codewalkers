package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
/**
 * Created by judit on 17.05.17.
 */
class ProductCategoryDaoTest {

    public static ProductCategoryDao productCategoryDao = ProductCategoryDaoJDBC.getInstance();
    public ProductCategory vegetable = new ProductCategory(34, "vegetable", "grocery", "descriptipn");
    public ProductCategory vegetableGet = productCategoryDao.find(vegetable.getId());

    @BeforeEach
    public void testDataUploader() {

        productCategoryDao.add(vegetable);

    }
    
    @Test
    public void addToDbOrMem() {

        assertEquals(vegetable, vegetableGet);

    }

    @Test
    public void findInDbOrMem() {

        assertEquals(vegetable, vegetableGet);
    }

    @Test
    public void removeFromDbOrMem() {

        productCategoryDao.remove(vegetable.getId());

        assertNull(productCategoryDao.find(vegetable.getId()));

    }

    @Test
    public void getAllFromDbOrMem() {

    }




}