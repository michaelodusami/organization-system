package dev.michaelodusami.organizationsystem;

import org.junit.jupiter.api.Test;

import dev.michaelodusami.organizationsystem.fields.Field;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class FieldTest {

    @Test
    public void testTextField() {
        Field textField = new Field(Field.Type.TEXT, "Hello, World!");
        assertEquals(Field.Type.TEXT, textField.getType());
        assertEquals("Hello, World!", textField.getValue());

        textField.setValue("New Value");
        assertEquals("New Value", textField.getValue());

        // Test invalid value
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            textField.setValue(123); // Invalid for TEXT
        });
        assertTrue(exception.getMessage().contains("does not match the specified type"));
    }

    @Test
    public void testNumberField() {
        Field numberField = new Field(Field.Type.NUMBER, 42);
        assertEquals(Field.Type.NUMBER, numberField.getType());
        assertEquals(42, numberField.getValue());

        numberField.setValue(99.99);
        assertEquals(99.99, numberField.getValue());
    }

    @Test
    public void testTypeConversion() {
        Field field = new Field(Field.Type.TEXT, "123");
        field.setType(Field.Type.NUMBER); // Should convert the value to a number
        assertEquals(123.0, field.getValue()); // Automatically converted

        // Test invalid conversion
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Field invalidField = new Field(Field.Type.TEXT, "Invalid");
            invalidField.setType(Field.Type.NUMBER); // Should fail
        });
        assertTrue(exception.getMessage().contains("Unable to convert value to the new type"));
    }

    @Test
    public void testPercentageField() {
        Field percentageField = new Field(Field.Type.PERCENTAGE, 85.5);
        assertEquals(Field.Type.PERCENTAGE, percentageField.getType());
        assertEquals(85.5, percentageField.getValue());

        // Test out-of-range percentage
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            percentageField.setValue(150.0); // Invalid percentage
        });
        assertTrue(exception.getMessage().contains("does not match the specified type"));
    }

    @Test
    public void testDateField() {
        Date currentDate = new Date();
        Field dateField = new Field(Field.Type.DATE, currentDate);
        assertEquals(Field.Type.DATE, dateField.getType());
        assertEquals(currentDate, dateField.getValue());

        // Test invalid date
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dateField.setValue((Date) null); // Null is allowed
            dateField.setValue("Invalid"); // Invalid for DATE
        });
        assertTrue(exception.getMessage().contains("does not match the specified type"));
    }
}
