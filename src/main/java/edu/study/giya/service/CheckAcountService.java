/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.study.giya.service;

import edu.study.giya.entity.Activity;
import edu.study.giya.entity.UserInfo;
import java.util.ArrayList;
import java.util.List;

public class CheckAcountService {
    
    // check charity account
    public List<Activity> getAllActivity(String username){
        
        //get user accord username
        UserInfo charity = new UserInfo();
        UserInfoService userInfoService = new UserInfoService();
        //search the charity by username
        charity = userInfoService.findUserByName(username);
        
        //get charity activity
        ActivityService activityService = new ActivityService();
        List<Activity> activities = new ArrayList<Activity>();
        activities = activityService.getPartListActivity(username);
               
        
        return activities;
}
    public Activity getActivity(int id){
        // define 
        Activity activity = new Activity();
        ActivityService activityService = new ActivityService();
        activity = activityService.getActivity(id);
        
        return activity;
    }
    
}
