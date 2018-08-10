package edu.study.giya.service;

import edu.study.giya.dao.jpa.SimpleBaseCrudDao;
import edu.study.giya.entity.Activity;
import edu.study.giya.entity.UserInfo;
import java.util.ArrayList;
import java.util.List;

public class ActivityService extends ObjectService {
    private SimpleBaseCrudDao<Activity> simpleBaseCrudDao = new SimpleBaseCrudDao<Activity>(Activity.class);
 
    //add an activity
    // get an activity from up layer
    public boolean addActivity(Activity activity){
        boolean result = false;
        // get the activity from
        if(activity == null){
            return result;
        }else{
            simpleBaseCrudDao.add(activity);
            result = true;
        }
        return result;
    }
    
    // delete an activity
    public boolean delActivity(Integer id){
        boolean result = false;
        //get 
        Activity activity = new Activity();
        if (id == null){
            return result;
        }else{
            activity = simpleBaseCrudDao.getEntityById(id);
            simpleBaseCrudDao.remove(activity);
            result = true;
        }
        return result;
    }
    
    
    // get an activity by 
    public List<Activity> getAllActivity(){
        List<Activity> activities = new ArrayList<Activity>();     
        String sql = "select a from Activity a";
        activities = simpleBaseCrudDao.jpaCrudList(sql);
        return activities;
    }
    
    //modify an activity
    public boolean modifyActivity(Activity activity){
        boolean result = false;
        if(activity == null){
            return result;
        }else{
            simpleBaseCrudDao.modify(activity);
            result = true;
        }
        return result;
    }
    
    //accord the username query activity
    public List<Activity> getPartListActivity(String username){
        List<Activity> activities = new ArrayList<Activity>();
        String sql = "select * from userinfo where username=" + username; 
        activities = simpleBaseCrudDao.queryByField(Activity.class, "username", username);
    
        return activities;
    }
    
    public Activity getActivity(int id){
        Activity activity = new Activity();
        activity = simpleBaseCrudDao.findById(id);
    
        return activity;
    }
    
}
