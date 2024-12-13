package tutor.service.impl;

import lombok.RequiredArgsConstructor;
import tutor.entity.LayerEntity;
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
}
