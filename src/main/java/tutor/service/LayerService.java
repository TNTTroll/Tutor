package tutor.service;

import tutor.entity.LayerEntity;
import reactor.core.publisher.Mono;

public interface LayerService {
    Mono<LayerEntity> getById(String id);
    Mono<LayerEntity> getByIdAndUserId(String id, String userId);
    Mono<LayerEntity> addSupportToLayer(MessageEntity tutor, String supportId);
}
