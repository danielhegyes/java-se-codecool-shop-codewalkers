import com.codecool.shop.controller.CartController;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.model.Lineitem;
import com.codecool.shop.model.Order;
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
