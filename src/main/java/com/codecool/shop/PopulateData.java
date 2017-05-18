package com.codecool.shop;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ProducCategoryDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

/**
 * Created by judit on 18.05.17.
 */
public class PopulateData {

    public static void populateData() {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = ProducCategoryDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier apple = new Supplier("Apple","Phones");
        supplierDataStore.add(apple);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "A smartphone is a cellular telephone with an integrated computer and other features not originally associated with telephones, such as an operating system, Web browsing and the ability to run software applications.");
        productCategoryDataStore.add(phone);
        ProductCategory laptop = new ProductCategory("Laptop", "hardware", " laptop, often called a notebook or \"notebook computer\", is a small, portable personal computer with a \"clamshell\" form factor, an alphanumeric keyboard on the lower part of the \"clamshell\" and a thin LCD or LED computer screen on the upper portion, which is opened up to use the computer. ");
        productCategoryDataStore.add(laptop);
        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("LENOVO IDEAPAD 100-15IBD", 350,"USD", "Great Machine!!!", laptop, lenovo));
        productDataStore.add(new Product("Apple Iphone 7",450,"USD", "Great Phone!!!",phone,apple));


    }
}
