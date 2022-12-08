package ru.karpov.NewsPublic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karpov.NewsPublic.models.Categories;
import ru.karpov.NewsPublic.models.News;
import ru.karpov.NewsPublic.models.userInfo;
import ru.karpov.NewsPublic.repos.newsRepo;
import ru.karpov.NewsPublic.repos.userRepo;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Controller
public class mainController {

    private userRepo userRepo;
    private newsRepo newsRepo;

    @Autowired
    public mainController(userRepo userRepo, newsRepo newsRepo)
    {
        this.newsRepo = newsRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String getMainPage(Model model, @PageableDefault(sort={"id"}, direction = Sort.Direction.DESC) Pageable pageable)
    {
        Page<News> publications;
        newsRepo.findAll().get(0).setDate(new GregorianCalendar(2022, Calendar.JANUARY, 7).getTime());
        newsRepo.findAll().get(1).setDate(new GregorianCalendar(2022, Calendar.JANUARY, 7).getTime());
        model.addAttribute("publications", newsRepo.findAll(pageable));
        return "mainPage";
    }

    @PostMapping("/reloadMain")
    public String reloadMainPage(@RequestParam("category") String category, Model model)
    {
        switch(category)
        {
            case "Sport":
                model.addAttribute("publications", newsRepo.findNewsByCategory(Categories.Sport));
                break;
            case "Economic":
                model.addAttribute("publications", newsRepo.findNewsByCategory(Categories.Economic));
                break;
            case "Science":
                model.addAttribute("publications", newsRepo.findNewsByCategory(Categories.Science));
                break;
            case "Politics":
                model.addAttribute("publications", newsRepo.findNewsByCategory(Categories.Politics));
                break;
        }
        return "mainPage";
    }

    @GetMapping("/addNewsPage")
    public String getAddNewsPage()
    {
        return "addNewsPage";
    }

    @GetMapping("/authProfilePage")
    public String getAuthProfilePage(Model model)
    {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String id = authentication.getName();
        final userInfo authUser = userRepo.findUserById(id);
        if(authUser != null)
        {
            model.addAttribute("name", authUser.getName());
            model.addAttribute("age", authUser.getAge());
            model.addAttribute("description", authUser.getDescription());
            model.addAttribute("averageMark", authUser.getAverageMark());
            return "authProfilePage";
        }
        return "mainPage";
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