package ietosun.tidyapi.global.config;

import ietosun.tidyapi.security.filter.JwtAuthenticationFilter;
import ietosun.tidyapi.security.handler.JwtLoginSuccessHandler;
import ietosun.tidyapi.user.entity.Grade;
import ietosun.tidyapi.user.service.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final Oauth2UserService oauth2UserService;

    private final JwtLoginSuccessHandler jwtLoginSuccessHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement((sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests(authorize -> authorize.requestMatchers("/login/**").permitAll()
                            .requestMatchers("/v*/api/**").hasAnyAuthority(Grade.ROLE_GENERAL.name(), Grade.ROLE_ADMIN.name()))
                    .oauth2Login((httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                            .successHandler(jwtLoginSuccessHandler)
                            .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oauth2UserService))))
                    .build();
    }

}
