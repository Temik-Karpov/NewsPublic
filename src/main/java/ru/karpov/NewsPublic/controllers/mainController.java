package ru.karpov.NewsPublic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class mainController {

    @GetMapping("/")
    public String getMainPage()
    {
        return "mainPage";
    }

    @GetMapping("/addNewsPage")
    public String getAddNewsPage()
    {

        return "addNewsPage";
    }

    @GetMapping("/authProfilePage")
    public String getAuthProfilePage()
    {
        return "authProfilePage";
    }

    @GetMapping("/subscriptionsPage")
    public String getSubscriptionsPage()
    {
        return "subscriptionsPage";
    }

    @GetMapping("/registrationPage")
    public String getRegistrationPage()
    {
        return "registrationPage";
    }

    @GetMapping("/lgoInPage")
    public String getLogInPage()
    {
        return "logInPage";
    }

    @GetMapping("/profilePage")
    public String getProfilePage()
    {
        return "profilePage";
    }

    @GetMapping("/newsPage")
    public String getNewsPage()
    {
        return "newsPage";
    }


}