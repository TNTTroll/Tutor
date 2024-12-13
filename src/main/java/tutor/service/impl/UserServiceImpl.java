package tutor.service.impl;

import lombok.RequiredArgsConstructor;
import tutor.repository.UserRepository;
import tutor.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;

    @Override
    public Flux<String> getAllNotUserIds() {
        return userRepository.getAllNotUserId();
    }
}
