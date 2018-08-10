/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.study.test;
import edu.study.giya.entity.UserInfo;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author gwd
 */
public class Main {
    private static final String PERSISTENCE_UNIT_NAME = "justgiving";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        // read the existing entries and write to console
        Query q = em.createQuery("select * from userinfo");
        List<UserInfo> todoList = q.getResultList();
        for (UserInfo todo : todoList) {
            System.out.println(todo);
        }
        System.out.println("Size: " + todoList.size());

        // create new todo
        em.getTransaction().begin();
        UserInfo todo = new UserInfo();
        todo.setNickname("This is a test"); 
        todo.setUsername("This is a test");
        em.persist(todo);
        em.getTransaction().commit();

        em.close();
    }
}
