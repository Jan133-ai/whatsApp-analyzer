import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Message;

public class MessageTest {

    static String sender;
    static LocalDateTime dateTime;
    
    @BeforeAll 
    static public void setupTests() {
        sender = "Test";
        dateTime = LocalDateTime.of(2026, 2, 2, 2, 2);
    }

    @Test 
    public void testWordsV() {
        Message message = new Message("Was geht denn hier ab", sender, dateTime);
        assertNotNull(message);
        assertArrayEquals(new String[]{"Was", "geht", "denn", "hier", "ab"}, message.getWordsv());
    }

    @Test 
    public void testWordsVPunctuation() {
        Message message = new Message("Wieso? Dass, das geht.", sender, dateTime);
        assertNotNull(message);
        for (String word : message.getWordsv()) {
            System.out.println(word);
        }
        assertArrayEquals(new String[]{"Wieso", "Dass", "das", "geht"}, message.getWordsv());
    }

    @Test 
    public void testWordsVDoubleSpace() {
        Message message = new Message("Was geht  denn hier   ab", sender, dateTime);
        assertNotNull(message);
        assertArrayEquals(new String[]{"Was", "geht", "denn", "hier", "ab"}, message.getWordsv());
    }

    @Test 
    public void testAppendMessage() {
        Message message = new Message("Hallo.", sender, dateTime);
        message.appendMessage("Wie geht es dir?");
        assertEquals("Hallo. Wie geht es dir?", message.getText());
        assertArrayEquals(new String[]{"Hallo", "Wie", "geht", "es", "dir"}, message.getWordsv());
    }
}
