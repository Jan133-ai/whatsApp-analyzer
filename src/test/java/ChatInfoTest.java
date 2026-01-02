import model.ChatInfo;
import model.MessageListFilter;
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
    static MessageListFilter nullFilter;

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

        nullFilter = new MessageListFilter(null, null, null);
    }

    @Test
    public void testMessages() {
        Map<String, Integer> results = chatInfo1.callMessages(nullFilter);
        assertNotNull(results);
        assertEquals(3, results.get("Alice"));
        assertEquals(2, results.get("Bob"));
        assertEquals(5, results.get("Total"));
    }

    @Test
    public void testMessagesDateFilter() {
        Map<String, Integer> resultsDay = chatInfo1.callMessages(new MessageListFilter("21.09.2025", null, null));
        assertNotNull(resultsDay);
        assertEquals(2, resultsDay.get("Alice"));
        assertEquals(1, resultsDay.get("Bob"));
        assertEquals(3, resultsDay.get("Total"));

        Map<String, Integer> resultsDay2 = chatInfo1.callMessages(new MessageListFilter("23.09.2025", null, null));
        assertNotNull(resultsDay);
        assertEquals(0, resultsDay2.get("Alice"));
        assertEquals(0, resultsDay2.get("Bob"));
        assertEquals(0, resultsDay2.get("Total"));

        Map<String, Integer> resultsMonth = chatInfo2.callMessages(new MessageListFilter("03.2021", null, null));
        assertNotNull(resultsMonth);
        assertEquals(2, resultsMonth.get("Alice"));
        assertEquals(2, resultsMonth.get("Bob"));
        assertEquals(4, resultsMonth.get("Total"));

        Map<String, Integer> resultsYear = chatInfo2.callMessages(new MessageListFilter("2025", null, null));
        assertNotNull(resultsYear);
        assertEquals(2, resultsYear.get("Alice"));
        assertEquals(1, resultsYear.get("Bob"));
        assertEquals(3, resultsYear.get("Total"));
    }

    @Test
    public void testMessagesTimeFilter() {
        Map<String, Integer> resultsSecond = chatInfo2.callMessages(new MessageListFilter(null, "07:55:44", null));
        assertNotNull(resultsSecond);
        assertEquals(1, resultsSecond.get("Alice"));
        assertEquals(0, resultsSecond.get("Bob"));
        assertEquals(1, resultsSecond.get("Total"));

        Map<String, Integer> resultsMinute = chatInfo2.callMessages(new MessageListFilter(null, "08:15", null));
        assertNotNull(resultsMinute);
        assertEquals(1, resultsMinute.get("Alice"));
        assertEquals(1, resultsMinute.get("Bob"));
        assertEquals(2, resultsMinute.get("Total"));

        Map<String, Integer> resultsHour = chatInfo2.callMessages(new MessageListFilter(null, "8", null));
        assertNotNull(resultsHour);
        assertEquals(4, resultsHour.get("Alice"));
        assertEquals(3, resultsHour.get("Bob"));
        assertEquals(7, resultsHour.get("Total"));
    }

    @Test
    public void testMessageWeekdayFilter() {
        Map<String, Integer> resultsFriday = chatInfo2.callMessages(new MessageListFilter(null, null, DayOfWeek.FRIDAY));
        assertNotNull(resultsFriday);
        assertEquals(1, resultsFriday.get("Alice"));
        assertEquals(1, resultsFriday.get("Bob"));
        assertEquals(2, resultsFriday.get("Total"));
    }

    @Test
    public void testWords() {
        Map<String, Integer> results = chatInfo1.callWords(nullFilter);
        assertNotNull(results);
        assertEquals(10, results.get("Alice"));
        assertEquals(6, results.get("Bob"));
        assertEquals(16, results.get("Total"));
    }

    @Test
    public void testWordsDateFilter() {
        Map<String, Integer> resultsDay = chatInfo1.callWords(new MessageListFilter("21.09.2025", null, null));
        assertNotNull(resultsDay);
        assertEquals(6, resultsDay.get("Alice"));
        assertEquals(3, resultsDay.get("Bob"));
        assertEquals(9, resultsDay.get("Total"));

        Map<String, Integer> resultsDay2 = chatInfo1.callWords(new MessageListFilter("23.09.2025", null, null));
        assertNotNull(resultsDay);
        assertEquals(0, resultsDay2.get("Alice"));
        assertEquals(0, resultsDay2.get("Bob"));
        assertEquals(0, resultsDay2.get("Total"));

        Map<String, Integer> resultsMonth = chatInfo2.callWords(new MessageListFilter("03.2021", null, null));
        assertNotNull(resultsMonth);
        assertEquals(8, resultsMonth.get("Alice"));
        assertEquals(10, resultsMonth.get("Bob"));
        assertEquals(18, resultsMonth.get("Total"));

        Map<String, Integer> resultsYear = chatInfo2.callWords(new MessageListFilter("2025", null, null));
        assertNotNull(resultsYear);
        assertEquals(18, resultsYear.get("Alice"));
        assertEquals(6, resultsYear.get("Bob"));
        assertEquals(24, resultsYear.get("Total"));
    }

    @Test
    public void testWordsTimeFilter() {
        Map<String, Integer> resultsSecond = chatInfo2.callWords(new MessageListFilter(null, "07:55:44", null));
        assertNotNull(resultsSecond);
        assertEquals(6, resultsSecond.get("Alice"));
        assertEquals(0, resultsSecond.get("Bob"));
        assertEquals(6, resultsSecond.get("Total"));

        Map<String, Integer> resultsMinute = chatInfo2.callWords(new MessageListFilter(null, "08:15", null));
        assertNotNull(resultsMinute);
        assertEquals(3, resultsMinute.get("Alice"));
        assertEquals(7, resultsMinute.get("Bob"));
        assertEquals(10, resultsMinute.get("Total"));

        Map<String, Integer> resultsHour = chatInfo2.callWords(new MessageListFilter(null, "8", null));
        assertNotNull(resultsHour);
        assertEquals(16, resultsHour.get("Alice"));
        assertEquals(17, resultsHour.get("Bob"));
        assertEquals(33, resultsHour.get("Total"));
    }

    @Test
    public void testWordsWeekdayFilter() {
        Map<String, Integer> resultsWednesday = chatInfo2.callWords(new MessageListFilter(null, null, DayOfWeek.WEDNESDAY));
        assertNotNull(resultsWednesday);
        assertEquals(15, resultsWednesday.get("Alice")); //Messages:3
        assertEquals(14, resultsWednesday.get("Bob")); //Messages:3
        assertEquals(29, resultsWednesday.get("Total"));
    }

    @Test
    public void testWordsPerMessage() {
        Map<String, Float> results = chatInfo1.callWordsPerMessage(nullFilter);
        assertNotNull(results);
        assertEquals((float) 10.0/3, results.get("Alice"));
        assertEquals((float) 6.0/2, results.get("Bob"));
        assertEquals((float) 16.0/5, results.get("Total"));
    }

    @Test
    public void testWordsPerMessageDateFilter() {
        Map<String, Float> resultsDay = chatInfo1.callWordsPerMessage(new MessageListFilter("21.09.2025", null, null));
        assertNotNull(resultsDay);
        assertEquals((float) 6.0/2, resultsDay.get("Alice"));
        assertEquals((float) 3, resultsDay.get("Bob"));
        assertEquals((float) 9.0/3, resultsDay.get("Total"));

        Map<String, Float> resultsDay2 = chatInfo1.callWordsPerMessage(new MessageListFilter("23.09.2025", null, null));
        assertNotNull(resultsDay2);
        assertEquals((float) 0, resultsDay2.get("Alice"));
        assertEquals((float) 0, resultsDay2.get("Bob"));
        assertEquals((float) 0, resultsDay2.get("Total"));

        Map<String, Float> resultsMonth = chatInfo2.callWordsPerMessage(new MessageListFilter("03.2021", null, null));
        assertNotNull(resultsMonth);
        assertEquals((float) 4, resultsMonth.get("Alice"));
        assertEquals((float) 5, resultsMonth.get("Bob"));
        assertEquals((float) 18.0/4, resultsMonth.get("Total"));

        Map<String, Float> resultsYear = chatInfo2.callWordsPerMessage(new MessageListFilter("2025", null, null));
        assertNotNull(resultsYear);
        assertEquals((float) 9, resultsYear.get("Alice"));
        assertEquals((float) 6, resultsYear.get("Bob"));
        assertEquals((float) 24.0/3, resultsYear.get("Total"));
    }

    @Test
    public void testWordsPerMessageTimeFilter() {
        Map<String, Float> resultsSecond = chatInfo2.callWordsPerMessage(new MessageListFilter(null, "07:55:44", null));
        assertNotNull(resultsSecond);
        assertEquals((float) 6, resultsSecond.get("Alice"));
        assertEquals((float) 0, resultsSecond.get("Bob"));
        assertEquals((float) 6, resultsSecond.get("Total"));

        Map<String, Float> resultsMinute = chatInfo2.callWordsPerMessage(new MessageListFilter(null, "08:15", null));
        assertNotNull(resultsMinute);
        assertEquals((float) 3, resultsMinute.get("Alice"));
        assertEquals((float) 7, resultsMinute.get("Bob"));
        assertEquals((float) 5, resultsMinute.get("Total"));

        Map<String, Float> resultsHour = chatInfo2.callWordsPerMessage(new MessageListFilter(null, "8", null));
        assertNotNull(resultsHour);
        assertEquals((float) 4, resultsHour.get("Alice"));
        assertEquals((float) 17.0/3, resultsHour.get("Bob"));
        assertEquals((float) 33.0/7, resultsHour.get("Total"));
    }

    @Test
    public void testWordsPerMessageWeekdayFilter() {
        Map<String, Float> resultsWednesday = chatInfo2.callWordsPerMessage(new MessageListFilter(null, null, DayOfWeek.WEDNESDAY));
        assertNotNull(resultsWednesday);
        assertEquals((float) 5, resultsWednesday.get("Alice"));
        assertEquals((float) 14.0/3, resultsWednesday.get("Bob"));
        assertEquals((float) 29.0/6, resultsWednesday.get("Total"));
    }
}
