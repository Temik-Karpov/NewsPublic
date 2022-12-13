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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karpov.NewsPublic.models.Categories;
import ru.karpov.NewsPublic.models.News;
import ru.karpov.NewsPublic.models.userInfo;
import ru.karpov.NewsPublic.repos.newsRepo;
import ru.karpov.NewsPublic.repos.userRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Controller
public class mainController {

    private userRepo userRepo;
    private newsRepo newsRepo;

    @Autowired
    void MainController(userRepo userRepo, newsRepo newsRepo)
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
            model.addAttribute("publications", newsRepo.findNewsByAuthorName(authUser.getName()));
            return "authProfilePage";
        }
        return "addUserInfoPage";
    }

    @GetMapping("/subscriptionsPage")
    public String getSubscriptionsPage()
    {
        return "subscriptionsPage";
    }

    @GetMapping("/profilePage/{username}")
    public String getProfilePage(@PathVariable("username") String username, Model model)
    {
        userInfo user = userRepo.findUserByName(username);
        model.addAttribute("name", user.getName());
        model.addAttribute("age", user.getAge());
        model.addAttribute("description", user.getDescription());
        model.addAttribute("averageMark", user.getAverageMark());
        model.addAttribute("publications", newsRepo.findNewsByAuthorName(user.getName()));
        return "profilePage";
    }

    @GetMapping("/newsPage")
    public String getNewsPage(Model model)
    {

        return "newsPage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "mainPage";
    }

    @PostMapping("/reloadAuthProfilePage")
    public String reloadAuthProfilePage(@RequestParam("category") String category, Model model)
    {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String id = authentication.getName();
        final userInfo authUser = userRepo.findUserById(id);
        model.addAttribute("name", authUser.getName());
        model.addAttribute("age", authUser.getAge());
        model.addAttribute("description", authUser.getDescription());
        model.addAttribute("averageMark", authUser.getAverageMark());
        switch(category)
        {
            case "Sport":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Sport, authUser.getName()));
                break;
            case "Economic":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Economic, authUser.getName()));;
                break;
            case "Science":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Science, authUser.getName()));;
                break;
            case "Politics":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Politics, authUser.getName()));;
                break;
        }
        return "authProfilePage";
    }

    @PostMapping("/reloadProfilePage/{name}")
    public String reloadProfilePage(@PathVariable("name") String username,
                                    @RequestParam("category") String category,
                                    Model model)
    {
        switch(category)
        {
            case "Sport":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Sport, username));
                break;
            case "Economic":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Economic, username));;
                break;
            case "Science":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Science, username));;
                break;
            case "Politics":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Politics, username));;
                break;
        }
        return "profilePage";
    }
}