package ru.karpov.NewsPublic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.karpov.NewsPublic.models.News;
import ru.karpov.NewsPublic.repos.userRepo;
import ru.karpov.NewsPublic.repos.newsRepo;
import ru.karpov.NewsPublic.repos.subscribeRepo;

@Controller
public class workController {
    private userRepo userRepo;
    private newsRepo newsRepo;
    private subscribeRepo subscribeRepol;

    @Autowired
    public workController(final userRepo userRepo, final newsRepo newsRepo, final subscribeRepo subscribeRepo)
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