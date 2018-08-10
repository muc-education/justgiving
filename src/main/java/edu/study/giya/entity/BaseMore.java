/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.study.giya.entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 *
 * @author gwd
 */

public class BaseMore implements Serializable {
    private String more;

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }
    
    public void setMoreInfo(JsonObject jsonObject){
        this.more=jsonObject.toString();
    }
        
    public JsonObject getMoreInfo(){
           JsonParser parser=new JsonParser();
           JsonObject o=new JsonObject();
           if(more==null)
               return o;
           else
               o=parser.parse(more).getAsJsonObject();
           return o;
    }
}
