import com.codecool.shop.controller.ProductController;
import com.codecool.shop.controller.CartController;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.*;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // populate some data for the memory storage
        populateData();
        //TestOrder.dummyOrder();

        // Always start with more specific routes
        get("/hello", (req, res) -> "Hello World");

        // Always add generic routes to the end
        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());
        // Equivalent with above
        get("/index", (Request req, Response res) -> {
           return new ThymeleafTemplateEngine().render( ProductController.renderProducts(req, res) );
        });
        get("/cart", CartController::renderCart, new ThymeleafTemplateEngine());
        // Add this line to your project to enable the debug screen
        enableDebugScreen();

        get("/hello/:id", (request, response) -> {
            String idString = request.params(":id");
            int id = Integer.parseInt(idString);
            Order.getInstance().addItem(id);
            return request.params(":id");
        });

        get("/addToQuantity/:id", (request, response) -> {
            String idString = request.params(":id");
            int id = Integer.parseInt(idString);
            Lineitem selected = Order.getInstance().getLine(id);
            selected.addOneToQuantity();

            return request.params(":id");
        });






    }

    public static void populateData() {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(phone);


        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Amazon phone", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", phone, amazon));

    }

//    public static Order dummyOrder(){
//        Order newOrder = new Order();
//
//        ProductDao productDataStore = ProductDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
//
//        Supplier a = new Supplier("almaárus", "almákat árul");
//        supplierDataStore.add(a);
//
//        ProductCategory b = new ProductCategory("gyümölcs", "sdasg", "sdfasdfa");
//        productCategoryDataStore.add(b);
//
//
//        productDataStore.add(new Product("alma", 22.2f, "HUF", "piros alma", b, a));
//
//
//
//        Lineitem line = new Lineitem(productDataStore.find(1));
//        newOrder.addLine(line);
//
//
//        productDataStore.add(new Product("körte", 10.0f, "HUF", "zöld körte", b, a));
//        Lineitem line2 = new Lineitem(productDataStore.find(2));
//
//
//        line2.addOneToQuantity();
//        newOrder.addLine(line2);
//
//        return newOrder;
//    }


}
