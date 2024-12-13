package tutor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ADMIN("Администратор"),
    WRITER("Писатель обучений"),
    USER("Пользователь");

    private final String value;
}
