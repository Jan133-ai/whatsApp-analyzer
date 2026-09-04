import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.ChatParser;
import model.Message;

public class ChatParserTest {

    static ChatParser singleLineParser;
    static ChatParser fileNewLineParser;
    
    @BeforeAll 
    static public void setupTests() throws URISyntaxException {
        singleLineParser = new ChatParser();

        URL resource = ChatParserTest.class.getClassLoader().getResource("chat.txt");
        assertNotNull(resource);
        File chatNewLine = new File(resource.toURI());
        fileNewLineParser = new ChatParser(chatNewLine);
        assertNotNull(fileNewLineParser);
    }

    @Test
    public void testParseCorrectMessageLine() {
        Message messageStruct = singleLineParser.parseLine("[21.09.25, 09:07:18] Alice: Perfekt!");
        assertNotNull(messageStruct);
        assertEquals(LocalDateTime.of(2025, 9, 21, 9, 7, 18), messageStruct.getDateTime());
        assertEquals("Alice", messageStruct.getSender());
        assertEquals("Perfekt!", messageStruct.getText());
    }

    @Test 
    public void testRemovesDeletedMessageLine() {
        Message messageStruct = singleLineParser.parseLine("[03.04.26, 12:07:18] Alice: \u200eDiese Nachricht wurde gelöscht.");
        assertNull(messageStruct);
    }

    @Test 
    public void testRemovesMediaTextMessageLine() {
        Message messageStruct = singleLineParser.parseLine("\u200e[02.08.25, 14:23:41] Carla: \u200eAudio weggelassen");
        assertNotNull(messageStruct);
        assertEquals("Carla", messageStruct.getSender());
        assertEquals("", messageStruct.getText());

        Message messageStruct2 = singleLineParser.parseLine("\u200e[23.06.26, 17:00:04] Carla: Es war einer hier \u200eBild weggelassen");
        assertNotNull(messageStruct2);
        assertEquals("Carla", messageStruct2.getSender());
        assertEquals("Es war einer hier", messageStruct2.getText());
    }

    @Test 
    public void testParseFile() throws FileNotFoundException{
        fileNewLineParser.parseFile();
        assertNotNull(fileNewLineParser.getMessageListGes());

        List<Message> messageList = fileNewLineParser.getMessageListGes();
        assertEquals(4, messageList.size());
        assertEquals("Komm schon mal Zur Bushaltestelle", messageList.get(1).getText());
        assertEquals("Der Bus kommt gleich", messageList.get(2).getText());
        assertEquals("Der Bus ist hier", messageList.get(3).getText());
    }
}