package dev.michaelodusami.organizationsystem.fields;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
public class FieldRequestBody {
    private String value;
    private String title;
    private ColumnType columnType;
}
