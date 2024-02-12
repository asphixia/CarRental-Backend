package org.example.config;

import jakarta.persistence.EntityManagerFactory;
import org.example.model.Roles;
import org.example.model.User;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = new User("william@gmail.com", "1234");
            User user1 = new User("william1@gmail.com", "1234");
            User user2 = new User("william2@gmail.com", "1234");
            User user3 = new User("william3@gmail.com", "1234");
            User user4 = new User("william4@gmail.com", "1234");

            Roles role = new Roles("user");
            Roles role1 = new Roles("admin");

            user.addRole(role1);
            user1.addRole(role1);
            user2.addRole(role);
            user3.addRole(role);
            user4.addRole(role);

            em.persist(role);
            em.persist(role1);
            em.persist(user);
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(user4);

            em.getTransaction().commit();
        }
    }




}
