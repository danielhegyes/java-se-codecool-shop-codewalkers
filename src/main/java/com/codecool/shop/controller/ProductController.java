package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.ProductDaoWithJdbc;
import com.codecool.shop.dao.implementation.SupplierDaoJDBC;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ProductController {

    public static ProductDao productDataStore = ProductDaoWithJdbc.getInstance();
    public static ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
    public static SupplierDao supplierDataStore = SupplierDaoJDBC.getInstance();

    public static ModelAndView renderProducts(Request req, Response res) {


        Map params = new HashMap<>();
        params.put("category", productCategoryDataStore.getAll());
        params.put("products", productDataStore.getAll());
        params.put("supplier", supplierDataStore.getAll());
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView prodByCategory(Request req, Response res, int id){


        Map params = new HashMap<>();

        params.put("category", productCategoryDataStore.find(id));
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(id)));
        return new ModelAndView(params, "product/index");
    }
    public static ModelAndView prodBySupplier(Request request, Response response, int id){


        Map paramsSup = new HashMap<>();
        paramsSup.put("supplier", supplierDataStore.find(id));
        paramsSup.put("products", productDataStore.getBy(supplierDataStore.find(id)));
        return new ModelAndView(paramsSup, "product/index");

    }

}


