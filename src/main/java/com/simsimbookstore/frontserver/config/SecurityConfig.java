package com.simsimbookstore.frontserver.config;


import com.simsimbookstore.frontserver.security.filter.JwtAuthenticationFilter;
import com.simsimbookstore.frontserver.security.filter.UserAuthenticationFilter;
import com.simsimbookstore.frontserver.security.handler.CustomLogoutHandler;
import com.simsimbookstore.frontserver.service.CustomUserDetailsService;
import com.simsimbookstore.frontserver.service.UserService;
import com.simsimbookstore.frontserver.util.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final JsonUtil jsonUtil;


    public SecurityConfig(CustomUserDetailsService userDetailsService, UserService userService, JsonUtil jsonUtil) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jsonUtil = jsonUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/users/myPage").authenticated()
                .anyRequest().permitAll());

        http.rememberMe(rememberMe->rememberMe
                .tokenValiditySeconds(3600)
                .alwaysRemember(true)
                .userDetailsService(userDetailsService)
                .rememberMeParameter("remember-me"));

        http.formLogin(form->form.loginPage("/login"));
        http.logout(logout->logout.addLogoutHandler(new CustomLogoutHandler()));
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager(null),userService), AnonymousAuthenticationFilter.class);
//        http.addFilterBefore(new UserAuthenticationFilter(jsonUtil), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
