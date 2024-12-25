package com.simsimbookstore.frontserver.config;


import com.simsimbookstore.frontserver.security.filter.JwtAuthenticationFilter;
import com.simsimbookstore.frontserver.security.filter.UserAuthenticationFilter;
import com.simsimbookstore.frontserver.security.handler.CustomLogoutHandler;
import com.simsimbookstore.frontserver.service.CustomOauth2UserService;
import com.simsimbookstore.frontserver.service.CustomUserDetailsService;
import com.simsimbookstore.frontserver.service.UserService;
import com.simsimbookstore.frontserver.util.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;

    public SecurityConfig(CustomUserDetailsService userDetailsService, UserService userService, JsonUtil jsonUtil) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        //authorize
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/users/myPage").authenticated()
                .anyRequest().permitAll());

        //rememberme
        http.rememberMe(rememberMe->rememberMe
                .tokenValiditySeconds(3600)
                .alwaysRemember(true)
                .userDetailsService(userDetailsService)
                .rememberMeParameter("remember-me"));

        // oauth2

//        http.oauth2Login(oauth2Login->oauth2Login
//                .loginPage("/login")
//                .defaultSuccessUrl("/",true)
//                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
//                        .authorizationRequestResolver(new Custom)
//                .userInfoEndpoint(userInfoEndpoint-> userInfoEndpoint.userService(new CustomOauth2UserService())));


        // local login
        http.formLogin(form->form.loginPage("/login"));
        http.logout(logout->logout.addLogoutHandler(new CustomLogoutHandler()));

        // filter
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager(null),userService), AnonymousAuthenticationFilter.class);
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

    private OAuth2AuthorizationRequest getPaycoAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("payco");

        OAuth2AuthorizationRequest request = OAuth2AuthorizationRequest
                .authorizationCode()
                .clientId(clientRegistration.getClientId())
                .redirectUri(clientRegistration.getRedirectUri())
                .authorizationUri(clientRegistration.getProviderDetails().getAuthorizationUri())
                .build();

        OAuth2AuthorizationRequest customRequest = OAuth2AuthorizationRequest
                .from(request)
                .additionalParameters(Map.of(
                        "response_type","code",
                        "serviceProviderCode","FRIENDS",
                        "userLocale","ko_KR"
                ))
                .build();

        return customRequest;
    }
}
