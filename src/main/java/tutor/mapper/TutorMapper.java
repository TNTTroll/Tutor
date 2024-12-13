package tutor.mapper;

import tutor.dto.TutorDto;
import tutor.entity.TutorEntity;
import tutor.enums.TutorStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TutorMapper {
    @Mapping(target = "status", source = "status", qualifiedByName = "getStringFromStatus")
    TutorEntity toEntity(TutorDto dto);

    @Mapping(target = "status", source = "status", qualifiedByName = "getStatusFromString")
    TutorDto toDto(TutorEntity entity);

    @Named("getStringFromStatus")
    private String getStringFromStatus(TutorStatus status) {
        if (status == null) return TutorStatus.ACTIVE.getValue();
        return status.getValue();
    }

    @Named("getStatusFromString")
    private TutorStatus getStatusFromString(String status) {
        if (status == null || status.isEmpty()) return TutorStatus.ACTIVE;
        return TutorStatus.valueOf(status);
    }
}
