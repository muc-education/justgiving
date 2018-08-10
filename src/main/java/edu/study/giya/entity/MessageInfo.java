/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.study.giya.entity;

import javax.faces.bean.ManagedBean;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gwd
 */

public class MessageInfo {
    private Integer code;
    private String type = "Notify";
    private String message;
    private Object data;
    private JsonObject info;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonObject getInfo() {
        if (info == null)
            info = new JsonObject();
        return info;
    }


    public void setInfo(JsonObject info) {
        this.info = info;
    }

    public String toString() {
        String s = info.toString();
        String ss;
        if (data != null && info != null) {
            ss = "{\"code\":" + code + "\",\"type\":" + type + "\",\"message\":" + message + ",\"data\":" + data.toString() + ",\"info\":" + info.toString() + "}";
        } else if (data != null) {
            ss = "{\"code\":" + code + "\",\"type\":" + type + "\",\"message\":" + message + ",\"info\":" + info.toString() + "}";
        } else if (info != null) {
            ss = "{\"code\":" + code + "\",\"type\":" + type + "\",\"message\":" + message + ",\"data\":" + data.toString() + "}";
        } else {
            ss = "{\"code\":" + code + "\",\"type\":" + type + "\",\"message\":" + message + "}";
        }
        return ss;
    }

    public String toString(String data, String info) {
        String s;
        if (data != null && info != null) {
            s = "{\"code\":" + code + "\",\"type\":" + type + "\",\"message\":" + message + ",\"data\":" + data + ",\"info\":" + info + "}";
        } else if (info != null) {
            s = "{\"code\":" + code + "\",\"type\":" + type + "\",\"message\":" + message + ",\"info\":" + info + "}";

        } else {
            s = "{\"code\":" + code + "\",\"type\":" + type + "\",\"message\":" + message + ",\"data\":" + data + "}";
        }
        return s;
    }

}
