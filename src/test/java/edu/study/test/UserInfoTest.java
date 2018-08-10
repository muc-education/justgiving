package edu.study.test;

import edu.study.giya.entity.UserInfo;
import edu.study.giya.service.UserInfoService;
import org.junit.Test;

public class UserInfoTest {
    @Test
    public void addUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("test");
        userInfo.setNickname("test test");
        userInfo.setPassword("abc");
        userInfo.setEmail("test@jsutgiving.com");
        userInfo.setMoney(1000.0);
        userInfo.setRoleId(1);
        UserInfoService userInfoService = new UserInfoService();
        userInfoService.registerUserInfo(userInfo);

    }
}
