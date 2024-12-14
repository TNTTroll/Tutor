package tutor.mapper;

import tutor.dto.TutorDto;
import tutor.entity.TutorEntity;
import tutor.enums.TutorStatus;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TutorMapper {
    @InheritInverseConfiguration
    TutorEntity toEntity(TutorDto dto);

    TutorDto toDto(TutorEntity entity);
}
