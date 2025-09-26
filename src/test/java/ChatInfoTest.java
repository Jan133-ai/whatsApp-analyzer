import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChatInfoTest {

    static ChatInfo chatInfo1;

    @BeforeAll
    static public void constructChatInfo() throws URISyntaxException, FileNotFoundException {
        URL resource = ChatInfoTest.class.getClassLoader().getResource("chat1.txt");
        assertNotNull(resource);
        String filename = Paths.get(resource.toURI()).toString();

        chatInfo1 = new ChatInfo(filename);
        assertNotNull(chatInfo1);
    }

    @Test
    public void testMessages() {
        Map<String, Integer> results = chatInfo1.callMessages(false, "", "");
        assertNotNull(results);

        assertEquals(results.get("Alice"), 3);
        assertEquals(results.get("Bob"), 2);
        assertEquals(results.get("Total"), 5);

        Map<String, Integer> resultsDay = chatInfo1.callMessages(true, "-d", "21.09.2025");
        assertNotNull(resultsDay);

        assertEquals(resultsDay.get("Alice"), 2);
        assertEquals(resultsDay.get("Bob"), 1);
        assertEquals(resultsDay.get("Total"), 3);

        Map<String, Integer> resultsDay2 = chatInfo1.callMessages(true, "-d", "23.09.2025");
        assertNotNull(resultsDay);

        assertEquals(resultsDay2.get("Alice"), 0);
        assertEquals(resultsDay2.get("Bob"), 0);
        assertEquals(resultsDay2.get("Total"), 0);
    }

    @Test
    public void testWords() {
        Map<String, Integer> results = chatInfo1.callWords(false, "", "");
        assertNotNull(results);

        assertEquals(results.get("Alice"), 10);
        assertEquals(results.get("Bob"), 6);
        assertEquals(results.get("Total"), 16);

        Map<String, Integer> resultsDay = chatInfo1.callWords(true, "-d", "21.09.2025");
        assertNotNull(resultsDay);

        assertEquals(resultsDay.get("Alice"), 6);
        assertEquals(resultsDay.get("Bob"), 3);
        assertEquals(resultsDay.get("Total"), 9);

        Map<String, Integer> resultsDay2 = chatInfo1.callWords(true, "-d", "23.09.2025");
        assertNotNull(resultsDay);

        assertEquals(resultsDay2.get("Alice"), 0);
        assertEquals(resultsDay2.get("Bob"), 0);
        assertEquals(resultsDay2.get("Total"), 0);
    }

    @Test
    public void testWordsPerMessage() {
        Map<String, Float> results = chatInfo1.callWordsPerMessage(false, "", "");
        assertNotNull(results);

        assertEquals(results.get("Alice"), (float) 10.0/3);
        assertEquals(results.get("Bob"), (float) 6.0/2);
        assertEquals(results.get("Total"), (float) 16.0/5);

        Map<String, Float> resultsDay = chatInfo1.callWordsPerMessage(true, "-d", "21.09.2025");
        assertNotNull(results);

        assertEquals(resultsDay.get("Alice"), (float) 6.0/2);
        assertEquals(resultsDay.get("Bob"), (float) 3.0);
        assertEquals(resultsDay.get("Total"), (float) 9.0/3);

        Map<String, Float> resultsDay2 = chatInfo1.callWordsPerMessage(true, "-d", "23.09.2025");
        assertNotNull(results);

        assertEquals(resultsDay2.get("Alice"), (float) 0.0);
        assertEquals(resultsDay2.get("Bob"), (float) 0.0);
        assertEquals(resultsDay2.get("Total"), (float) 0.0);
    }
}
