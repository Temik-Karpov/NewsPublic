package ru.karpov.NewsPublic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.karpov.NewsPublic.models.News;
import ru.karpov.NewsPublic.repos.userRepo;
import ru.karpov.NewsPublic.repos.newsRepo;
import ru.karpov.NewsPublic.repos.subscribeRepo;

@Controller
public class workController {
    private userRepo userRepo;
    private newsRepo newsRepo;
    private subscribeRepo subscribeRepo;

    @Autowired
    public void WorkController(final userRepo userRepo, final newsRepo newsRepo, final subscribeRepo subscribeRepo)
    {
        this.newsRepo = newsRepo;
        this.userRepo = userRepo;
        this.subscribeRepo = subscribeRepo;
    }

    @PostMapping("/addNews")
    public String addNews(@ModelAttribute("news") News news, Model model)
    {
        newsRepo.save(news);
        return "mainPage";
    }

    @PostMapping("/deleteNews")
    public String deleteNews(int id, Model model)
    {
        News news = newsRepo.findNewsById(id);
        newsRepo.delete(news);
        return "mainPage";
    }

    @PostMapping("/editNews")
    public String editNews(@ModelAttribute("news") News news, Model model)
    {

        return "mainPage";
    }

    @PostMapping("/subscribeUser")
    public String subscribeUser(int id, Model model)
    {

        return "subscriptionsPage";
    }

    @PostMapping("/unsubscribeUser")
    public String unsubscribeUser(int id, Model model)
    {
        return "subscriptionsPage";
    }


    @PostMapping("/rateNews")
    public String rateNews(@ModelAttribute("news") News news, int id, Model model)
    {
        return "";
    }

}