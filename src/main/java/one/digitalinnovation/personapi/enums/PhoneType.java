package one.digitalinnovation.personapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhoneType {
    HOME("Home"),
    COMERCIAL("Comercial"),
    MOBILE("Mobile");

    private final String description;
}
