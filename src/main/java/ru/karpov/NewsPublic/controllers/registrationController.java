package ru.karpov.NewsPublic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karpov.NewsPublic.models.News;
import ru.karpov.NewsPublic.models.userInfo;
import ru.karpov.NewsPublic.repos.newsRepo;
import ru.karpov.NewsPublic.repos.userRepo;

import java.util.ArrayList;
import java.util.List;

@Controller
public class registrationController {
    private userRepo userRepo;
    private newsRepo newsRepo;

    @Autowired
    void RegistrationController(final userRepo userRepo, final newsRepo newsRepo)
    {
        this.userRepo = userRepo;
        this.newsRepo = newsRepo;
    }

    private boolean isAuth()
    {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
    }

    @PostMapping("/addUserInfo")
    public String addUserInfo(@RequestParam("username") String username,
                              @RequestParam("age") Integer age,
                              @RequestParam("description") String description,
                              Model model)
    {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String id = authentication.getName();
        List<News> news = new ArrayList<>();
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        if(userRepo.findUserById(id) != null)
        {
            news = newsRepo.findNewsByAuthorName(userRepo.findUserById(id).getName());
            userRepo.delete(userRepo.findUserById(id));
        }
        if(username.isEmpty() || description.isEmpty() || age == null)
        {
            model.addAttribute("nullError", 1);
            return "addUserInfoPage";
        }
        if(age > 100 || age < 18) {
            model.addAttribute("ageError", 1);
            return "addUserInfoPage";
        }
        for (News publication: news) {
            publication.setAuthorName(username);
            newsRepo.save(publication);
        }
        userInfo newUser = new userInfo();
        newUser.setName(username);
        newUser.setId(id);
        newUser.setDescription(description);
        newUser.setAge(age);
        newUser.setCountOfMarks(0);
        newUser.setSummaryOfMarks(0);
        userRepo.save(newUser);
        model.addAttribute("publications", newsRepo.findAll());
        model.addAttribute("isPub", newsRepo.findAll().size() == 0 ? 1 : 0);
        return "mainPage";
    }
}