package spell;
import java.io.File;
import java.io.IOException;
import java.io.SyncFailedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    ITrie myTrie;
    private int occurCount;
    private String bestSuggestion;
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {

        // pass the filename to the file as a parameter
        File file = new File(dictionaryFileName);
        Scanner sc = new Scanner(file);

        List<String> rawStrings = new ArrayList<String>();
        while (sc.hasNextLine()) {
            String[] multWordLine = sc.nextLine().split(" ", 0);
            Collections.addAll(rawStrings, multWordLine);
        }
        //System.out.println(rawStrings);

        myTrie = new Trie();
        int i = 0;
        while (i < rawStrings.size()) {
            //System.out.println(rawStrings.get(i));
            myTrie.add(rawStrings.get(i));
            i++;
        }

    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        if (this.myTrie.find(inputWord) != null) return inputWord.toLowerCase();
        bestSuggestion = null;
        occurCount = 0;
        List<String> oneEdits = new ArrayList<String>();
        List<String> doubleEdits = new ArrayList<String>();
        //TODO: Run all the edit distance one methods. They should each return a STRING which is the word that occurred
        // the most in the Trie after edit distance one. Maybe they should each return an array of every possible edit...
        // WITHIN each method, I want to be able to set the suggest word to the best match so far

        deletion(inputWord, oneEdits);
        transposition(inputWord, oneEdits);
        alteration(inputWord, oneEdits);
        insertion(inputWord, oneEdits);
        if (bestSuggestion == null){
            deletion2(oneEdits, doubleEdits);
            transposition2(oneEdits, doubleEdits);
            alteration2(oneEdits, doubleEdits);
            insertion2(oneEdits, doubleEdits);
        }
        //TODO: If each method returns an array, then the editDistanceTwo methods will take an array and return an
        // array of SIZE * factor of edit distance growth. (eg. deletion is (absolute(word.length) - 1) * word.length

        //TODO: I think that my edit methods could all take an array (editDistanceOne would take an array with ONE word),
        // and return an array of all the words. I think this way I can have FOUR methods instead of EIGHT.
        //if(editOne != null) return editOne;
        //if(editTwo) != null) return editTwo;
        System.out.println(bestSuggestion);
        return bestSuggestion;
    }

    private void deletion(String inputWord, List<String> edits){

        String word = inputWord.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            StringBuilder testWord = new StringBuilder(word);
            String suggestion = testWord.deleteCharAt(i).toString();
            //TODO: if the suggestion -- after edit -- is found in the Trie, check if the node has a higher count. If
            // it does, then it occurs more often, and takes place as the "best word" (editOne)
            checkBestFit(edits, suggestion);
        }
    }

    private void checkBestFit(List<String> edits, String suggestion) {
        edits.add(suggestion);
        INode nodeMatch = this.myTrie.find(suggestion);
        if (nodeMatch != null) {
            if (bestSuggestion == null) {
                bestSuggestion = suggestion;
                occurCount = nodeMatch.getValue();
            }
            else if (nodeMatch.getValue() > occurCount) {
                bestSuggestion = suggestion;
                occurCount = nodeMatch.getValue();
            }
        }
    }

    private void deletion2(List<String> oneEdits, List<String> doubleEdits){
        for (int i = 0; i < oneEdits.size(); i++){
            deletion(oneEdits.get(i), doubleEdits);
        }
    }

    private void transposition2(List<String> oneEdits, List<String> doubleEdits){
        for (int i = 0; i < oneEdits.size(); i++){
            transposition(oneEdits.get(i), doubleEdits);
        }
    }

    private void alteration2(List<String> oneEdits, List<String> doubleEdits){
        for (int i = 0; i < oneEdits.size(); i++){
            alteration(oneEdits.get(i), doubleEdits);
        }
    }

    private void insertion2(List<String> oneEdits, List<String> doubleEdits){
        for (int i = 0; i < oneEdits.size(); i++){
            insertion(oneEdits.get(i), doubleEdits);
        }
    }

    private void transposition(String inputWord, List<String> edits){
        if (inputWord == null || inputWord.length() < 2) return;
        String word = inputWord.toLowerCase();
        for (int i = 0; i < word.length() - 1; i++) {
            char temp = word.charAt(i);
            char temp2 = word.charAt(i + 1);
            StringBuilder testWord = new StringBuilder(word);
            testWord.setCharAt(i, temp2);
            testWord.setCharAt(i + 1, temp);
            //System.out.println(testWord.toString());
            checkBestFit(edits, testWord.toString());
            //if (this.myTrie.find(suggestion) != null) return suggestion;
        }
    }
    private void alteration(String inputWord, List<String> edits){
        String word = inputWord.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < 25; j++) {
                StringBuilder testWord = new StringBuilder(word);
                testWord.setCharAt(i, (char)('a' + j));
                checkBestFit(edits, testWord.toString());
            }
        }
    }
    private void insertion(String inputWord, List<String> edits){
        String word = inputWord.toLowerCase();
        for (int i = 0; i <= word.length(); i++) {
            for (int j = 0; j < 25; j++) {
                StringBuilder testWord = new StringBuilder(word);
                testWord.insert(i, (char)('a' + j));
                checkBestFit(edits, testWord.toString());
            }
        }
    }
}
