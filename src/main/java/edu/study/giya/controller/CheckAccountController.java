/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 * this controller get charity activity data to web layer
 */
package edu.study.giya.controller;

import edu.study.giya.entity.Activity;
import edu.study.giya.service.ActivityService;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;
 

@Named(value="checkAccountController")
@SessionScoped
@Path("/checkAccount")
public class CheckAccountController {
    
    public List<Activity> CheckAccount(){
    
    //get browser session data
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext extContext =facesContext.getExternalContext();
    HttpSession session =(HttpSession)extContext.getSession(true); 

    String username = session.getAttribute("username").toString();
   //int id = session.getAttribute("id");
   
    List<Activity> activities = new ArrayList<Activity>();
    //if not get session, turn to login
    
    if(username == null){
        try{
            facesContext.getExternalContext().redirect("/Login");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }else{
    //get activity list from activityservice
    ActivityService activityService = new ActivityService();
    activities = activityService.getPartListActivity(username);
    
    //sent to page and jump
    }
    return activities;
    }
    
}
