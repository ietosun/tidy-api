package ietosun.tidyapi.user;

import ietosun.tidyapi.user.entity.LoginType;
import ietosun.tidyapi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCodeAndLoginType(String code, LoginType loginType);

    Optional<User> findById(int id);
}
