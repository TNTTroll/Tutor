package tutor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TutorStatus {
    ACTIVE("Активно"),
    DELETED("Удалено");

    private final String value;
}
