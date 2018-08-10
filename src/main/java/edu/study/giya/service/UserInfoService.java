package edu.study.giya.service;

import edu.study.giya.dao.jpa.SimpleBaseCrudDao;
import edu.study.giya.entity.Role;
import edu.study.giya.entity.UserInfo;
import edu.study.giya.entity.UserInfoRole;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UserInfoService {

    private SimpleBaseCrudDao<UserInfo> simpleBaseCrudDao = new SimpleBaseCrudDao<UserInfo>(UserInfo.class);

    public boolean checkUserInfo(String username, String password) {
        boolean result = false;
        String sql = "select * from userinfo where username=" + username;
        List<UserInfo> userInfo = simpleBaseCrudDao.queryByField(UserInfo.class, "username", username);
        if (userInfo.size() > 0) {
            for (UserInfo u :
                    userInfo) {
                if (u.getPassword().equals(password))
                    return result;
            }
        }
        return false;
    }

   /* public boolean checkUserInfo(String username, String password, Integer power) {
        boolean result = false;
        String sql = "select * from userinfo where username=" + username;
        List<UserInfo> userInfo = simpleBaseCrudDao.queryByField(UserInfo.class, "username", username);
        if (userInfo.size() > 0) {
            for (UserInfo u :
                    userInfo) {
                if (u.getPassword().equals(password)) {
                    return result;
                }

            }
        }
        return false;
    }*/

    public UserInfo findUserByEmail(String email) {
        String sql = "SELECT u FROM UserInfo u WHERE u.email = " + email;
        UserInfo result = simpleBaseCrudDao.jpaCrud(sql);
        return result;
    }

    public UserInfo findUserById(Integer id){
        UserInfo userInfo=simpleBaseCrudDao.findById(id);
        return userInfo;
    }

    public UserInfo findUserByName(String username) {
        String sql = "SELECT u FROM UserInfo u WHERE u.username = " + username;
        UserInfo result = simpleBaseCrudDao.jpaCrud(sql);
        return result;
    }

    public boolean registerUserInfo(UserInfo userInfo) {
        if (userInfo.getStartTime() == null)
            userInfo.setStartTime(new java.sql.Timestamp(new java.util.Date().getTime()));
        if (userInfo.getEndTime() == null)
            userInfo.setEndTime(new java.sql.Timestamp(new java.util.Date().getTime()));
        simpleBaseCrudDao.add(userInfo);
        return true;
    }

    public boolean modifyUserInfo(UserInfo userInfo) {
        simpleBaseCrudDao.modify(userInfo);
        return true;
    }


}
