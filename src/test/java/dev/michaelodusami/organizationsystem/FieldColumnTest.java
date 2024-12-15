package dev.michaelodusami.organizationsystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import static org.junit.jupiter.api.Assertions.*;

import dev.michaelodusami.organizationsystem.fields.ColumnType;
import dev.michaelodusami.organizationsystem.fields.FieldColumn;

public class FieldColumnTest {  
    
    private FieldColumn fieldColumn;

    
    @BeforeEach
    public void setup()
    {
       fieldColumn = new FieldColumn();
    }

    @Test
    public void testSettersAndGetters()
    {
        fieldColumn.setTitle("Date");
        fieldColumn.setType(ColumnType.DATE);
        assertEquals("Date", fieldColumn.getTitle());
        assertEquals(ColumnType.DATE, fieldColumn.getType());
    }

    @Test
    public void testEqualsMethod()
    {
        FieldColumn fieldColumnTemp = new FieldColumn("Date", ColumnType.DATE);
        assertFalse(fieldColumn.equals(fieldColumnTemp));
        assertFalse(fieldColumn.equals(new String("void")));

        fieldColumn.setTitle("Date");
        fieldColumn.setType(ColumnType.DATE);
        assertTrue(fieldColumn.equals(fieldColumnTemp));

        fieldColumn.setType(ColumnType.CURRENCY);
        assertFalse(fieldColumn.equals(fieldColumnTemp));
    }

    @Test
    public void testSettingTitleNull()
    {
        Exception mainEx = null;
        try{
            fieldColumn.setTitle(null);
        }
        catch(Exception ex)
        {
            mainEx = ex;
        }
        assertTrue(mainEx instanceof NullPointerException);
    }
}
