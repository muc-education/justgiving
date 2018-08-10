/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.study.giya.controller;

import edu.study.giya.entity.Activity;
import edu.study.giya.service.ActivityService;
import java.sql.Timestamp;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ManagedBean(name="activity")
@SessionScoped
@Path("/addActivity")
public class ActivityController {
    private static Logger logger = LoggerFactory.getLogger(UserInfoController.class.getName());
    
    private ActivityService activityService;
    
    private Activity activity;
    private Integer id;
    private String title;
    private String content;
    private String location;
    private Double money;
    private String state;
    private Timestamp startTime;
    private Timestamp endTime;
    private Double Account;
    
    public Double getAccount() {
        return Account;
    }

    public void setAccount(Double Account) {
        this.Account = Account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    
    public void addActivity(){
        //get value from xmlns
        Activity activity = new Activity();
        activity.setTitle(title);
        activity.setContent(content);
        activity.setMoney(money);
        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        activity.setAccount(0);
        activityService.addActivity(activity);
        logger.info("add an Activity");
    }
    
    
    
}



