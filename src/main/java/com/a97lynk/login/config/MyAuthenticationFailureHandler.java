/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.a97lynk.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

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
public class MyAuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger logger
            = Logger.getLogger(MyAuthenticationFailureHandler.class.getName());


    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        String refererURL = request.getHeader("referer");
        if (refererURL.indexOf('?') > 0) {
            refererURL = refererURL.substring(0, refererURL.indexOf('?'));
        }
        setDefaultFailureUrl(String.format("%s?error=true", refererURL)); // chuyển trang
        super.onAuthenticationFailure(request, response, exception); // set failure
        String errorMessage = "Email adress or password is incorrect";
        logger.log(Level.INFO, ">> Referer: {0}", refererURL);
        logger.log(Level.WARNING, ">> Failure auth: {0}", errorMessage);
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}
