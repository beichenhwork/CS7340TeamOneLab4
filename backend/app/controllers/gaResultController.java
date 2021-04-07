package controllers;

import com.avaje.ebean.SqlRow;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Result;
import utils.MinimizingMakeChange;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static play.mvc.Controller.request;
import static play.mvc.Results.ok;

public class gaResultController {

    public Result gaResult(){
        System.out.println("In gaResult!");
        System.out.println("Receive the command!");
        try {
            System.out.println("Receive the command2!");
            Random rd = new Random();
            int targetAmount = rd.nextInt(3000);
            ObjectNode result = MinimizingMakeChange.makeChangeForAmount(targetAmount,true);
            System.out.println("Receive the command3!");
            System.out.println(result);
            return ok(result);
        }
        catch (Exception e) {
            return ok("false");
        }
    }


}
