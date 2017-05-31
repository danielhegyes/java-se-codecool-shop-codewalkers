import com.codecool.shop.controller.CartController;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.model.Lineitem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import javax.json.Json;
import javax.json.JsonObject;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * <h1>Codecool Shop</h1>
 * A simple Spark based webshop application with database connection
 * where the user can add items to the cart.
 * <p>
 * <b>Note:</b> Please don't use this application in real life
 * as it has serious security issues.
 *
 * @author  CodewalkerZ
 * @version 0.2
 * @since   2017-05-01
 */

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    /**
     * This is the main method which handles the Spark requests.
     * It does logging to the console.
     * @param args Unused.
     */
    public static void main(String[] args) {

        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.info("I am informative!");

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);


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


        //get("/cart", CartController::renderCart(), new ThymeleafTemplateEngine());

        get("/cart", (Request request, Response res) -> {
            Order myOrder = Order.getOrder(request);
            logger.debug("The actual order is: {}", myOrder);
            return new ThymeleafTemplateEngine().render(CartController.renderCart(request, res, myOrder));
        });

        // Add this line to your project to enable the debug screen
        enableDebugScreen();

        get("/hello/:id", (request, response) -> {
            String idString = request.params(":id");
            int id = Integer.parseInt(idString);
            Order myOrder = Order.getOrder(request);
            myOrder.addItem(id);
            return Order.getOrder(request).getTotalQuantity();
        });

        get("/addToQuantity/:id", (request, response) -> {
            String idString = request.params(":id");
            int id = Integer.parseInt(idString);
            Order myOrder = Order.getOrder(request);
            Lineitem selected = myOrder.getLine(id);
            selected.addOneToQuantity(request);
            System.out.println(myOrder);

            JsonObject object = Json.createObjectBuilder()
                    .add("quantity", selected.getQuantity())
                    .add("linePrice", selected.getLinePrice())
                    .add("total", myOrder.getTotal())
                    .build();

            return object.toString();
        });

        get("/subFromQuantity/:id", (request, response) -> {
            String idString = request.params(":id");
            int id = Integer.parseInt(idString);
            Order myOrder = Order.getOrder(request);
            Lineitem selected = myOrder.getLine(id);
            if (selected.subOneFromQuantity(request)) {
                    myOrder.removeLine(selected);
            }

            System.out.println(myOrder);


            JsonObject object = Json.createObjectBuilder()
                    .add("quantity", selected.getQuantity())
                    .add("linePrice", selected.getLinePrice())
                    .add("total", myOrder.getTotal())
                    .build();

            return object.toString();
        });
    }
}
