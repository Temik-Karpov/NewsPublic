package ru.karpov.NewsPublicTest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.karpov.NewsPublicTest.models.userInfo;
import ru.karpov.NewsPublicTest.repos.UserRepo;

@Controller
public class registrationController {

    private UserRepo userRepo;

    @Autowired
    public RegistrationController(final UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    public void addUser(userInfo user)
    {
        userRepo.save(user);
    }
}