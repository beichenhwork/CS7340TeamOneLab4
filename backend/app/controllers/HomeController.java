package controllers;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.*;
import utils.MinimizingMakeChange;

/**
 * File Name: HomeController.java
 *
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 *
 * @author Beichen Hu, John Reynolds, Jianyu Shen, Jiachen Tang
 * @date March 7th, 2021
 * @since 1.0
 */

public class HomeController extends Controller {
    public Result index() throws Exception {
        return ok(Json.toJson("Welcome to backend!"));
    }
}
