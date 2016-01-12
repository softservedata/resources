package org.registrator.community.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.JsonObject;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterRestController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/check-username-is-available", method = RequestMethod.GET)
    public @ResponseBody
    String checkUsernameIsAvailable(@RequestParam String login){
        if(userService.checkUsernameNotExistInDB(login)){
            return "";
        }
        return "Username is already taken. Please, use another one";
    }
}
