package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by judit on 17.05.17.
 */
class SupplierDaoTest {

    static SupplierDao supplierDao = SupplierDaoMem.getInstance();
    Supplier vegetableStore; //= new ProductCategory(34, "vegetable", "grocery", "description");
    Supplier fruitStore; //= new ProductCategory(35, "fruit", "grocery", "description");
    Supplier vegetableStoreFromDb; //= productCategoryDao.find(vegetable.getId())
    Supplier fruitStoreFromDb; //= productCategoryDao.find(vegetable.getId());
    Supplier tomatoStore;
    Supplier tomatoFromDb;
    private static InitTestShop initTestShop = new InitTestShop();


    @BeforeAll
    public static void testDataUploader() {

        if (supplierDao instanceof SupplierDaoJDBC){
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
        tomatoFromDb = supplierDao.find(tomatoStore.getId());
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

        assertEquals(vegetableStore.getId(), vegetableStoreFromDb.getId());
        assertEquals(fruitStore.getId(), fruitStoreFromDb.getId());
        assertEquals(tomatoStore.getId(), tomatoFromDb.getId());
    }

}