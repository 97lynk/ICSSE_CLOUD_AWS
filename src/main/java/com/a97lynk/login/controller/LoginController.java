/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.a97lynk.login.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 97lynk
 */
@Controller
public class LoginController {

    private static final Logger logger
            = Logger.getLogger(LoginController.class.getName());

    /**
     * gửi form login
     *
     * @return loginPage.html
     */
    @GetMapping("/u/login")
    public String loginPage(@RequestParam(name = "ajax", defaultValue = "false") String ajax, Principal principal, HttpServletRequest request) {
        if (principal != null)
            return "redirect:/";
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            logger.info(headerName + " : " + request.getHeader(headerName));
        }
        loggingAuth("LOGIN PAGE");
//        return "signin/loginPage";
//        request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (ajax.equals("true"))
            return "fragment/login";
        else
            return "signin/loginPage";
    }

    /**
     * chuyển trang logout thành công
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/u/logout")
    public String logoutSuccessfulPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("title", "Logout");
        loggingAuth("LOGOUT REQUEST");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        logger.log(Level.INFO, ">> {0} is logout success", auth.getPrincipal());
//        return "signin/logoutSuccessfulPage";
        return "redirect:/";
    }
    void loggingAuth(String title) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.log(Level.INFO, "------------------{0}--------------------------", title);
            logger.log(Level.INFO, ">> Is authencated: {0}", auth.isAuthenticated());
            logger.log(Level.INFO, ">> Credentials: {0}", auth.getCredentials());
            logger.log(Level.INFO, ">> Details: {0}", auth.getDetails());
            logger.log(Level.INFO, ">> Principal: {0}", auth.getPrincipal());
            logger.log(Level.INFO, ">> List authorities: ");
            auth.getAuthorities().forEach(ga -> {
                logger.log(Level.INFO, "\t{0}", ga.toString());
            });

            logger.log(Level.INFO, "---------------------------------------------------------");
        }
    }
}
