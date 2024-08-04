package ietosun.tidyapi.user.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/api/users")
@RestController
public class UserApi {

    @GetMapping
    public ResponseEntity getUser() {
        return ResponseEntity.ok("LOGIN SUCCESS");
    }

}
