package ru.karpov.NewsPublic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.karpov.NewsPublic.models.News;
import ru.karpov.NewsPublic.models.Subscribe;
import ru.karpov.NewsPublic.models.userInfo;
import ru.karpov.NewsPublic.repos.newsRepo;
import ru.karpov.NewsPublic.repos.subscribeRepo;
import ru.karpov.NewsPublic.repos.userRepo;

import java.time.Instant;
import java.util.Date;

@Controller
public class workController {
    private userRepo userRepo;
    private newsRepo newsRepo;
    private subscribeRepo subscribeRepo;

    @Autowired
    void WorkController(final userRepo userRepo, final newsRepo newsRepo, final subscribeRepo subscribeRepo)
    {
        this.newsRepo = newsRepo;
        this.userRepo = userRepo;
        this.subscribeRepo = subscribeRepo;
    }

    private boolean isAuth()
    {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
    }

    @PostMapping("/addNews")
    public String addNews(@RequestParam("Title") String title, @RequestParam("text") String text,
                          @RequestParam("category") String category,
                          Model model)
    {
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        if(text.isEmpty() || title.isEmpty())
        {
            model.addAttribute("nullError", 1);
            return "addNewsPage";
        }
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String id = authentication.getName();
        News news = new News();
        news.setName(title);
        news.setCategory(category);
        news.setText(text);
        news.setDate(Date.from(Instant.now()));
        news.setAuthorName(userRepo.findUserById(id).getName());
        newsRepo.save(news);
        model.addAttribute("publications", newsRepo.findAll());
        return "mainPage";
    }

    @GetMapping("/deleteNews/{id}")
    public String deleteNews(@PathVariable("id") Integer id, Model model)
    {
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        News news = newsRepo.findNewsById(id);
        newsRepo.delete(news);
        model.addAttribute("publications", newsRepo.findAll());
        model.addAttribute("isPub", newsRepo.findAll().size() == 0 ? 1 : 0);
        return "mainPage";
    }

    @GetMapping("/editNews/{id}")
    public String editNews(@PathVariable("id") Integer id, Model model)
    {
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        model.addAttribute("publication", newsRepo.findNewsById(id));
        newsRepo.delete(newsRepo.findNewsById(id));
        return "addNewsPage";
    }

    @GetMapping("/unsubscribeUser/{username}")
    public String unsubscribeUser(@PathVariable("username") String username, Model model)
    {
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        userInfo user = userRepo.findUserByName(username);
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String id = authentication.getName();
        subscribeRepo.delete(subscribeRepo.findSubscribeByIdUserSubscribeAndIdUser(user.getId(), id));
        model.addAttribute("publications", newsRepo.findAll());
        model.addAttribute("isPub", newsRepo.findAll().size() == 0 ? 1 : 0);
        return "mainPage";
    }

    @PostMapping("/rateNews/{id}")
    public String rateNews(@PathVariable("id") Integer id, @RequestParam("mark") Integer mark, Model model)
    {
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        userInfo user = userRepo.findUserByName(newsRepo.findNewsById(id).getAuthorName());
        user.setCountOfMarks(user.getCountOfMarks() + 1);
        user.setSummaryOfMarks(user.getSummaryOfMarks() + mark);
        userRepo.save(user);
        model.addAttribute("publications", newsRepo.findAll());
        model.addAttribute("isPub", newsRepo.findAll().size() == 0 ? 1 : 0);
        return "mainPage";
    }

    @GetMapping("/subscribeUser/{username}")
    public String subscribeUser(@PathVariable("username") String username, Model model)
    {
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        userInfo user = userRepo.findUserByName(username);
        Subscribe subscribe = new Subscribe();
        subscribe.setIdUserSubscribe(user.getId());
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String id = authentication.getName();
        subscribe.setIdUser(id);
        subscribeRepo.save(subscribe);
        model.addAttribute("publications", newsRepo.findAll());
        model.addAttribute("isPub", newsRepo.findAll().size() == 0 ? 1 : 0);
        return "mainPage";
    }

}
