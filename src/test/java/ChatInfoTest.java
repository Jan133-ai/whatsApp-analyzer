import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.DayOfWeek;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChatInfoTest {

    static ChatInfo chatInfo1;
    static ChatInfo chatInfo2;

    @BeforeAll
    static public void constructChatInfo() throws URISyntaxException, FileNotFoundException {
        URL resource = ChatInfoTest.class.getClassLoader().getResource("chat1.txt");
        assertNotNull(resource);
        File chat1 = new File(resource.toURI());
        chatInfo1 = new ChatInfo(chat1);
        assertNotNull(chatInfo1);

        URL resource2 = ChatInfoTest.class.getClassLoader().getResource("chat2.txt");
        assertNotNull(resource2);
        File chat2 = new File(resource2.toURI());
        chatInfo2 = new ChatInfo(chat2);
        assertNotNull(chatInfo2);
    }

    @Test
    public void testMessages() {
        Map<String, Integer> results = chatInfo1.callMessages(null, null, null);
        assertNotNull(results);
        assertEquals(results.get("Alice"), 3);
        assertEquals(results.get("Bob"), 2);
        assertEquals(results.get("Total"), 5);
    }

    @Test
    public void testMessagesDateFilter() {
        Map<String, Integer> resultsDay = chatInfo1.callMessages("21.09.2025", null, null);
        assertNotNull(resultsDay);
        assertEquals(resultsDay.get("Alice"), 2);
        assertEquals(resultsDay.get("Bob"), 1);
        assertEquals(resultsDay.get("Total"), 3);

        Map<String, Integer> resultsDay2 = chatInfo1.callMessages("23.09.2025", null, null);
        assertNotNull(resultsDay);
        assertEquals(resultsDay2.get("Alice"), 0);
        assertEquals(resultsDay2.get("Bob"), 0);
        assertEquals(resultsDay2.get("Total"), 0);

        Map<String, Integer> resultsMonth = chatInfo2.callMessages("03.2021", null, null);
        assertNotNull(resultsMonth);
        assertEquals(resultsMonth.get("Alice"), 2);
        assertEquals(resultsMonth.get("Bob"), 2);
        assertEquals(resultsMonth.get("Total"), 4);

        Map<String, Integer> resultsYear = chatInfo2.callMessages("2025", null, null);
        assertNotNull(resultsYear);
        assertEquals(resultsYear.get("Alice"), 2);
        assertEquals(resultsYear.get("Bob"), 1);
        assertEquals(resultsYear.get("Total"), 3);
    }

    @Test
    public void testMessagesTimeFilter() {
        Map<String, Integer> resultsSecond = chatInfo2.callMessages(null, "07:55:44", null);
        assertNotNull(resultsSecond);
        assertEquals(resultsSecond.get("Alice"), 1);
        assertEquals(resultsSecond.get("Bob"), 0);
        assertEquals(resultsSecond.get("Total"), 1);

        Map<String, Integer> resultsMinute = chatInfo2.callMessages(null, "08:15", null);
        assertNotNull(resultsMinute);
        assertEquals(resultsMinute.get("Alice"), 1);
        assertEquals(resultsMinute.get("Bob"), 1);
        assertEquals(resultsMinute.get("Total"), 2);

        Map<String, Integer> resultsHour = chatInfo2.callMessages(null, "8", null);
        assertNotNull(resultsHour);
        assertEquals(resultsHour.get("Alice"), 4);
        assertEquals(resultsHour.get("Bob"), 3);
        assertEquals(resultsHour.get("Total"), 7);
    }

    @Test
    public void testMessageWeekdayFilter() {
        Map<String, Integer> resultsFriday = chatInfo2.callMessages(null, null, DayOfWeek.FRIDAY);
        assertNotNull(resultsFriday);
        assertEquals(resultsFriday.get("Alice"), 1);
        assertEquals(resultsFriday.get("Bob"), 1);
        assertEquals(resultsFriday.get("Total"), 2);
    }

    @Test
    public void testWords() {
        Map<String, Integer> results = chatInfo1.callWords(null, null, null);
        assertNotNull(results);
        assertEquals(results.get("Alice"), 10);
        assertEquals(results.get("Bob"), 6);
        assertEquals(results.get("Total"), 16);
    }

    @Test
    public void testWordsDateFilter() {
        Map<String, Integer> resultsDay = chatInfo1.callWords("21.09.2025", null, null);
        assertNotNull(resultsDay);
        assertEquals(resultsDay.get("Alice"), 6);
        assertEquals(resultsDay.get("Bob"), 3);
        assertEquals(resultsDay.get("Total"), 9);

        Map<String, Integer> resultsDay2 = chatInfo1.callWords("23.09.2025", null, null);
        assertNotNull(resultsDay);
        assertEquals(resultsDay2.get("Alice"), 0);
        assertEquals(resultsDay2.get("Bob"), 0);
        assertEquals(resultsDay2.get("Total"), 0);

        Map<String, Integer> resultsMonth = chatInfo2.callWords("03.2021", null, null);
        assertNotNull(resultsMonth);
        assertEquals(resultsMonth.get("Alice"), 8);
        assertEquals(resultsMonth.get("Bob"), 10);
        assertEquals(resultsMonth.get("Total"), 18);

        Map<String, Integer> resultsYear = chatInfo2.callWords("2025", null, null);
        assertNotNull(resultsYear);
        assertEquals(resultsYear.get("Alice"), 18);
        assertEquals(resultsYear.get("Bob"), 6);
        assertEquals(resultsYear.get("Total"), 24);
    }

    @Test
    public void testWordsTimeFilter() {
        Map<String, Integer> resultsSecond = chatInfo2.callWords(null, "07:55:44", null);
        assertNotNull(resultsSecond);
        assertEquals(resultsSecond.get("Alice"), 6);
        assertEquals(resultsSecond.get("Bob"), 0);
        assertEquals(resultsSecond.get("Total"), 6);

        Map<String, Integer> resultsMinute = chatInfo2.callWords(null, "08:15", null);
        assertNotNull(resultsMinute);
        assertEquals(resultsMinute.get("Alice"), 3);
        assertEquals(resultsMinute.get("Bob"), 7);
        assertEquals(resultsMinute.get("Total"), 10);

        Map<String, Integer> resultsHour = chatInfo2.callWords(null, "8", null);
        assertNotNull(resultsHour);
        assertEquals(resultsHour.get("Alice"), 16);
        assertEquals(resultsHour.get("Bob"), 17);
        assertEquals(resultsHour.get("Total"), 33);
    }

    @Test
    public void testWordsWeekdayFilter() {
        Map<String, Integer> resultsWednesday = chatInfo2.callWords(null, null, DayOfWeek.WEDNESDAY);
        assertNotNull(resultsWednesday);
        assertEquals(resultsWednesday.get("Alice"), 15); //Messages:3
        assertEquals(resultsWednesday.get("Bob"), 14); //Messages:3
        assertEquals(resultsWednesday.get("Total"), 29);
    }

    @Test
    public void testWordsPerMessage() {
        Map<String, Float> results = chatInfo1.callWordsPerMessage(null, null, null);
        assertNotNull(results);
        assertEquals(results.get("Alice"), (float) 10.0/3);
        assertEquals(results.get("Bob"), (float) 6.0/2);
        assertEquals(results.get("Total"), (float) 16.0/5);
    }

    @Test
    public void testWordsPerMessageDateFilter() {
        Map<String, Float> resultsDay = chatInfo1.callWordsPerMessage("21.09.2025", null, null);
        assertNotNull(resultsDay);
        assertEquals(resultsDay.get("Alice"), (float) 6.0/2);
        assertEquals(resultsDay.get("Bob"), (float) 3);
        assertEquals(resultsDay.get("Total"), (float) 9.0/3);

        Map<String, Float> resultsDay2 = chatInfo1.callWordsPerMessage("23.09.2025", null, null);
        assertNotNull(resultsDay2);
        assertEquals(resultsDay2.get("Alice"), (float) 0);
        assertEquals(resultsDay2.get("Bob"), (float) 0);
        assertEquals(resultsDay2.get("Total"), (float) 0);

        Map<String, Float> resultsMonth = chatInfo2.callWordsPerMessage("03.2021", null, null);
        assertNotNull(resultsMonth);
        assertEquals(resultsMonth.get("Alice"), (float) 4);
        assertEquals(resultsMonth.get("Bob"), (float) 5);
        assertEquals(resultsMonth.get("Total"), (float) 18.0/4);

        Map<String, Float> resultsYear = chatInfo2.callWordsPerMessage("2025", null, null);
        assertNotNull(resultsYear);
        assertEquals(resultsYear.get("Alice"), (float) 9);
        assertEquals(resultsYear.get("Bob"), (float) 6);
        assertEquals(resultsYear.get("Total"), (float) 24.0/3);
    }

    @Test
    public void testWordsPerMessageTimeFilter() {
        Map<String, Float> resultsSecond = chatInfo2.callWordsPerMessage(null, "07:55:44", null);
        assertNotNull(resultsSecond);
        assertEquals(resultsSecond.get("Alice"), (float) 6);
        assertEquals(resultsSecond.get("Bob"), (float) 0);
        assertEquals(resultsSecond.get("Total"), (float) 6);

        Map<String, Float> resultsMinute = chatInfo2.callWordsPerMessage(null, "08:15", null);
        assertNotNull(resultsMinute);
        assertEquals(resultsMinute.get("Alice"), (float) 3);
        assertEquals(resultsMinute.get("Bob"), (float) 7);
        assertEquals(resultsMinute.get("Total"), (float) 5);

        Map<String, Float> resultsHour = chatInfo2.callWordsPerMessage(null, "8", null);
        assertNotNull(resultsHour);
        assertEquals(resultsHour.get("Alice"), (float) 4);
        assertEquals(resultsHour.get("Bob"), (float) 17.0/3);
        assertEquals(resultsHour.get("Total"), (float) 33.0/7);
    }

    @Test
    public void testWordsPerMessageWeekdayFilter() {
        Map<String, Float> resultsWednesday = chatInfo2.callWordsPerMessage(null, null, DayOfWeek.WEDNESDAY);
        assertNotNull(resultsWednesday);
        assertEquals(resultsWednesday.get("Alice"), (float) 5);
        assertEquals(resultsWednesday.get("Bob"), (float) 14.0/3);
        assertEquals(resultsWednesday.get("Total"), (float) 29.0/6);
    }
}
