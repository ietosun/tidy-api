package ietosun.tidyapi.user.service;

import ietosun.tidyapi.user.UserRepository;
import ietosun.tidyapi.user.entity.CustomUserDetail;
import ietosun.tidyapi.user.entity.Grade;
import ietosun.tidyapi.user.entity.LoginType;
import ietosun.tidyapi.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Service
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId().toUpperCase(Locale.ROOT);
        LoginType loginType = LoginType.valueOf(registrationId);

        return userRepository.findByCodeAndLoginType(oAuth2User.getName(), loginType)
                .map(this::processExistingUser)
                .orElseGet(() -> processNewUser(loginType, oAuth2User));
    }

    private OAuth2User processExistingUser(User user) {
        validateUser(user);
        return CustomUserDetail.fromUser(user);
    }

    private OAuth2User processNewUser(LoginType loginType, OAuth2User oAuth2User) {
        return CustomUserDetail.fromUser(joinOauth2User(loginType, oAuth2User));
    }

    private User joinOauth2User(LoginType loginType, OAuth2User oAuth2User) {
        User newUser = User.builder()
                .bannedState(false)
                .joinState(true)
                .code(oAuth2User.getName())
                .grade(Grade.ROLE_GENERAL)
                .loginType(loginType)
                .build();
        userRepository.save(newUser);
        return newUser;
    }

    private void validateUser(User user) {
        if (user.isBannedState()) {
            throw new LockedException("Ban User: " + user.getId());
        }
        if (user.isJoinState()) {
            throw new DisabledException("Withdrawal User: " + user.getId());
        }
    }
}

