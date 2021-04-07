package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Controller;
import play.mvc.Result;
import play.libs.concurrent.HttpExecutionContext;
import play.libs.ws.WSResponse;
import views.html.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Software Service Market Place
 */
public class HomeController extends Controller {

    @Inject
    HttpExecutionContext ec;

    private FormFactory formFactory;

    @Inject
    public HomeController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    /**
     * Index page
     */
    public Result resultView() {
        return ok(views.html.resultView.render(""));
    }

    public CompletionStage<Result> gaHandler() {
        Form<gaClient> publicationForm = formFactory.form(gaClient.class).bindFromRequest();
        if (publicationForm.hasErrors()){
            return (CompletionStage<Result>) badRequest(views.html.gaResult.render(null));  // send parameter like register so that user could know
        }

        return publicationForm.get().checkAuthorized().thenApplyAsync((WSResponse r) -> {
            if (r.getStatus() == 200 && r.asJson() != null && !r.getBody().equals("null")) {
                JsonNode res = r.asJson();

                List<List<String>> result = new ArrayList<>();
                System.out.println("The rseult you are looking for: ");
                    JsonNode row = res;
                    List<String> curNode = new ArrayList<>();
                    curNode.add(""+row.findValue(("evolutionTime")));
                    curNode.add(""+row.findValue(("bestSolution")));
                    curNode.add(""+row.findValue(("services")));
                    result.add(curNode);
                    System.out.println(result);
                return ok(views.html.gaResult.render(result));
            } else {
                System.out.println("response null");
                return badRequest(views.html.gaResult.render(null));
            }
        }, ec.current());
    }





}