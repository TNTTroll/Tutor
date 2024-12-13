package tutor.repository;

import tutor.entity.LayerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LayerRepository extends R2dbcRepository<LayerEntity, String> {
}
