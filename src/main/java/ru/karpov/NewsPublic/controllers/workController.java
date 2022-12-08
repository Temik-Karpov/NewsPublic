package ru.karpov.NewsPublic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karpov.NewsPublic.models.News;
import ru.karpov.NewsPublic.repos.newsRepo;
import ru.karpov.NewsPublic.repos.subscribeRepo;
import ru.karpov.NewsPublic.repos.userRepo;

import java.time.Instant;
import java.util.Date;

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

    @PostMapping("/addNews")
    public String addNews(@RequestParam("Title") String title, @RequestParam("text") String text,
                          @RequestParam("category") String category,
                          Model model)
    {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String id = authentication.getName();

        News news = new News();
        news.setName(title);
        news.setCategory(category);
        news.setText(text);
        news.setDate(Date.from(Instant.now()));
        news.setAuthorName(userRepo.findUserById(id).getName());
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
