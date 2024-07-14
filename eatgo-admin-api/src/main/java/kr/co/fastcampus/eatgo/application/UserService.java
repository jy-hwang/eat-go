package kr.co.fastcampus.eatgo.application;

import jakarta.transaction.Transactional;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();

        return users;
    }

    public User addUser(String email, String nickname) {
        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .build();

        return userRepository.save(user);
    }

    public User updateUser(Long id, String email, String nickname, Long level) {
        // TODO : 나중에 예외처리 추가해보기
        User user = userRepository.findById(id).orElse(null);

        user.setEmail(email);
        user.setNickname(nickname);
        user.setLevel(level);

        return user;
    }
}
