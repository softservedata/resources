package org.registrator.community.service;

import org.registrator.community.dto.json.UsersDataNotConfJson;

public interface EmailConfirmService {

	void sendConfirmEmailFirstTime(String userEmail, String baseLink);

	Boolean confirmEmail(String token);

	String actionsWithNotConfirmedUsers(UsersDataNotConfJson usersDataNotConfJson);
	
    void sendConfirmEmailAgain(String login);


}
