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
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<SessionResonseDto> create(@RequestBody User resource) throws URISyntaxException {
        String url = "/session";

        String email = resource.getEmail();
        String password = resource.getPassword();

        User user = userService.authenticate(email, password);
        String accessToken = user.getAccessToken();

        return ResponseEntity.created(new URI(url))
                .body(SessionResonseDto.builder()
                        .accessToken(accessToken)
                        .build());
    }
}
