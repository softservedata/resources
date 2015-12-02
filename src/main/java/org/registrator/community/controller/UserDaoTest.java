package org.registrator.community.controller;

import org.registrator.community.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public interface UserDaoTest {

    public List<User> getPortionOfUser(int from,int quantity);

    public List<User> getAllEntity();

    public User getEntityById(int id);

    public User update(User user);
}

