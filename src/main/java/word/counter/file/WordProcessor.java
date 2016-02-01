package word.counter.file;

import word.counter.web.resources.pojo.CounterResults;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * This class is the main operation controller class
 * Created by prageeth_2 on 1/31/2016.
 */
public class WordProcessor {

    /**
     * This method gets the clientId and input stream obtained from the client request.
     * @param clientId ClientID or the username of the client
     * @param inputStream inputstream of the long sentence
     * @return Process results which include maximum, minimum and median words
     */
    public CounterResults generateWordCounterResuts(String clientId, InputStream inputStream) {

        BufferedReader bufferedReader = FileHandler.getNewStreamReader(inputStream);
        BufferedWriter bufferedWriter = FileHandler.getNewFileWriter();
        String spCharactersAndwhiteSpaces = "[{}().,';:\"!`\t\r\n-=+/\\*$#@%^|]";
        HashMap<String, Long> wordCounterMap = new HashMap<>();
        String str;
        try {
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println("str = " + str);
                // log the string to a file
                bufferedWriter.write(str);
                // split the string and generate a word count hash map
                String[] characters = str.split(spCharactersAndwhiteSpaces);
                for (String word : characters) {
                    if (wordCounterMap.get(word) == null) {
                        wordCounterMap.put(word, (long) 1);
                    } else {
                        wordCounterMap.put(word, wordCounterMap.get(word) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileHandler.close(bufferedWriter);
            FileHandler.close(bufferedReader);
        }
        // sort the values
        TreeSet<Long> sortedCountSet = new TreeSet<>();
        sortedCountSet.addAll(wordCounterMap.values());
        // calculate the median item
        int medianEntry = sortedCountSet.size() / 2;
        // calculate the second median number when the word count is even
        int evenMedianEntry = sortedCountSet.size() % 2 == 0 ? medianEntry - 1 : -1;
        long maxCount = sortedCountSet.last();
        ArrayList<Long> sortedList = new ArrayList<>(sortedCountSet);
        // identify the median word count
        long medCount = sortedList.get(medianEntry);
        // if there are even number of words, then calculate the second median count
        long evenMedCount = -1;
        if (evenMedianEntry >= 0) {
            evenMedCount = sortedList.get(evenMedianEntry);
        }
        long minCount = sortedCountSet.first();

        // Generate the output strings
        StringBuilder maxWordString = new StringBuilder();
        StringBuilder medWordString = new StringBuilder();
        StringBuilder evenMedWordString = new StringBuilder();
        StringBuilder minWordString = new StringBuilder();
        for (String word : wordCounterMap.keySet()) {
            if (maxCount == wordCounterMap.get(word)) {
                maxWordString.append(word).append(",");
            }
            if (minCount == wordCounterMap.get(word)) {
                minWordString.append(word).append(",");
            }
            if (medCount == wordCounterMap.get(word)) {
                medWordString.append(word).append(",");
            }
            if (evenMedCount > 0 && evenMedCount == wordCounterMap.get(word)) {
                evenMedWordString.append(word).append(",");
            }
        }
        // Generate the response object
        CounterResults counterResults = new CounterResults();
        counterResults.setMaxWord(maxCount);
        counterResults.setMedWord(medCount);
        counterResults.setEvenMedWord(evenMedCount);
        counterResults.setMinWord(minCount);
        counterResults.setMaxWordString(maxWordString.toString());
        counterResults.setMedWordString(medWordString.toString());
        counterResults.setEvenMedWordString(evenMedWordString.toString());
        counterResults.setMinWordString(minWordString.toString());

        return counterResults;
    }
}
