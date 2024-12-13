package tutor.entity;

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
@Table("user_to_layer")
public class UserToLayerEntity implements Persistable<String> {
    @Id
    @Column("id")
    private String id;

    @Column("user_id")
    private String userId;

    @Column("layer_id")
    private String layerId;

    @Transient
    private boolean isNew = false;
}
