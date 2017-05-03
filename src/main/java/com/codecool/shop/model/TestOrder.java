package com.codecool.shop.model;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;

/**
 * Created by tahin on 2017.05.02..
 */
public class TestOrder {

    public static void main(String[] args) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        Supplier a = new Supplier("almaárus", "almákat árul");
        supplierDataStore.add(a);

        ProductCategory b = new ProductCategory("gyümölcs", "sdasg", "sdfasdfa");
        productCategoryDataStore.add(b);


        productDataStore.add(new Product("alma", 22.2f, "HUF", "piros alma", b, a));


        Order newOrder = new Order();
        Lineitem line = new Lineitem(productDataStore.find(1));
        newOrder.addLine(line);


        productDataStore.add(new Product("körte", 10.0f, "HUF", "zöld körte", b, a));
        Lineitem line2 = new Lineitem(productDataStore.find(2));


        line2.addOneToQuantity();
        newOrder.addLine(line2);


        System.out.println(line);
        System.out.println(newOrder);
    }

}
