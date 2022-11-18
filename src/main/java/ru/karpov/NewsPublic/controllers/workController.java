package ru.karpov.NewsPublicTest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.karpov.NewsPublicTest.models.News;
import ru.karpov.NewsPublicTest.repos.NewsRepo;
import ru.karpov.NewsPublicTest.repos.SubscribeRepo;
import ru.karpov.NewsPublicTest.repos.UserRepo;

@Controller
public class workController {
    private UserRepo userRepo;
    private NewsRepo newsRepo;
    private SubscribeRepo subscribeRepol;

    @Autowired
    public WorkController(final UserRepo userRepo, final NewsRepo newsRepo, final SubscribeRepo subscribeRepo)
    {
        this.newsRepo = newsRepo;
        this.userRepo = userRepo;
        this.subscribeRepol = subscribeRepo;
    }

    public String addNews(News news, Model model)
    {

        return "addNewsPage";
    }



}