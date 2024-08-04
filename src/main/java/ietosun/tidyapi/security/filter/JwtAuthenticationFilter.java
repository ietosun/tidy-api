package ietosun.tidyapi.security.filter;

import ietosun.tidyapi.security.util.JwtUtil;
import ietosun.tidyapi.user.entity.CustomUserDetail;
import ietosun.tidyapi.user.entity.User;
import ietosun.tidyapi.user.service.Oauth2UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Currency;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final Oauth2UserService oauth2UserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            int id =  Integer.parseInt((String) jwtUtil.getClaims(jwt).get("id"));
            log.info("{}", jwtUtil.getClaims(jwt));
            CustomUserDetail customUserDetail = CustomUserDetail.fromUser(oauth2UserService.findById(id));
            Authentication auth = new UsernamePasswordAuthenticationToken(customUserDetail, customUserDetail.getPassword(), customUserDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

}
