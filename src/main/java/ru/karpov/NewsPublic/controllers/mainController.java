package ru.karpov.NewsPublicTest.controllers;

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

    @PostMapping("/reloadPageNews")
    public String reloadPage()
    {
        //Смена категории новостей и обновление списка новостей
        return "mainPage";
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
        return "";
    }


}