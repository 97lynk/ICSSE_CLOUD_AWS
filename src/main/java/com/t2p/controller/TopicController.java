package com.t2p.controller;

import com.t2p.persistence.entity.News;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.logging.Level;


@Controller
@RequestMapping({"/", ""})
public class TopicController extends IcsseController {

    @GetMapping
    public String index(Model model) {
        String s;
        model.addAttribute("topic", newsService.getNewsByUrl("home"));


        return "topic/viewTopicPage";
    }

    @RequestMapping("/{url}")
    public String newsInType(@PathVariable(name = "url") String urlMenu, Model model, HttpServletRequest req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Enumeration enumeration = req.getSession().getAttributeNames();
        int i = 1;
        String str = "";
        while (enumeration.hasMoreElements()){
            String name = (String) enumeration.nextElement();
            str = i + ". " + name + " : " + req.getSession().getAttribute(name).toString() +"\n";
        }

        model.addAttribute("text", authentication.toString() + "\n"
        + str);
        try {
            News topic = newsService.getNewsByUrl(urlMenu);
            model.addAttribute("topic", topic);
            model.addAttribute("title", topic.getTypeOfNews().getType_name());

//            model.addAttribute("user", login());
        } catch (Exception e) {
            //return "error/404";
        }
        return "topic/viewTopicPage";
    }

    @GetMapping("/edit/{url}")
    @PreAuthorize("hasAuthority('WRITE_DATA')")
    public String editPage(@PathVariable("url") String url, Model model) {
        try {
            News webPage = newsService.getNewsByUrl(url);
            model.addAttribute("title", webPage.getTypeOfNews().getType_name());
            model.addAttribute("topic", webPage);

        } catch (Exception e) {
//            return "redirect:/home";
        }
        return "topic/editTopicPage";
    }

    @PostMapping("/edit/{url}")
    @PreAuthorize("hasAuthority('WRITE_DATA')")
    public String submitChange(@PathVariable("url") String url, @RequestParam("content") String content, Model model) {
        try {
            News webPage = newsService.getNewsByUrl(url);
            webPage.setContent(content);
            newsService.changeEditWebPage(webPage);
            model.addAttribute("title", webPage.getTypeOfNews().getType_name());
            model.addAttribute("topic", webPage);
            logger.log(Level.INFO, ">>> Edit web page (topic): {0} success", webPage.getTypeOfNews().getType_name());
        } catch (Exception e) {
            logger.log(Level.WARNING, ">>> Edit web page (topic): {0} failure", url);
            return "redirect:/home";
        }
        return String.format("redirect:/%s", url);
    }
}
