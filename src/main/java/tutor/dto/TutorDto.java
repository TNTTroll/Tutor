package tutor.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tutor.enums.TutorStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorDto {
    private String id;
    private String layerId;
    private String authorId;
    private String tutor;
    private Integer numberInLayer;
    LocalDateTime createdAt;
    String status;
}
