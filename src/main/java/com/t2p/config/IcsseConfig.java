package com.t2p.config;

import com.a97lynk.login.config.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class IcsseConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler authSuccessHandler;

    @Autowired
    protected MyUserDetailsService userDetailsService;

    @Autowired
    protected AuthenticationFailureHandler authFailureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //1. dùng authenticationProvider
//        auth.authenticationProvider(authProvider);

        //2. dùng userDetailService
          auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        //3. account trong memory
       // auth.inMemoryAuthentication().withUser("t2p@gmail.com")
       //         .password("123").roles("hasAuthority('WRITE_DATA')");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // bỏ qua csrf
        http.csrf().disable();

        // Các trang không yêu cầu login
        http.authorizeRequests().antMatchers("/u/login", "/u/logout").permitAll();

        // Khi người dùng đã login
        // Ngoại lệ AccessDeniedException sẽ ném ra.
        http.authorizeRequests()
                .and().exceptionHandling().accessDeniedPage("/403");

        // Cấu hình cho Login Form.
        http.authorizeRequests()
                .and().formLogin()//
                // Submit URL của trang login
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/u/login")
                .permitAll()
                .successHandler(authSuccessHandler)
                //
                .defaultSuccessUrl("/")
                //
                .failureHandler(authFailureHandler)
                //
                .usernameParameter("username")//
                .passwordParameter("password")
                // Cấu hình cho Logout Page.
                //...
                // cấu hình remember me
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me");
    }

}

