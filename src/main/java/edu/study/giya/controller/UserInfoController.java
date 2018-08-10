package edu.study.giya.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.study.giya.entity.UserInfo;
import edu.study.giya.service.RoleService;
import edu.study.giya.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import java.io.Serializable;
import java.sql.Timestamp;

@ManagedBean(name="userInfo",eager = true)
@SessionScoped
public class UserInfoController implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(UserInfoController.class.getName());

    @Inject
    private UserInfoService userInfoService;
    @Inject
    private RoleService roleService;

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String nickname;


    public void validatePassword(ComponentSystemEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();
        // get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmpassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();
        // Let required="true" do its job.
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }
        if (!password.equals(confirmPassword)) {
            FacesMessage msg = new FacesMessage("Confirm password does not match password");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(passwordId, msg);
            facesContext.renderResponse();
        }
        if (userInfoService.findUserByEmail(email) != null) {
            FacesMessage msg = new FacesMessage("User with this e-mail already exists");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(passwordId, msg);
            facesContext.renderResponse();
        }
    }

    public String login(){
        if ("abc".equalsIgnoreCase(username) && "abc".equalsIgnoreCase(password)) {
            // message ="Successfully logged-in.";
            return "success";
        } else {
            // message ="Wrong credentials.";
            return "login";
        }
    }

    public String register() {
        UserInfo user = new UserInfo();
        if (!user.check()) {
            logger.warn("user register information was not validating." +
                    "" +
                    "" +
                    "");
            return "fail";
        }
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        user.setNickname(nickname);
        user.setEndTime(new java.sql.Timestamp(new java.util.Date().getTime()));
        user.setRoleId(2);
        userInfoService.registerUserInfo(user);
        logger.info("New user created with e-mail: " + email + " and username: " + username);
        return "register-done";
    }


    public String index(){
        // set default user
        Integer userId=1;
        UserInfo userInfo=userInfoService.findUserById(userId);
        JsonArray data=new JsonArray();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("username",userInfo.getUsername());
        jsonObject.addProperty("nickname",userInfo.getNickname());
        jsonObject.addProperty("id", userInfo.getId());
        jsonObject.addProperty("roleId", userInfo.getRoleId());
        // Role role=roleService.getRoleById(user)
        return "login";
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
