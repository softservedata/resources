package org.registrator.community.controller;


import org.hibernate.SessionFactory;
import org.registrator.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDaoTest {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> getPortionOfUser(int from, int quantity) {
        return entityManager.createQuery("From User")
                .setFirstResult(from)
                .setMaxResults(quantity).getResultList();
    }

    @Override
    public List<User> getAllEntity() {
        return entityManager.createQuery("From User").getResultList();
    }

    @Override
    public User getEntityById(int id) {
        return entityManager.find(User.class,id);
    }

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }
}

