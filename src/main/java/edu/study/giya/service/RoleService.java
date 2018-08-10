package edu.study.giya.service;

import edu.study.giya.dao.jpa.SimpleBaseCrudDao;
import edu.study.giya.entity.Role;

import javax.ejb.Stateless;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class RoleService {
    private SimpleBaseCrudDao<Role> simpleBaseCrudDao = new SimpleBaseCrudDao<Role>(Role.class);

    public List<Role> getRoleByPower(Integer power) {
        //TODO
        List<Role> result=new  LinkedList<>();
        List<Role> temp=simpleBaseCrudDao.getAll();
        for(Role r:temp){
            if(r.getPower()==power){
                result.add(r);
            }
        }
        return result;
    }

    public Role getRoleById(Integer id) {
        //Role role = simpleBaseCrudDao.get(id + "");
        Role role=simpleBaseCrudDao.findById(id);
        if (role == null)
            role = new Role();
        return role;
    }




    public boolean modifyRole(Role role) {
        //simpleBaseCrudDao.modify(role.getId(),"nickname",role.getNickname());
        simpleBaseCrudDao.modify(role);
        simpleBaseCrudDao.flush();
        return true;
    }

    public boolean addRole(Role role) {
        simpleBaseCrudDao.add(role);
        simpleBaseCrudDao.flush();
        return true;
    }
}
