package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> list() {
        List<User> users = userService.getUsers();
        System.out.println("=================" + users.toString());
        return users;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {
        String email = resource.getEmail();
        String nickname = resource.getNickname();
        User response = userService.addUser(email, nickname);

        String url = "/users/" + response.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @PatchMapping("/{id}")
    public String update(
            @PathVariable("id") Long id,
            @RequestBody User resource) throws URISyntaxException {
        String email = resource.getEmail();
        String nickname = resource.getNickname();
        Long level = resource.getLevel();

        userService.updateUser(id, email, nickname, level);

        return "{}";
    }

}
