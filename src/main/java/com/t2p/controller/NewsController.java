package com.t2p.controller;

import com.t2p.persistence.entity.News;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;



@Controller
@RequestMapping("/news")
public class NewsController extends IcsseController {

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('WRITE_DATA')")
    public String addNews() {
        return "news/addNewsPage";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('WRITE_DATA')")
    public String submitAddNews(
            @RequestParam(name = "title", defaultValue = "EMPTY") String title,
            @RequestParam(name = "content", defaultValue = "EMPTY") String content) {
        ResponseEntity.ok().build();
        try {
            News newNews = newsService.addNews(new News(title, content,
                    Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                    14, 1, "news"));
            logger.log(Level.INFO, ">>> Write a news is success");
            return String.format("redirect:/news/%d", newNews.getId());
        } catch (Exception e) {
            logger.log(Level.WARNING, ">>> Write a news is failure" + e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/{newsId}")
    public String newsaNews(@PathVariable("newsId") String newsId, Model model) {
        try {
            int id = Integer.parseInt(newsId);
            News anews = newsService.getNewsById(id);
            List<News> listNews = new ArrayList<>(allNews());
            listNews.remove(anews);
            model.addAttribute("title", anews.getTitle());
            model.addAttribute("anews", anews);
            model.addAttribute("news", listNews);
        } catch (Exception e) {
            return "redirect:/home";
        }
        return "news/viewNewsPage";
    }

    @PostMapping("/delete/{newsId}")
    @PreAuthorize("hasAuthority('WRITE_DATA')")
    public ResponseEntity deleteNews(@PathVariable("newsId") String newsId) {
        try {
            int id = Integer.parseInt(newsId);
            newsService.deleteNewsById(id);

            logger.log(Level.INFO, ">>> Delete news is success");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.log(Level.WARNING, ">>> Delete news is failure");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @GetMapping("/edit/{newsId}")
    @PreAuthorize("hasAuthority('WRITE_DATA')")
    public String editaNews(@PathVariable("newsId") String newsId, Model model) {
        newsaNews(newsId, model);
        return "news/editNewsPage";
    }

    @PostMapping("/edit/{newsId}")
    @PreAuthorize("hasAuthority('WRITE_DATA')")
    public String submitEditNews(@PathVariable("newsId") String newsId,
                                 @RequestParam(name = "title", defaultValue = "EMPTY") String title,
                                 @RequestParam(name = "content", defaultValue = "EMPTY") String content) {
        try {
            int id = Integer.parseInt(newsId);
            News oldNews = newsService.getNewsById(id);
            oldNews.setTitle(title);
            oldNews.setContent(content);
            oldNews.setLastModified(Calendar.getInstance().getTime());
            newsService.editNews(oldNews);
            logger.log(Level.INFO, ">>> Edit a news is success");
            return String.format("redirect:/news/%s", newsId);
        } catch (Exception e) {
            logger.log(Level.WARNING, ">>> Edit a news is failure");
            return "redirect:/";
        }
    }
}
