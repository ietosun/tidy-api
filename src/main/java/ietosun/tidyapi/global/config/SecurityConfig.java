package ietosun.tidyapi.global.config;

import ietosun.tidyapi.security.JwtLoginSuccessHandler;
import ietosun.tidyapi.user.service.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final Oauth2UserService oauth2UserService;

    private final JwtLoginSuccessHandler jwtLoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
            http
                    .authorizeHttpRequests(authorize -> authorize.requestMatchers("/**").permitAll())
                    .oauth2Login((httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                            .successHandler(jwtLoginSuccessHandler)
                            .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oauth2UserService))))
                    .build();
    }

}
