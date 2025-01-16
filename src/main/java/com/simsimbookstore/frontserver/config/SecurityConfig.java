package com.simsimbookstore.frontserver.config;


import com.simsimbookstore.frontserver.cart.service.CartService;
import com.simsimbookstore.frontserver.security.filter.TokenAuthenticationFilter;
import com.simsimbookstore.frontserver.security.handler.CustomAuthFailureHandler;
import com.simsimbookstore.frontserver.security.handler.CustomLogoutHandler;
import com.simsimbookstore.frontserver.security.handler.CustomLocalLoginSuccessHandler;
import com.simsimbookstore.frontserver.security.handler.CustomOAuthSuccessHandler;
import com.simsimbookstore.frontserver.security.provider.CustomAuthenticationProvider;
import com.simsimbookstore.frontserver.security.provider.TokenProvider;
import com.simsimbookstore.frontserver.security.requestRepository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.simsimbookstore.frontserver.security.tokenResponseClient.CustomAccessTokenResponseClient;
import com.simsimbookstore.frontserver.users.user.feign.PaycoAuthServiceClient;
import com.simsimbookstore.frontserver.users.user.service.CustomOauth2UserService;

import com.simsimbookstore.frontserver.users.user.service.CustomUserDetailsService;
import com.simsimbookstore.frontserver.users.user.service.LoginSuccessHandlerService;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Map;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final CustomOauth2UserService oauth2UserService;
    private final LoginSuccessHandlerService loginSuccessHandlerService;
    private final PaycoAuthServiceClient paycoAuthServiceClient;
    private final CartService cartService;
    private final TokenProvider tokenProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable);
        // session
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //authorize
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/users/myPage/**").authenticated()
                .requestMatchers("/reviews/create").authenticated()
                .requestMatchers(HttpMethod.POST,"/reviews/*/likes").authenticated()
                .requestMatchers("/management/health").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll());


        http.authenticationProvider(customAuthenticationProvider());

        //remember-me
//        http.rememberMe(rememberMe->rememberMe
//                .tokenValiditySeconds(3600)
//                .userDetailsService(userDetailsService)
//                .rememberMeParameter("remember-me"));

        // oauth2
        http.oauth2Login(oauth2Login->oauth2Login
                .authorizationEndpoint(endpoint->endpoint.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
                .tokenEndpoint(tokenEndpoint -> tokenEndpoint.accessTokenResponseClient(new CustomAccessTokenResponseClient(paycoAuthServiceClient)))
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(oauth2UserService))
                .loginPage("/index?showLoginModal=true")
                .successHandler(new CustomOAuthSuccessHandler(loginSuccessHandlerService)))
                ;


        // local login
        http.formLogin(form->form.loginPage("/index?showLoginModal=true")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .successHandler(new CustomLocalLoginSuccessHandler(loginSuccessHandlerService))
                .failureHandler(new CustomAuthFailureHandler())
        );

        http.logout(AbstractHttpConfigurer::disable);
//                .addLogoutHandler(new CustomLogoutHandler(cartService))
//                .logoutUrl("/logout"));

//      filter
        http.addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
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

//    private OAuth2AuthorizationRequest getPaycoAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository) {
//        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("payco");
//
//        OAuth2AuthorizationRequest request = OAuth2AuthorizationRequest
//                .authorizationCode()
//                .clientId(clientRegistration.getClientId())
//                .redirectUri(clientRegistration.getRedirectUri())
//                .authorizationUri(clientRegistration.getProviderDetails().getAuthorizationUri())
//                .build();
//
//        OAuth2AuthorizationRequest customRequest = OAuth2AuthorizationRequest
//                .from(request)
//                .additionalParameters(Map.of(
//                        "response_type","code",
//                        "serviceProviderCode","FRIENDS",
//                        "userLocale","ko_KR"
//                ))
//                .build();
//
//        return customRequest;
//    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(){
        CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();
        customAuthenticationProvider.setUserDetailsService(userDetailsService);
        customAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return customAuthenticationProvider;
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        // CORS 정책 설정
//        CorsConfiguration corsConfig = new CorsConfiguration();
//
//        // jsw.com 도메인에서의 요청만 허용
//        corsConfig.addAllowedOrigin("https://jsw.com"); // jsw.com에서 오는 요청만 허용
//
//        corsConfig.addAllowedMethod("*"); // 모든 HTTP 메소드 허용
//        corsConfig.addAllowedHeader("*"); // 모든 헤더 허용
//        corsConfig.setAllowCredentials(true); // 자격 증명 허용
//
//        // CORS 설정을 URL에 매핑
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig); // 모든 경로에 대해 CORS 설정 적용
//
//        return new CorsFilter().;
//    }
}
