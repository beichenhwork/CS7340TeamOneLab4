
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/huasucaster/Desktop/CS Master/CS7340/Lab 4/CS7340TeamOneLab4/backend/conf/routes
// @DATE:Tue Apr 06 21:15:10 CDT 2021

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseHomeController HomeController = new controllers.ReverseHomeController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReversegaResultController gaResultController = new controllers.ReversegaResultController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseHomeController HomeController = new controllers.javascript.ReverseHomeController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReversegaResultController gaResultController = new controllers.javascript.ReversegaResultController(RoutesPrefix.byNamePrefix());
  }

}
