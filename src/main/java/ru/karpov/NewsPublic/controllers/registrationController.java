package ru.karpov.NewsPublic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.karpov.NewsPublic.models.userInfo;
import ru.karpov.NewsPublic.repos.userRepo;

@Controller
public class registrationController {

    private userRepo userRepo;

    @Autowired
    public registrationController(final userRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    public void addUser(userInfo user)
    {
        userRepo.save(user);
    }
}