package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.exception.AuthorizationException;
import org.example.model.Roles;
import org.example.model.User;

import java.util.List;

public class UserDAO implements IDAO<User, String> {

    private static UserDAO instance;
    private static EntityManagerFactory emf;

    public static UserDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserDAO();
        }
        return instance;
    }

    public User getVerifiedUser(String username, String password) throws AuthorizationException {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, username);

            if (user == null || !user.verifyPassword(password)) {
                throw new AuthorizationException(401, "Invalid user name or password");
            }
            em.getTransaction().commit();
            return user;
        }
    }

    public User registerUser(String username, String password, String user_role) throws AuthorizationException {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            User user = new User(username, password);
            Roles role = em.find(Roles.class, user_role);

            if (role == null) {
                role = createRole(user_role);
            }

            user.addRole(role);
            em.persist(user);
            em.getTransaction().commit();
            return user;
        } catch (Exception e) {
            throw new AuthorizationException(400, "Username already exists");
        }
    }

    public Roles createRole(String role) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Roles newRole = new Roles(role);
            em.persist(newRole);
            em.getTransaction().commit();
            return newRole;
        }
    }

    @Override
    public User read(String userName) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, userName);
            em.getTransaction().commit();
            return user;
        }
    }

    @Override
    public List<User> readAll() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
            em.getTransaction().commit();
            return users;
        }
    }

    @Override
    public User create(User user) {
        throw new UnsupportedOperationException("Use register instead");
    }

    @Override
    public User update(String userName, User user) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User userToUpdate = em.find(User.class, userName);
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setUserPassword(user.getUserPassword());
            em.getTransaction().commit();
            return userToUpdate;
        }
    }

    @Override
    public void delete(String userName) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, userName);
            em.remove(user);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(String userName) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, userName);
            em.getTransaction().commit();
            return user != null;
        }
    }
}
