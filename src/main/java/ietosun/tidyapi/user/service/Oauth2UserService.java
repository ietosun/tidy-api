package ietosun.tidyapi.user.service;

import ietosun.tidyapi.user.UserRepository;
import ietosun.tidyapi.user.entity.Grade;
import ietosun.tidyapi.user.entity.LoginType;
import ietosun.tidyapi.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

        userRepository.findByCodeAndLoginType(oAuth2User.getName(), loginType)
                .ifPresentOrElse(this::validateUser, () -> joinOauth2User(loginType, oAuth2User));

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName);
    }

    private void joinOauth2User(LoginType loginType, OAuth2User oAuth2User) {
        User newUser = User.builder()
                .bannedState(false)
                .joinState(true)
                .code(oAuth2User.getName())
                .grade(Grade.General)
                .loginType(loginType)
                .build();
        userRepository.save(newUser);
    }

    private void validateUser(User user) {
        if (user.isBannedState()) {
            throw new LockedException("User has been banned");
        }
        if (user.isJoinState()) {
            throw new DisabledException("User has been joined");
        }
    }
}
