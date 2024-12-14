package tutor.service.impl;

import lombok.RequiredArgsConstructor;
import tutor.entity.TutorEntity;
import tutor.enums.TutorStatus;
import tutor.repository.TutorRepository;
import tutor.service.TutorService;
import tutor.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static tutor.util.Util.SUCCESS;
import static tutor.util.Util.UNAUTHORIZED_ACCESS_ATTEMPT_EXCEPTION;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {
    private final TutorRepository tutorRepository;

    private final UserService userService;
    private final LayerService layerService;

    @Override
    public Mono<TutorEntity> sendTutor(String layerId, String authorId, String tutor) {
        TutorEntity tutorEntity = createNewTutor(authorId, layerId, tutor);
        return checkUserMatchLayer(authorId, layerId)
                .map(b -> tutorEntity)
                .flatMap(entity -> tutorRepository.countByLayerId(layerId)
                        .map(count -> {
                            entity.setNumberInLayer(count == null ? 1 : ++count);
                            return entity;
                        }))
    }

    @Override
    public Mono<TutorEntity> getTutor(String layerId, String userId, String tutorId) {
        Mono<Boolean> check = checkIdFromNotUserIdList(userId);
        return check
                .zipWith(userToLayerService.checkMatchUserToLayer(userId, layerId), (checkNotUser, checkMatchLayer) -> {
                    checkAuthorizedAccess(checkNotUser, checkMatchLayer);
                    return checkNotUser;
                })
                .flatMap(a -> tutorRepository.findById(layerId))
                .flatMap(entity -> checkIdFromNotUserIdList(authorId)
                        .filter(checkNotUser -> !checkNotUser)
                        .map(check -> entity)
                )
                .flatMap(entity -> layerService.addSupportToLayer((TutorEntity) entity, authorId))
                .map(layer -> tutorEntity);;
    }

    @Override
    public Mono<TutorEntity> getTutor(String userId) {
        return checkIdFromNotUserIdList(userId)
                .filter(check -> check)
                .switchIfEmpty(Mono.error(UNAUTHORIZED_ACCESS_ATTEMPT_EXCEPTION))
                .flatMap(tutorRepository::findById);
    }

    @Override
    public Flux<TutorEntity> getTutors(String layerId, String userId) {
       return checkUserMatchLayer(userId, layerId)
                .flatMapMany(a -> tutorRepository.findAllByLayerId(layerId));
    }

    @Override
    public Mono<String> deleteTutor(String tutorId, String userId, String layerId) {
        return checkUserMatchLayer(userId, layerId)return checkNotUser;
                })
                .flatMap(a -> tutorRepository.deleteById(tutorId))
                .then(Mono.just(SUCCESS));
    }

     private Mono<Boolean> checkUserMatchLayer(String userId, String layerId) {
        return userService.getAllNotUserIds()
                .any(id -> Objects.equals(id, userId))
                .flatMap(checkSupportId -> layerService.getByIdAndUserId(layerId, userId)
                        .map(checkMatchLayer -> !checkSupportId || checkMatchLayer != null)
                        .switchIfEmpty(checkSupportId ? Mono.just(true) : Mono.empty()))
                .switchIfEmpty(Mono.error(UNAUTHORIZED_ACCESS_ATTEMPT_EXCEPTION))
                .map(layer -> true);
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
