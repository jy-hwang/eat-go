package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {

        String email = resource.getEmail();
        String nickname = resource.getNickname();
        String password = resource.getPassword();

        User user = userService.registerUser(
                email,
                nickname,
                password
        );


        String url = "/users/1004";//+user.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
