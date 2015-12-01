package org.registrator.community.controller;

import org.registrator.community.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public interface UserDaoTest {



    List<User> getAllEntity();

    public User getEntityById(int id);
}
