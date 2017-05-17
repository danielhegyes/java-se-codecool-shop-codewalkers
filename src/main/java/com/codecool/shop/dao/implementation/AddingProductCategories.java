package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.ProductCategory;

import java.util.ArrayList;

/**
 * Created by judit on 16.05.17.
 */
public class AddingProductCategories {

    public static void main(String[] args) {
        update();
        ProductCategoryDaoJDBC prodCatDaoJdbc = ProductCategoryDaoJDBC.getInstance();
        prodCatDaoJdbc.add(new_categories.get(3));
    }

    public static ArrayList<ProductCategory> new_categories = new ArrayList<>();

    public static void update() {

        new_categories.add(new ProductCategory("10","newProd1", "dep", "desc"));
        new_categories.add(new ProductCategory("11","newProd2", "dep", "desc"));
        new_categories.add(new ProductCategory("12", "newProd3", "dep", "desc"));
        new_categories.add(new ProductCategory("13","newProd4", "dep", "desc"));
        new_categories.add(new ProductCategory("14","newProd5", "dep", "desc"));
    }

}
