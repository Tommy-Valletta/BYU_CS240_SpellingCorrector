package spell;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {

        // pass the filename to the file as a parameter
        File file = new File(dictionaryFileName);
        Scanner sc = new Scanner(file);

        List<String> rawStrings = new ArrayList<String>();
        while (sc.hasNextLine())
            rawStrings.add(sc.nextLine());
        //System.out.println(rawStrings);

        ITrie myTrie = new Trie();
        int i = 0;
        while (i < rawStrings.size()) {
            //System.out.println(rawStrings.get(i));
            myTrie.add(rawStrings.get(i));
            i++;
        }

    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
