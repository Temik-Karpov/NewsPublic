package ru.karpov.NewsPublic.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karpov.NewsPublic.models.Categories;
import ru.karpov.NewsPublic.models.Subscribe;
import ru.karpov.NewsPublic.models.userInfo;
import ru.karpov.NewsPublic.repos.newsRepo;
import ru.karpov.NewsPublic.repos.subscribeRepo;
import ru.karpov.NewsPublic.repos.userRepo;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class mainController {

    private userRepo userRepo;
    private newsRepo newsRepo;
    private subscribeRepo subscribeRepo;

    @Autowired
    public mainController(userRepo userRepo, newsRepo newsRepo, subscribeRepo subscribeRepo)
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

    @GetMapping("/")
    public String getMainPage(Model model)
    {
        model.addAttribute("publications", newsRepo.findAll());
        model.addAttribute("isPub", newsRepo.findAll().size() == 0 ? 1 : 0);
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        return "mainPage";
    }

    @PostMapping("/reloadMain")
    public String reloadMainPage(@RequestParam("category") String category, Model model)
    {
        switch(category)
        {
            case "All":
                model.addAttribute("publications", newsRepo.findAll());
                model.addAttribute("isPub", newsRepo.findAll().size() == 0 ? 1 : 0);
                break;
            case "Sport":
                model.addAttribute("publications", newsRepo.findNewsByCategory(Categories.Sport));
                model.addAttribute("isPub", newsRepo.findNewsByCategory(Categories.Sport).size() == 0 ?
                        1 : 0);
                break;
            case "Economic":
                model.addAttribute("publications", newsRepo.findNewsByCategory(Categories.Economic));
                model.addAttribute("isPub", newsRepo.findNewsByCategory(Categories.Economic).size() == 0 ?
                        1 : 0);
                break;
            case "Science":
                model.addAttribute("publications", newsRepo.findNewsByCategory(Categories.Science));
                model.addAttribute("isPub", newsRepo.findNewsByCategory(Categories.Science).size() == 0 ?
                        1 : 0);
                break;
            case "Politics":
                model.addAttribute("publications", newsRepo.findNewsByCategory(Categories.Politics));
                model.addAttribute("isPub", newsRepo.findNewsByCategory(Categories.Politics).size() == 0 ?
                        1 : 0);
                break;
            case "Culture":
                model.addAttribute("publications", newsRepo.findNewsByCategory(Categories.Culture));
                model.addAttribute("isPub", newsRepo.findNewsByCategory(Categories.Culture).size() == 0 ?
                        1 : 0);
                break;
        }
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        return "mainPage";
    }

    @GetMapping("/addNewsPage")
    public String getAddNewsPage(Model model)
    {
        model.addAttribute("publication", 0);
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        return "addNewsPage";
    }

    @GetMapping("/authProfilePage")
    public String getAuthProfilePage(Model model)
    {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String id = authentication.getName();
        final userInfo authUser = userRepo.findUserById(id);
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        if(authUser != null)
        {
            model.addAttribute("name", authUser.getName());
            model.addAttribute("age", authUser.getAge());
            model.addAttribute("description", authUser.getDescription());
            model.addAttribute("averageMark", authUser.getCountOfMarks() == 0 ? 0 :
                    authUser.getSummaryOfMarks() / authUser.getCountOfMarks());
            model.addAttribute("publications", newsRepo.findNewsByAuthorName(authUser.getName()));
            model.addAttribute("isPub", newsRepo.findNewsByAuthorName(authUser.getName()).size() == 0 ?
                    1 : 0);
            return "authProfilePage";
        }
        return "addUserInfoPage";
    }

    @GetMapping("/addUserInfoPage")
    public String getAddUserInfoPage()
    {
        return "addUserInfoPage";
    }

    @GetMapping("/subscriptionsPage")
    public String getSubscriptionsPage(Model model)
    {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String id = authentication.getName();
        final List<userInfo> subscribeUsers = new ArrayList<>();
        for (Subscribe subscribe : subscribeRepo.findSubscribeByIdUser(id))
        {
            subscribeUsers.add(userRepo.findUserById(subscribe.getIdUserSubscribe()));
        }
        model.addAttribute("noSubscribes", subscribeUsers.size() == 0 ? 1:0);
        model.addAttribute("subscribes", subscribeUsers);
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        return "subscriptionsPage";
    }

    @GetMapping("/profilePage/{username}")
    public String getProfilePage(@PathVariable("username") String username, Model model)
    {
        userInfo user = userRepo.findUserByName(username);
        model.addAttribute("name", user.getName());
        model.addAttribute("age", user.getAge());
        model.addAttribute("description", user.getDescription());
        model.addAttribute("averageMark", user.getCountOfMarks() == 0 ? 0 :
                user.getSummaryOfMarks() / user.getCountOfMarks());
        model.addAttribute("publications", newsRepo.findNewsByAuthorName(user.getName()));
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String id = authentication.getName();
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        if(id.equals(user.getId()))
        {
            return "authProfilePage";
        }
        if(subscribeRepo.findSubscribeByIdUserSubscribeAndIdUser(user.getId(), id) != null)
        {
            model.addAttribute("isSub", 1);
        }
        else
        {
            model.addAttribute("isSub", 0);
        }
        return "profilePage";
    }

    @GetMapping("/newsPage/{id}")
    public String getNewsPage(@PathVariable("id") Integer id,
                              Model model)
    {
        model.addAttribute("news", newsRepo.findNewsById(id));
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String idAuth = authentication.getName();
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        if(userRepo.findUserByName(newsRepo.findNewsById(id).getAuthorName()).getId().equals(idAuth))
        {
            model.addAttribute("edit", 1);
        }
        else
        {
            model.addAttribute("edit", 0);
        }
        return "newsPage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) throws ServletException {
        request.logout();
        model.addAttribute("publications", newsRepo.findAll());
        model.addAttribute("isPub", newsRepo.findAll() == null ? 1 : 0);
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
        model.addAttribute("averageMark", authUser.getCountOfMarks() == 0 ? 0 :
                authUser.getSummaryOfMarks() / authUser.getCountOfMarks());
        switch(category)
        {
            case "All":
                model.addAttribute("publications", newsRepo.findNewsByAuthorName(authUser.getName()));
                model.addAttribute("isPub", newsRepo.findAll().size() == 0 ? 1 : 0);
                break;
            case "Sport":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Sport, authUser.getName()));
                model.addAttribute("isPub", newsRepo.findNewsByCategoryAndAndAuthorName
                        (Categories.Sport, authUser.getName()).size() == 0 ? 1 : 0);
                break;
            case "Economic":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Economic, authUser.getName()));
                model.addAttribute("isPub", newsRepo.findNewsByCategoryAndAndAuthorName
                        (Categories.Economic, authUser.getName()).size() == 0 ? 1 : 0);
                break;
            case "Science":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Science, authUser.getName()));
                model.addAttribute("isPub", newsRepo.findNewsByCategoryAndAndAuthorName
                        (Categories.Science, authUser.getName()).size() == 0 ? 1 : 0);
                break;
            case "Politics":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Politics, authUser.getName()));
                model.addAttribute("isPub", newsRepo.findNewsByCategoryAndAndAuthorName
                        (Categories.Politics, authUser.getName()).size() == 0 ? 1 : 0);
                break;
            case "Culture":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Culture,
                                authUser.getName()));
                model.addAttribute("isPub", newsRepo.findNewsByCategoryAndAndAuthorName
                        (Categories.Culture, authUser.getName()).size() == 0 ? 1 : 0);
                break;
        }
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        return "authProfilePage";
    }
    @PostMapping("/reloadProfilePage/{name}")
    public String reloadProfilePage(@PathVariable("name") String username,
@RequestParam("category") String category,
                                    Model model)
    {
        switch(category)
        {
            case "All":
                model.addAttribute("publications", newsRepo.findNewsByAuthorName(username));
                break;
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
            case "Culture":
                model.addAttribute("publications",
                        newsRepo.findNewsByCategoryAndAndAuthorName(Categories.Culture, username));;
                break;
        }
        model.addAttribute("isAuth", isAuth() ? 0 : 1);
        return "profilePage";
    }

}