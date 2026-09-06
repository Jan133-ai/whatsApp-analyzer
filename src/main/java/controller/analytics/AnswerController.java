package controller.analytics;

import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import controller.AnalyticsController;
import model.ChatInfo;

public class AnswerController extends AnalyticsController {

    Map<String, Integer> answersGes;

    Map<String, Map<String, Integer>> answerIntevalMap;
    Map<String, Map<String, Float>> answerPercentIntevalMap;

    public AnswerController(ChatInfo currentChatInfo) throws FileNotFoundException{

        super(currentChatInfo);
        
        answersGes = currentChatInfo.callAnswers(false, 0, 0);

        answerIntevalMap = new LinkedHashMap<>();
        answerPercentIntevalMap = new LinkedHashMap<>();

        int[] intevalls = {0, 1, 5, 20, 60, 5*60, 24*60 - 1};
        for (int i = 1; i < intevalls.length; i++) {
            String[] timeStrings = {LocalTime.MIDNIGHT.plusMinutes(intevalls[i-1]).toString(),
                 LocalTime.MIDNIGHT.plusMinutes(intevalls[i]).toString()};
            String timeLabel = timeStrings[0] + " - " + timeStrings[1];
            Map<String, Integer> answerMap = currentChatInfo.callAnswers(true, intevalls[i-1], intevalls[i]);
            answerIntevalMap.put(timeLabel, answerMap);

            Map<String, Float> answerPercentMap = new HashMap<>();
            for (Map.Entry<String, Integer> me : answerMap.entrySet()) {
                String chatter = me.getKey();
                if (answersGes.get(chatter) != 0) {
                    answerPercentMap.put(chatter, (float) me.getValue() / answersGes.get(chatter) * 100);
                } else {
                    answerPercentMap.put(chatter, (float) 0);
                }
                
            }
            answerPercentIntevalMap.put(timeLabel, answerPercentMap);
        }
    }

    public Map<String, Integer> getAnswersGes() {
        return answersGes;
    }

    public Map<String, Map<String, Integer>> getAnswerIntevalMap() {
        return answerIntevalMap;
    }
    
    public Map<String, Map<String, Float>> getAnswerPercentIntevalMap() {
        return answerPercentIntevalMap;
    }
}
