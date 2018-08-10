/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.study.test;

import edu.study.giya.entity.Activity;
import edu.study.giya.service.ActivityService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joshua
 */
public class ActivityTest {
    @Test
    public void test() {
        //add a activity
        Activity activity = new Activity();
        activity.setContent("test");
        activity.setLocation("en");
        activity.setTitle("temp");
        activity.setMoney(1000.0);
        ActivityService activityservice = new ActivityService();
        activityservice.addActivity(activity);
//        List<Activity> activities = new ArrayList<Activity>();
//        activities = activityservice.getAllActivity();
//        for (int i = 0; i < activities.size();i++){
//            System.out.println(activities.indexOf(i));
//        }
//        int id = activity.getId();
        boolean result = activityservice.delActivity(51);
        System.out.println(result);
    }
    }
    

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

