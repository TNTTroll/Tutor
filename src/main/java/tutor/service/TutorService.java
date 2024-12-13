package tutor.service;

import tutor.entity.TutorEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TutorService {
    Mono<TutorEntity> sendTutor(String layerId, String authorId, String tutor);
    Mono<TutorEntity> getTutor(String layerId, String userId, String tutorId);
    Mono<TutorEntity> getTutor(String userId);
    Flux<TutorEntity> getTutors(String layerId, String userId);
    Mono<String> deleteTutor(String tutorId, String userId, String layerId);
}
