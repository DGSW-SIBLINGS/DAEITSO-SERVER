package dgsw.hackathon.DaeItSo.domain.user.service;

import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import dgsw.hackathon.DaeItSo.domain.user.repository.UserRepository;
import dgsw.hackathon.DaeItSo.global.error.CustomError;
import dgsw.hackathon.DaeItSo.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        return byEmail.orElseGet(() -> userRepository.save(user));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw CustomError.of(ErrorCode.NOT_FOUND);
        });
    }
}
