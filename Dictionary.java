import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Dictionary {
    private Map<String, List<String>> permutationTable;

    public Dictionary() {
        permutationTable = new HashMap<>();
    }

    public void loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                String cleanedWord = cleanWord(word);
                if (!cleanedWord.isEmpty()) {
                    String sortedWord = getSortedLetters(cleanedWord);

                    if (!permutationTable.containsKey(sortedWord)) {
                        permutationTable.put(sortedWord, new ArrayList<>());
                    }
                    permutationTable.get(sortedWord).add(word);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du dictionnaire: " + e.getMessage());
        }
    }


    public static String getSortedLetters(String word) {
        char[] chars = word.toLowerCase().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static String cleanWord(String word) {
        return word.toLowerCase().replaceAll("[^a-zàâäæçéèêëîïôœùûüÿ]", "");
    }

    public boolean containsPermutation(String sortedLetters) {
        return permutationTable.containsKey(sortedLetters); // clé - permutation
    }

    public List<String> getWordsForPermutation(String sortedLetters) {
        return permutationTable.get(sortedLetters); // valeur - la liste des mots qui ont la meme permutation
    }


    public int size() {
        int totalWords = 0;
        for (List<String> words : permutationTable.values()) {
            totalWords += words.size();
        }
        return totalWords;
    }
    

    public List<String> getAllWords() {
        List<String> allWords = new ArrayList<>();
        for (List<String> words : permutationTable.values()) {
            allWords.addAll(words);
        }
        return allWords;
    }
}