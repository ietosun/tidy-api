package ietosun.tidyapi.user.service;

import ietosun.tidyapi.user.UserRepository;
import ietosun.tidyapi.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<User> getList() {
        return this.userRepository.findAll();
    }
}
