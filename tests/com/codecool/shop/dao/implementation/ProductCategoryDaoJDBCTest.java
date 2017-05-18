package com.codecool.shop.dao.implementation;


import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Created by judit on 17.05.17.
 */
class ProductCategoryDaoJDBCTest {

    @Test
    public void addToDbOrMem() {
        ProductCategoryDaoJDBC productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();
        ProductCategoryDaoMem productCategoryDaoMem = ProductCategoryDaoMem.getInstance();
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
        ProductCategoryDaoJDBC productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();
        ProductCategoryDaoMem productCategoryDaoMem = ProductCategoryDaoMem.getInstance();
        ProductCategory vegetable = new ProductCategory(34, "vegetable", "grocery", "descriptipn");


    }

    @Test
    public void removeFromDbOrMem() {

        ProductCategoryDaoJDBC productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();
        ProductCategoryDaoMem productCategoryDaoMem = ProductCategoryDaoMem.getInstance();
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






}