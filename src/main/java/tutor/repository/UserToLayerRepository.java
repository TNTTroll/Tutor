package tutor.repository;

import tutor.entity.UserToLayerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserToLayerRepository extends R2dbcRepository<UserToLayerEntity, String> {
    Mono<UserToLayerEntity> findByUserId(String userId);
    Mono<UserToLayerEntity> findByUserIdAndLayerId(String userId, String layerId);
}
