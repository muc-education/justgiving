/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.study.giya.controller;


import edu.study.giya.entity.MessageInfo;
import edu.study.giya.entity.Role;
import edu.study.giya.entity.UserInfo;
import edu.study.giya.service.RoleService;
import edu.study.giya.service.UserInfoService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;

public class CharityController {

    @Inject
    private UserInfoService userInfoService;
    @Inject
    private RoleService roleService;


    //register a charity

    @GET
    @Path("/charity/{id}")
    // @Produces("text/plain")
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("id") String id) {
        MessageInfo messageInfo = new MessageInfo();
        if (id == null) {
            messageInfo.setCode(1400);
            messageInfo.setMessage("Error request.");
            return messageInfo.toString();
        }
        try {
            Integer i = Integer.parseInt(id);
            UserInfo userInfo = userInfoService.findUserById(i);
            Role role = roleService.getRoleById(userInfo.getRoleId());
            // we think the power more 256 and type equals charity would be Charity user
            if (role.getType().equalsIgnoreCase("charity") && role.getPower() > 256) {
                messageInfo.setCode(200);
                messageInfo.setMessage(userInfo.toString());
            } else {
                messageInfo.setCode(500);
                messageInfo.setMessage("Not a valid charity. or Under register.");
            }
        } catch (Exception e) {
            messageInfo.setCode(1444);
            messageInfo.setMessage("Error Request.");
        } finally {
            return messageInfo.toString();
        }
    }

    @POST
    @Path("/charity/donation")
    //@Produces("text/plain")
    @Produces(MediaType.APPLICATION_XHTML_XML)
    public String post(@Context HttpServletRequest servletRequest, @Context HttpServletResponse servletResponse) {
        MessageInfo messageInfo = new MessageInfo();
        String charityId = servletRequest.getParameter("charityId");
        String money = servletRequest.getParameter("money");
        String userId = servletRequest.getParameter("userId");
        try {
            Integer i = Integer.parseInt(charityId);
            UserInfo charity = userInfoService.findUserById(i);
            i = Integer.parseInt(userId);
            Double m = Double.parseDouble(money);
            UserInfo sponser = userInfoService.findUserById(i);
            Role role = roleService.getRoleById(charity.getRoleId());
            // we think the power more 256 and type equals charity would be Charity user
            if (role.getType().equalsIgnoreCase("charity") && role.getPower() > 256) {
                if (sponser.getMoney() > 0 && m <= 500) {
                    messageInfo.setCode(200);
                    messageInfo.setMessage("sent the money to the charity.");
                } else {
                    messageInfo.setCode(300);
                    messageInfo.setMessage("user should not donate more 500$ or your account is less than 0$.");
                }

            } else {
                messageInfo.setCode(500);
                messageInfo.setMessage("Not a valid charity. or Under register.");
            }
        } catch (Exception e) {
            messageInfo.setCode(1444);
            messageInfo.setMessage("Error Request.");
        } finally {
            return messageInfo.toString();
        }

    }
    
    
}
