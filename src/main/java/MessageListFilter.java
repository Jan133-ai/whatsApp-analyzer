import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;

public class MessageListFilter {

    String dateString;
    String timeString;
    DayOfWeek weekday;

    public MessageListFilter(String dateString, String timeString, DayOfWeek weekday) {
        this.dateString = dateString;
        this.timeString = timeString;
        this.weekday = weekday;
    }

    private List<Message> filterMessagesByDate(String dateString, List<Message> messageList) throws NumberFormatException{
        if (dateString == null) {
            return messageList;
        }
        List<Message> listByDate = new LinkedList<>();

        int year = 0;
        int month = 0;
        int day = 0;

        String[] date = dateString.split("[.]");
        if (date.length == 3) {
            day = Integer.parseInt(date[0]);
            month = Integer.parseInt(date[1]);
            year = Integer.parseInt(date[2]);
        }
        else if (date.length == 2) {
            month = Integer.parseInt(date[0]);
            year = Integer.parseInt(date[1]);
        }
        else if (date.length == 1) {
            year = Integer.parseInt(date[0]);
        }

        for (Message message : messageList) {
            if ((message.getDateTime().getYear() == year && message.getDateTime().getMonth().getValue() == month
                    && message.getDateTime().getDayOfMonth() == day)
                    || (message.getDateTime().getYear() == year && message.getDateTime().getMonth().getValue() == month && day == 0)
                    || (message.getDateTime().getYear() == year && month == 0 && day == 0)) {
                listByDate.add(message);
            }
        }
        return listByDate;
    }

    // if second = -1 -> all seconds in minute; if second = -1 and minute = -1 -> all minutes in hour
    private List<Message> filterMessagesByTime(String timeString, List<Message> messageList) throws NumberFormatException {
        if (timeString == null) {
            return messageList;
        }
        List<Message> listByTime = new LinkedList<>();

        int hour = -1;
        int minute = -1;
        int second = -1;

        String[] time = timeString.split(":");
        if (time.length == 3) {
            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);
            second = Integer.parseInt(time[2]);
        }
        else if (time.length == 2) {
            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);
        }
        else if (time.length == 1) {
            hour = Integer.parseInt(time[0]);
        }

        for (Message message : messageList) {
            if ((message.getDateTime().getHour() == hour && message.getDateTime().getMinute() == minute
                    && message.getDateTime().getSecond() == second)
                    || (message.getDateTime().getHour() == hour && message.getDateTime().getMinute() == minute && second == -1)
                    || (message.getDateTime().getHour() == hour && minute == -1 && second == -1)) {
                listByTime.add(message);
            }
        }
        return listByTime;
    }

    private List<Message> filterMessagesByWeekday(DayOfWeek weekday, List<Message> messageList) {
        if (weekday == null) {
            return messageList;
        }
        List<Message> listByWeekday = new LinkedList<>();

        for (Message message : messageList) {
            if (message.getDateTime().getDayOfWeek() == weekday) {
                listByWeekday.add(message);
            }
        }
        return listByWeekday;
    }

    public List<Message> filterList(List<Message> messageList) {
        List<Message> filteredList = filterMessagesByDate(dateString, messageList);
        filteredList = filterMessagesByTime(timeString, filteredList);
        filteredList = filterMessagesByWeekday(weekday, filteredList);
        return filteredList;
    }
}
