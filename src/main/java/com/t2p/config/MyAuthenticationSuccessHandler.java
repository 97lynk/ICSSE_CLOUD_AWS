/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2p.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 97lynk
 */
@Service
public class MyAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger logger
            = Logger.getLogger(MyAuthenticationSuccessHandler.class.getName());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String refererURL = request.getHeader("referer");
        if (refererURL.indexOf('?') > 0) {
            refererURL = refererURL.substring(0, refererURL.indexOf('?'));
        }
        setDefaultTargetUrl(refererURL); // chuyển trang
        super.onAuthenticationSuccess(request, response, authentication); // set failure
        String successMessage = "Login success " + authentication.toString();
        logger.log(Level.INFO, ">> Referer: {0}", refererURL);
        logger.log(Level.INFO, ">> Success auth: {0}", successMessage);
        request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }

}
