package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by judit on 18.05.17.
 */
class ProductDaoMemTest {

    static ProductDao productDao = ProductDaoWithJdbc.getInstance();
    static ProductCategoryDao productCategoryDao = ProductCategoryDaoJDBC.getInstance();
    static SupplierDao supplierDao = SupplierDaoJDBC.getInstance();

    ProductCategory vegetable; //= new ProductCategory(34, "vegetable", "grocery", "description");
    ProductCategory fruit; //= new ProductCategory(35, "fruit", "grocery", "description");
    ProductCategory vegetableFromDb; //= productCategoryDao.find(vegetable.getId())
    ProductCategory fruitFromDb; //= productCategoryDao.find(vegetable.getId());
    ProductCategory tomato;
    ProductCategory tomatoFromDb;

    Supplier vegetableStore; //= new ProductCategory(34, "vegetable", "grocery", "description");
    Supplier fruitStore; //= new ProductCategory(35, "fruit", "grocery", "description");
    Supplier vegetableStoreFromDb; //= productCategoryDao.find(vegetable.getId())
    Supplier fruitStoreFromDb; //= productCategoryDao.find(vegetable.getId());
    Supplier tomatoStore;
    Supplier TomatoStoreFromDb;

    Product fruit1;
    Product vegetable1;
    Product tomato1;
    Product fruit1fromDb;
    Product vegetable1fromDb;
    Product tomato1fromDb;

    private static InitTestShop initTestShop = new InitTestShop();


    @BeforeAll
    public static void testDataUploader() {

        if (productDao instanceof ProductDaoWithJdbc){
            initTestShop.initDbForTestshop();
            SupplierDaoJDBC.DATABASE = initTestShop.DATABASE;
        }

    }

    @BeforeEach
    public void PopulateData() {
        initTestShop.initDbForTestshop();

        fruitStore = new Supplier(40, "fruitStore", "description1");
        vegetableStore = new Supplier(41, "vegetableStore", "description2");
        tomatoStore = new Supplier(42, "tomatoStore", "description3");

        supplierDao.add(fruitStore);
        supplierDao.add(vegetableStore);
        supplierDao.add(tomatoStore);
        fruitStoreFromDb = supplierDao.find(fruitStore.getId());
        vegetableStoreFromDb = supplierDao.find(vegetableStore.getId());
        TomatoStoreFromDb = supplierDao.find(tomatoStore.getId());

        fruit = new ProductCategory(34, "fruit", "grocery1", "description1");
        vegetable = new ProductCategory(35, "vegetable", "grocery2", "description2");
        tomato = new ProductCategory(36, "tomato", "grocery3", "description3");

        productCategoryDao.add(fruit);
        productCategoryDao.add(vegetable);
        productCategoryDao.add(tomato);
        fruitFromDb = productCategoryDao.find(fruit.getId());
        vegetableFromDb = productCategoryDao.find(vegetable.getId());
        tomatoFromDb = productCategoryDao.find(tomato.getId());

        Product fruit1 = new Product(51, "fruit1", 20.0f, "USD", "description", fruit, fruitStore );
        Product vegetable1 = new Product(52, "fruit2", 21.0f, "USD", "description", vegetable, vegetableStore );
        Product tomato1 = new Product(53, "fruit3", 22.0f, "USD", "description", tomato, tomatoStore );
        Product fruit1fromDb = productDao.find(fruit1.getId());
        Product vegetable1fromDb = productDao.find(vegetable1.getId());
        Product tomato1fromDb = productDao.find(tomato1.getId());
    }

    @Test
    public void removeAndAddCheck() {
        supplierDao.remove(vegetableStore.getId());
        supplierDao.remove(fruitStore.getId());
        supplierDao.add(vegetableStore);
        supplierDao.add(fruitStore);

        assertEquals(vegetableStore.getId(), vegetableStore.getId());
    }

    @Test
    public void checkIfIdValid() {

        assertEquals(vegetableStore.getId(), vegetableStoreFromDb.getId());


    }

    @Test
    public void checkIfNameValid() {

        assertEquals(vegetableStore.getName(), vegetableStoreFromDb.getName());
    }

    @Test
    public void removeFromDbOrMem() {

        supplierDao.remove(fruitStore.getId());

        assertNull(supplierDao.find(fruitStore.getId()));

    }

    @Test
    public void checkIfIdIsInt() {

        assertTrue(vegetableStoreFromDb.getId() == 41);

    }

    @Test
    public void checkGetAll() {

        assertEquals(vegetable1.getId(), vegetable1fromDb.getId());
        assertEquals(fruitStore.getId(), fruitStoreFromDb.getId());
        assertEquals(tomatoStore.getId(), tomatoFromDb.getId());
    }

}