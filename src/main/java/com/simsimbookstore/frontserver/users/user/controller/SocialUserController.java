package com.simsimbookstore.frontserver.users.user.controller;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import com.simsimbookstore.frontserver.users.socialUser.dto.PaycoTokenResponseDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.PaycoUserInfoResponseDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.SocialUserRequestDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.SocialUserResponse;
import com.simsimbookstore.frontserver.users.user.dto.Gender;
import com.simsimbookstore.frontserver.users.socialUser.service.SocialUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

@RequiredArgsConstructor
@Controller()
@RequestMapping("/auth")
public class SocialUserController
{
    private final SocialUserService socialService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("socialUsers/paycoLogin")
    public ResponseEntity<String> authorize() {
        String url = socialService.getPaycoUrl();

        return ResponseEntity.status(HttpStatus.FOUND).header("Location", url).build();
    }

    @GetMapping("/paycoLogin/callback")
    public ModelAndView paycoCallback(
            HttpSession session,
            @RequestParam(value = "code") String code
    ) {
        ModelAndView modelAndView = new ModelAndView("paycoCallback");
        PaycoTokenResponseDto tokenResponseDto = socialService.generateToken(code);
        PaycoUserInfoResponseDto userInfoResponseDto = socialService.getUserInfo(tokenResponseDto.getAccessToken());

        SocialUserRequestDto socialUserRequestDto = SocialUserRequestDto.builder()
                .oauthId(userInfoResponseDto.getIdNo())
                .email(userInfoResponseDto.getEmail())
                .mobile(userInfoResponseDto.getMobile())
                .name(userInfoResponseDto.getName())
                .gender(Objects.equals(userInfoResponseDto.getGenderCode(), "FEMALE") ? Gender.FEMALE : Gender.MALE)
                .build();

        SocialUserResponse response = socialService.loginSocialUser(socialUserRequestDto);

        modelAndView.addObject("accessToken", tokenResponseDto.getAccessToken());
        modelAndView.addObject("userInfo", socialUserRequestDto);

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .principalName(response.getOauthId())
                .userId(response.getUserId())
                .userStatus(response.getUserStatus())
                .latestLoginDate(response.getLatestLoginDate())
                .build();

        for (RoleName roleName : response.getRoles()) {
            customUserDetails.addRole(roleName);
        }
        OAuth2AuthenticationToken authenticationToken = new OAuth2AuthenticationToken(customUserDetails,customUserDetails.getAuthorities(),response.getOauthId());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return modelAndView;
    }

    @GetMapping("/paycoLogout")
    public String paycoLogout(HttpSession session) {
        String token = (String) session.getAttribute("token");
        String result = "";
        try {
            String encodedToken = URLEncoder.encode(token,"UTF-8");
            result = socialService.logoutPayco(encodedToken);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }
}
