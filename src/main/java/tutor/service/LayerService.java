package tutor.service;

import tutor.entity.LayerEntity;
import reactor.core.publisher.Mono;

public interface LayerService {
    public Mono<LayerEntity> getById(String id);
}
