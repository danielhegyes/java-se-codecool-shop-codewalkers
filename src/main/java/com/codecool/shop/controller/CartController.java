package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.TestOrder;
import com.codecool.shop.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by judit on 02.05.17.
 */
public class CartController {
    public static ModelAndView renderCart(Request req, Response res) {
//        ProductDao productDataStore = ProductDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        Order myOrder = Order.getInstance();
        Map params = new HashMap<>();
//        params.put("order", productCategoryDataStore.find(1));
//        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        params.put("order", myOrder);
        params.put("lines", myOrder.getOrderLines());
        return new ModelAndView(params, "product/cart");
    }

}
