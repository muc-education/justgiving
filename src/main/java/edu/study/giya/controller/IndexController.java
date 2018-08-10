/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.study.giya.controller;

import edu.study.giya.entity.MessageInfo;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gwd
 */
@ManagedBean(name="indexController",eager=true)
//@RequestScoped
@Path("/demo")
public class IndexController implements Serializable{
    private Logger logger=LoggerFactory.getLogger(IndexController.class);
    @ManagedProperty(value = "#{message}")
    private MessageInfo messageInfo;
    public IndexController(){
       logger.info("test.in.indexcontroller");
    }
    public String getMessage() {
      return "index conrtoller";
   }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}{path:.*}")
    public Response getDemoTest(@PathParam("id") String idstring) {
        logger.debug(idstring);
        return Response.status(200).type("application/json").entity("success").build();
    }
}
