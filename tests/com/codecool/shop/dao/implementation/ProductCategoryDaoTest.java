package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by judit on 17.05.17.
 */
class ProductCategoryDaoTest {

     static ProductCategoryDao productCategoryDao = ProductCategoryDaoJDBC.getInstance();
     ProductCategory vegetable; //= new ProductCategory(34, "vegetable", "grocery", "description");
     ProductCategory fruit; //= new ProductCategory(35, "fruit", "grocery", "description");
     ProductCategory vegetableFromDb; //= productCategoryDao.find(vegetable.getId())
     ProductCategory fruitFromDb; //= productCategoryDao.find(vegetable.getId());
     ProductCategory tomato;
     ProductCategory tomatoFromDb;
    private static InitTestShop initTestShop = new InitTestShop();


    @BeforeAll
    public static void testDataUploader() {

        if (productCategoryDao instanceof ProductCategoryDaoJDBC){
            initTestShop.initDbForTestshop();
            ProductCategoryDaoJDBC.DATABASE = initTestShop.DATABASE;
        }



    }

    @BeforeEach
    public void PopulateData() {
        initTestShop.initDbForTestshop();
        fruit = new ProductCategory(34, "fruit", "grocery1", "description1");
        vegetable = new ProductCategory(35, "vegetable", "grocery2", "description2");
        tomato = new ProductCategory(36, "tomato", "grocery3", "description3");
        productCategoryDao.add(fruit);
        productCategoryDao.add(vegetable);
        productCategoryDao.add(tomato);
        fruitFromDb = productCategoryDao.find(fruit.getId());
        vegetableFromDb = productCategoryDao.find(vegetable.getId());
        tomatoFromDb = productCategoryDao.find(tomato.getId());
    }

    @Test
    public void removeAndAddCheck() {
        productCategoryDao.remove(vegetable.getId());
        productCategoryDao.remove(fruit.getId());
        productCategoryDao.add(vegetable);
        productCategoryDao.add(fruit);

        assertEquals(vegetable.getId(), vegetableFromDb.getId());
    }
    
    @Test
    public void checkIfIdValid() {

        assertEquals(vegetable.getId(), vegetableFromDb.getId());


    }

    @Test
    public void checkIfNameValid() {

        assertEquals(vegetable.getName(), vegetableFromDb.getName());
    }

    @Test
    public void removeFromDbOrMem() {

        productCategoryDao.remove(fruit.getId());

        assertNull(productCategoryDao.find(fruit.getId()));

    }

    @Test
    public void checkIfIdIsInt() {

        assertTrue(vegetableFromDb.getId() == 35);

    }

    @Test
    public void checkGetAll() {

        assertEquals(vegetable.getId(), vegetableFromDb.getId());
        assertEquals(fruit.getId(), fruitFromDb.getId());
        assertEquals(tomato.getId(), tomatoFromDb.getId());
    }

}