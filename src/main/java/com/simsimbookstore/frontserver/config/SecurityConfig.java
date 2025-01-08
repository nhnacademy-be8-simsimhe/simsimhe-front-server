package com.simsimbookstore.frontserver.config;


import com.simsimbookstore.frontserver.cart.service.CartService;
import com.simsimbookstore.frontserver.security.handler.CustomAuthFailureHandler;
import com.simsimbookstore.frontserver.security.handler.CustomLogoutHandler;
//import com.simsimbookstore.frontserver.security.handler.LocalLoginSuccessHandler;
import com.simsimbookstore.frontserver.users.user.service.CustomUserDetailsService;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Map;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final CartService cartService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        //authorize
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/users/myPage/**").authenticated()
                .requestMatchers("/reviews/create").authenticated()
                .requestMatchers(HttpMethod.POST,"/reviews/*/likes").authenticated()
                .requestMatchers("/management/health").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll());
        //rememberme
        http.rememberMe(rememberMe->rememberMe
                .tokenValiditySeconds(3600)
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
        http.formLogin(form->form.loginPage("/index?showLoginModal=true")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .successHandler(new LocalLoginSuccessHandler(userService))
                .failureHandler(new CustomAuthFailureHandler())
        );

        http.logout(logout->logout
                .addLogoutHandler(new CustomLogoutHandler(cartService))
                .logoutUrl("/logout"));

        // filter
//        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager(null),userService), AnonymousAuthenticationFilter.class);
//        http
//                .addFilterAt(new JwtAuthenticationFilter(authenticationManager(null),userService), UsernamePasswordAuthenticationFilter.class)
//                .anonymous(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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