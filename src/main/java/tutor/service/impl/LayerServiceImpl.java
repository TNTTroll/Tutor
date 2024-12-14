package tutor.service.impl;

import lombok.RequiredArgsConstructor;
import tutor.entity.LayerEntity;
import tutor.entity.TutorEntity;
import tutor.repository.LayerRepository;
import tutor.service.LayerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LayerServiceImpl {

    private final LayerRepository layerRepository;

    @Override
    public Mono<LayerEntity> getById(String id) {
        return layerRepository.findById(id);
    }

    @Override
    public Mono<LayerEntity> getByIdAndUserId(String id, String userId) {
        return layerRepository.findByIdAndUserId(id, userId);
    }
    @Override
    public Mono<LayerEntity> addSupportToLayer(TutorEntity tutor, String supportId) {
        return layerRepository.findById(tutor.getLayerId())
                .map(layer -> {
                    layer.getSupportIds().add(supportId);
                    return layer;
                })
                .flatMap(layerRepository::save);
    }
}
