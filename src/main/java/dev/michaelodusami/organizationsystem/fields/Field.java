package dev.michaelodusami.organizationsystem.fields;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents a Field analougous to that in an Excel Table
 * @author Michael-Andre Odusami 
 * @version (v1) 2024.12.14
 */
@NoArgsConstructor
@ToString
@Data
@Component
public class Field {

    private ColumnType type;
    private Object value;

    /**
     * Constructor to create a Field instance.
     * @param type  the type of the field (e.g., TEXT, NUMBER).
     * @param value the value of the field.
     * @throws IllegalArgumentException if the value does not match the type.
     */
    public Field(ColumnType type, Object value) {
        try {  
            if (type == null) {
                throw new IllegalArgumentException("Type cannot be null.");
            }
            if (!validateType(type, value)) {
                throw new IllegalArgumentException("Value does not match the specified type.");
            }
            this.type = type;
            this.value = value;
        } catch (IllegalArgumentException exception) {
            throw exception; // Re-throw to allow the caller to handle it if needed.
        } catch (Exception exception) {
            throw new RuntimeException("Unexpected error occurred.", exception);
        }
    }

     /**
     * Sets the value of the field.
     * @param value the new value to set.
     * @throws IllegalArgumentException if the value does not match the current type.
     */
    public void setValue(Object value) {
        if (validateType(type, value)) {
            this.value = value;
        } else {
            String fs = String.format("This value [%s:%s] does not match the specified type [%s]",
                    value == null ? "null" : value.getClass(),
                    value == null ? "null" : value.toString(),
                    type);
            throw new IllegalArgumentException(fs);
        }
    }

    /**
     * Sets the type of the field. Attempts to convert the value to match the new type.
     * If conversion is not possible, throws an exception.
     * @param type the new type to set.
     * @throws IllegalArgumentException if the current value cannot be converted to the new type.
     */
    public void setType(ColumnType type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null.");
        }

        // Attempt to convert the value to match the new type
        try {
            if (!validateType(type, value)) {
                this.value = convertValue(type, value);
            }
            this.type = type;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to convert value to the new type: " + e.getMessage());
        }
    }

    /**
     * Converts the current value to match the specified type.
     * @param type  the target type.
     * @param value the current value.
     * @return the converted value.
     * @throws IllegalArgumentException if conversion is not possible.
     */
    private Object convertValue(ColumnType type, Object value) throws IllegalArgumentException {
        if (value == null) {
            return null; // Null values can remain null.
        }

        switch (type) {
            case TEXT:
                return value.toString();
            case NUMBER:
                if (value instanceof String) {
                    return Double.valueOf((String) value);
                }
                throw new IllegalArgumentException("Cannot convert value to NUMBER.");
            case PERCENTAGE:
                if (value instanceof String) {
                    double percentValue = Double.valueOf((String) value);
                    if (percentValue >= 0 && percentValue <= 100) {
                        return Double.valueOf(percentValue);
                    }
                }
                throw new IllegalArgumentException("Cannot convert value to PERCENTAGE.");
            case CURRENCY:
                if (value instanceof String) {
                    return Double.valueOf((String) value);
                }
                throw new IllegalArgumentException("Cannot convert value to CURRENCY.");
            case DATE:
                if (value instanceof String) {
                    return java.util.Date.from(java.time.LocalDate.parse((String) value).atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
                }
                throw new IllegalArgumentException("Cannot convert value to DATE.");
            case UNCATEGORIZED:
                return value; // No conversion needed for UNCATEGORIZED.
            default:
                throw new IllegalArgumentException("Unsupported type conversion.");
        }
    }


    /**
     * Gets the type of the field
     * @return the type of the field
     */
    public ColumnType getType() {
        return type;
    }

    /**
     * Gets the value of the field
     * @return the value of the field
     */
    public Object getValue() {
        return value;
    }

    /**
     * Validates whether the value matches the specified type.
     * @param type  the type of the field.
     * @param value the value of the field.
     * @return true if the value is valid for the type, false otherwise.
     */
    private boolean validateType(ColumnType type, Object value) {
        if (value == null) {
            return true; // Null values can be allowed.
        }

        switch (type) {
            case TEXT:
                return value instanceof String;
            case NUMBER:
                return value instanceof Number;
            case PERCENTAGE:
                return value instanceof Number && isPercentage((Number) value);
            case CURRENCY:
                return value instanceof Number; // Add currency-specific validation if needed.
            case DATE:
                return value instanceof java.util.Date; // Or java.time.LocalDate, etc.
            case UNCATEGORIZED:
                return true; // No restrictions for UNCATEGORIZED.
            default:
                return false;
        }
    }

    /**
     * Validates that a number is a percentage (0 <= value <= 100).
     * @param value the number to validate.
     * @return true if the number is a valid percentage, false otherwise.
     */
    private boolean isPercentage(Number value) {
        double val = value.doubleValue();
        return val >= 0 && val <= 100;
    }


}
