package tutor.repository;

import tutor.entity.TutorEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TutorRepository extends R2dbcRepository<TutorEntity, String> {
    Mono<Integer> countByLayerId(String layerId);
    Flux<TutorEntity> findAllByLayerId(String layerId);
}
