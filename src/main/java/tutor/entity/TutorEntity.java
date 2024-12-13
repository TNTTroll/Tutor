package tutor.entity;

import java.time.LocalDateTime;
import tutor.enums.TutorStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tutor")
public class TutorEntity implements Persistable<String> {
    @Id
    @Column("id")
    private String id;

    @Column("layer_id")
    private String layerId;

    @Column("author_id")
    private String authorId;

    @Column("tutor")
    private String tutor;

    @Column("number_in_layer")
    private Integer numberInLayer;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("status")
    private TutorStatus status;

    @Transient
    private boolean isNew = false;
}
