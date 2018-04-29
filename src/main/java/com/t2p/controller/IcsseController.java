package com.t2p.controller;

import com.a97lynk.login.persistence.entity.User;
import com.a97lynk.login.service.IUserService;
import com.t2p.persistence.entity.News;
import com.t2p.persistence.entity.TypeOfNews;
import com.t2p.service.AmazonS3Service;
import com.t2p.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class IcsseController {
    protected static final Logger logger
            = Logger.getLogger(IcsseController.class.getName());


    @Autowired
    private AmazonS3Service amazonS3Service;

    @Autowired
    protected INewsService newsService;

    @Autowired
    protected IUserService userService;

    // data cho navbar menu trên
    @ModelAttribute("NavMenu")
    public List<TypeOfNews> allMenus() {
        return newsService.getAllTypes().subList(0, 13);
    }

    // data cho aside news bên phải
    @ModelAttribute("news")
    public List<News> allNews() {
        return newsService.getAllNewsByTypeId(14).stream().sorted(new Comparator<News>() {
            @Override
            public int compare(News o1, News o2) {
                return (int) (o2.getCreateDate().getTime() - o1.getCreateDate().getTime());
            }
        }).collect(Collectors.toList());
    }

    // user hiện tại
    @ModelAttribute("user")
    public User login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return userService.getUserByEmail(authentication.getName());
        }
        return null;
    }


    @ModelAttribute
    public void fileModel(Model model, HttpServletRequest req) {
        try {
            logger.info("Fetching all Files from S3");
            model.addAttribute("files", amazonS3Service.getAllFiles()
                    .stream()
                    .collect(Collectors.toMap(f ->  amazonS3Service.getDownloadURL(f), f -> {
                        List<String> filesNFolders = new ArrayList<>();
                        String[] folders = f.split("/");
                        Arrays.asList(folders).forEach(filesNFolders::add);
                        logger.info(f.toString());
                        return filesNFolders;
                    })));
        } catch (IOException e) {
            logger.warning("Failed to Connect to AWS S3 - Please check your AWS Keys");
            e.printStackTrace();
        }
    }
}
