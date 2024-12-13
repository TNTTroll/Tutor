package tutor.service.impl;

import lombok.RequiredArgsConstructor;
import tutor.entity.TutorEntity;
import tutor.enums.TutorStatus;
import tutor.repository.TutorRepository;
import tutor.service.KafkaService;
import tutor.service.TutorService;
import tutor.service.UserService;
import tutor.service.UserToLayerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static tutor.util.Util.SUCCESS;
import static tutor.util.Util.checkAuthorizedAccess;
import static tutor.util.Util.throwException;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {
    private final TutorRepository tutorRepository;

    private final KafkaService kafkaService;
    private final UserService userService;
    private final UserToLayerService userToLayerService;

    @Override
    public Mono<TutorEntity> sendTutor(String layerId, String authorId, String tutor) {
        Mono<Boolean> check = checkIdFromNotUserIdList(authorId);
        TutorEntity tutorEntity = createNewTutor(authorId, layerId, tutor);
        return check
                .zipWith(userToLayerService.checkMatchUserToLayer(authorId, layerId), (checkNotUser, checkMatchLayer) -> {
                    checkAuthorizedAccess(checkNotUser, checkMatchLayer);
                    return tutorEntity;
                })
                .zipWith(tutorRepository.countByLayerId(layerId), (entity, count) -> {
                    entity.setNumberInLayer(count);
                    return entity;
                })
                .flatMap(tutorRepository::save)
                .zipWith(check, (entity, checkNotUser) -> checkNotUser ? entity : null)
                .flatMap(entity -> (Mono<TutorEntity>) kafkaService.sendTutor(entity.getId()))
                .map(id -> tutorEntity);
    }

    @Override
    public Mono<TutorEntity> getTutor(String layerId, String userId, String tutorId) {
        Mono<Boolean> check = checkIdFromNotUserIdList(userId);
        return check
                .zipWith(userToLayerService.checkMatchUserToLayer(userId, layerId), (checkNotUser, checkMatchLayer) -> {
                    checkAuthorizedAccess(checkNotUser, checkMatchLayer);
                    return checkNotUser;
                })
                .flatMap(a -> tutorRepository.findById(layerId));
    }

    @Override
    public Mono<TutorEntity> getTutor(String userId) {
        return checkIdFromNotUserIdList(userId)
                .flatMap(check -> {
                    if (check) {
                        throwException("Unauthorized access attempts");
                    }
                    return (Mono<String>) kafkaService.getTutorId();
                })
                .flatMap(tutorRepository::findById);
    }

    @Override
    public Flux<TutorEntity> getTutors(String layerId, String userId) {
        Mono<Boolean> check = checkIdFromNotUserIdList(userId);
        return check
                .zipWith(userToLayerService.checkMatchUserToLayer(userId, layerId), (checkNotUser, checkMatchLayer) -> {
                    checkAuthorizedAccess(checkNotUser, checkMatchLayer);
                    return checkNotUser;
                })
                .flatMapMany(a -> tutorRepository.findAllByLayerId(layerId));
    }

    @Override
    public Mono<String> deleteTutor(String tutorId, String userId, String layerId) {
        Mono<Boolean> check = checkIdFromNotUserIdList(userId);
        return check
                .zipWith(userToLayerService.checkMatchUserToLayer(userId, layerId), (checkNotUser, checkMatchLayer) -> {
                    checkAuthorizedAccess(checkNotUser, checkMatchLayer);
                    return checkNotUser;
                })
                .flatMap(a -> tutorRepository.deleteById(tutorId))
                .then(Mono.just(SUCCESS));
    }

    private Mono<Boolean> checkIdFromNotUserIdList(String userId) {
        return userService.getAllNotUserIds()
                .any(id -> Objects.equals(id, userId));
    }

    private TutorEntity createNewTutor(String authorId, String layerId, String tutor) {
        TutorEntity entity = new TutorEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setAuthorId(authorId);
        entity.setLayerId(layerId);
        entity.setTutor(tutor);
        entity.setStatus(TutorStatus.ACTIVE);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setNew(true);
        return entity;
    }
}
