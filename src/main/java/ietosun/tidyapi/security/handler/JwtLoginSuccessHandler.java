package ietosun.tidyapi.security.handler;

import ietosun.tidyapi.security.util.JwtUtil;
import ietosun.tidyapi.user.entity.CustomUserDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Component
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        try {
            response.setHeader("Authorization", "Bearer " + createJwtToken(user));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String createJwtToken(CustomUserDetail CustomUserDetail) throws NoSuchAlgorithmException {
        return jwtUtil.generateToken(CustomUserDetail);
    }

}
