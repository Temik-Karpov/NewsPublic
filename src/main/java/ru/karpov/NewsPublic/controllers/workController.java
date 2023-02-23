package ru.karpov.NewsPublic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.karpov.NewsPublic.models.Mark;
import ru.karpov.NewsPublic.models.News;
import ru.karpov.NewsPublic.models.Subscribe;
import ru.karpov.NewsPublic.models.userInfo;
import ru.karpov.NewsPublic.repos.markRepo;
import ru.karpov.NewsPublic.repos.newsRepo;
import ru.karpov.NewsPublic.repos.subscribeRepo;
import ru.karpov.NewsPublic.repos.userRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;

@Controller
public class workController {
    private userRepo userRepo;
    private newsRepo newsRepo;
    private subscribeRepo subscribeRepo;
    private markRepo markRepo;

    @Autowired
    public void WorkController(final userRepo userRepo, final newsRepo newsRepo, final subscribeRepo subscribeRepo,
                               final markRepo markRepo)
    {
        this.newsRepo = newsRepo;
        this.userRepo = userRepo;
        this.subscribeRepo = subscribeRepo;
        this.markRepo = markRepo;
    }

    private boolean isAuth()
    {
        return SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
    }

    @PostMapping("/addNews")
    public String addNews(@RequestParam("Title") String title, @RequestParam("text") String text,
                          @RequestParam("category") String category,
                          @RequestParam("photo") MultipartFile file,
                          Model model) throws IOException {
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        if(text.isEmpty() || title.isEmpty())
        {
            model.addAttribute("nullError", 1);
            model.addAttribute("publication", 0);
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

        if(file.getBytes().length > 0) {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get("D:/temik/Work/Data/NewsPublic/NewsPublic/src/main/resources/static/images",
                    file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            news.setImageUrl("/images/" + file.getOriginalFilename());
        }


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

    @PostMapping("/subscribeUser")
    public String subscribeUser(int id, Model model)
    {
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        return "subscriptionsPage";
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

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String idUser = authentication.getName();
        Mark newMark = new Mark();
        newMark.setIdUser(idUser);
        newMark.setIsNews(id);
        newMark.setMark(mark);
        markRepo.save(newMark);
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