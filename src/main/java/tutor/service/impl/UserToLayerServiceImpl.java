package tutor.service.impl;

import lombok.RequiredArgsConstructor;
import tutor.entity.UserToLayerEntity;
import tutor.repository.UserToLayerRepository;
import tutor.service.UserToLayerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserToLayerServiceImpl {

    private final UserToLayerRepository userToLayerRepository;

    @Override
    public Mono<UserToLayerEntity> getByUserId(String userId) {
        return userToLayerRepository.findByUserId(userId);
    }

    @Override
    public Mono<Boolean> checkMatchUserToLayer(String userId, String layerId) {
        return userToLayerRepository.findByUserIdAndLayerId(userId, layerId)
                .map(entity -> true)
                .switchIfEmpty(Mono.just(false));
    }
}
