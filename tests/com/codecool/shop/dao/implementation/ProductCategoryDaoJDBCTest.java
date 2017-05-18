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
class ProductCategoryDaoJDBCTest {

    public static ProductCategoryDao productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();
    public static ProductCategoryDao productCategoryDaoMem = ProductCategoryDaoMem.getInstance();

    @BeforeEach
    public void
    
    @Test
    public void addToDbOrMem() {
        ProductCategory vegetable = new ProductCategory(34, "vegetable", "grocery", "descriptipn");

        productCategoryDaoJDBC.add(vegetable);
        productCategoryDaoMem.add(vegetable);

        ProductCategory vegetableFromDb = productCategoryDaoJDBC.find(vegetable.getId());
        ProductCategory vegetableFromMem = productCategoryDaoMem.find(vegetable.getId());

        assertEquals(vegetable, vegetableFromDb);
        assertEquals(vegetable, vegetableFromMem);
    }

    @Test
    public void findInDbOrMem() {
        ProductCategory vegetable = new ProductCategory(34, "vegetable", "grocery", "descriptipn");

        productCategoryDaoJDBC.add(vegetable);
        productCategoryDaoMem.add(vegetable);

        ProductCategory vegetableFromDb = productCategoryDaoJDBC.find(vegetable.getId());
        ProductCategory vegetableFromMem = productCategoryDaoMem.find(vegetable.getId());

        assertEquals(vegetable, vegetableFromDb);
        assertEquals(vegetable, vegetableFromMem);
    }

    @Test
    public void removeFromDbOrMem() {
        ProductCategory vegetable = new ProductCategory(34, "vegetable", "grocery", "descriptipn");

        productCategoryDaoJDBC.add(vegetable);
        productCategoryDaoMem.add(vegetable);

        productCategoryDaoJDBC.remove(vegetable.getId());
        productCategoryDaoMem.remove(vegetable.getId());

        ProductCategory vegetableFromDb = productCategoryDaoJDBC.find(vegetable.getId());
        ProductCategory vegetableFromMem = productCategoryDaoMem.find(vegetable.getId());

        assertNull(productCategoryDaoJDBC.find(vegetable.getId()));
        assertNull(productCategoryDaoMem.find(vegetable.getId()));

    }

    @Test
    public void getAllFromDbOrMem() {

    }




}