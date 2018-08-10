package edu.study.test;

import edu.study.giya.entity.Role;
import edu.study.giya.service.RoleService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRole {
    private Logger logger = LoggerFactory.getLogger(TestRole.class);

    @Test
    public void testRole() {
        logger.debug("start test role");
        Role role = new Role();
        role.setId(1);
        role.setName("test");
        role.setNickname("test");
        role.setPower(2048);
        role.setType("system");
        RoleService roleService = new RoleService();
        roleService.addRole(role);
        logger.debug("finish.");


        logger.debug("start user role");
         role = new Role();
        role.setName("user");
        role.setNickname("user");
        role.setPower(1);
        role.setType("user");
         roleService = new RoleService();
        roleService.addRole(role);
        logger.debug("finish.");

        logger.debug("start user role");
        role = new Role();
        role.setName("user");
        role.setNickname("user");
        role.setPower(300);
        role.setType("charity");
        roleService = new RoleService();
        roleService.addRole(role);
        logger.debug("finish.");

        logger.debug("start admin role");
         role = new Role();
        role.setName("admin");
        role.setNickname("admin");
        role.setPower(1024);
        role.setType("base");
        roleService.addRole(role);
        logger.debug("finish.");
    }
}
