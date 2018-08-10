package edu.study.giya.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class FundaraiserView {

    private String username;
    private String nickname;

    public void test() {

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Welcome " + username + " " + nickname));
    }
}
