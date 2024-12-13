package tutor.service;

import main.java.tutor.entity.UserToLayerEntity;
import reactor.core.publisher.Mono;

public interface UserToLayerService {
    Mono<UserToLayerEntity> getByUserId(String userId);
    Mono<Boolean> checkMatchUserToLayer(String userId, String layerId);
}
