package ietosun.tidyapi.global.config;

import ietosun.tidyapi.user.service.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final Oauth2UserService oauth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
            http
                    .authorizeHttpRequests(authorize -> authorize.requestMatchers("/**").permitAll())
                    .oauth2Login((httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                            .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oauth2UserService))))
                    .build();
    }
}
