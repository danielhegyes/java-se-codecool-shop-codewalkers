import com.codecool.shop.controller.CartController;
import com.codecool.shop.controller.ProductController;
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

import javax.json.Json;
import javax.json.JsonObject;

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

        // Always add generic routes to the end
        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());

        // Equivalent with above
        get("/index", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(ProductController.renderProducts(req, res));
        });

        get("/category/:id", (Request req, Response res) -> {
            String categoryStringId = req.params(":id");
            int  categoryId = Integer.parseInt(categoryStringId);
            return new ThymeleafTemplateEngine().render( ProductController.prodByCategory(req, res, categoryId));
        });

        get("/supplier/:id", (Request req, Response res) -> {
            String supplierStringId = req.params(":id");
            int  supplierId = Integer.parseInt(supplierStringId);
            return new ThymeleafTemplateEngine().render( ProductController.prodBySupplier(req, res, supplierId));
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
            System.out.println(Order.getInstance());

            JsonObject object = Json.createObjectBuilder()
                    .add("quantity", selected.getQuantity())
                    .add("linePrice", selected.getLinePrice())
                    .add("total", Order.getInstance().getTotal())
                    .build();

            return object.toString();
        });

        get("/subFromQuantity/:id", (request, response) -> {
            String idString = request.params(":id");
            int id = Integer.parseInt(idString);
            Lineitem selected = Order.getInstance().getLine(id);
            if (selected.subOneFromQuantity()) {
                    Order.getInstance().removeLine(selected);
            }

            System.out.println(Order.getInstance());

            JsonObject object = Json.createObjectBuilder()
                    .add("quantity", selected.getQuantity())
                    .add("linePrice", selected.getLinePrice())
                    .add("total", Order.getInstance().getTotal())
                    .build();

            return object.toString();
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
        Supplier apple = new Supplier("Apple","Phones");

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
        productDataStore.add(new Product("Apple Iphone 7",450,"USD", "Great Phone!!!",phone,apple))
        ;


    }




}
