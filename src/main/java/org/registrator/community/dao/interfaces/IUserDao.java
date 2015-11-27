package org.registrator.community.dao.interfaces;

import java.util.List;

import org.registrator.community.entity.User;

public interface IUserDao {
	public List<User> getUsersListByPortions(int from, int quantity);
}
