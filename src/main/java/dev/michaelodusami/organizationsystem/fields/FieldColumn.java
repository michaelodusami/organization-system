package dev.michaelodusami.organizationsystem.fields;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Component
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class FieldColumn {
    
    @NonNull
    private String title; 
    private ColumnType type;
}
