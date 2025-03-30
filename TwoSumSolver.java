import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TwoSumSolver {
    private Dictionary dictionary;
    

    public TwoSumSolver(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public List<WordPair> findTwoSumPairs(String phrase) {
        String cleanPhrase = cleanPhrase(phrase); // Nettoyer la phrase (supprimer espaces et ponctuation)

        String sortedPhrase = Dictionary.getSortedLetters(cleanPhrase); // permutation de la phrase p

        Map<Character, Integer> phraseFreq = getCharFrequency(sortedPhrase); // Créer une table de fréquence des caractères de la phrase
        
        List<WordPair> results = new ArrayList<>();
        
        // Pour chaque mot du dictionnaire
        for (String word : dictionary.getAllWords()) {
            Map<Character, Integer> wordFreq = getCharFrequency(word); // fréquence des caractères du mot (u)

            Map<Character, Integer> complementFreq = calculateComplementFreq(phraseFreq, wordFreq);

            if (complementFreq != null) {
                // Convertir la table de fréquence en chaîne ordonnée
                String complement = freqMapToSortedString(complementFreq);
                
                // Si le complément existe dans la table de hashage
                if (dictionary.containsPermutation(complement)) {
                    // Pour chaque mot correspondant au complément
                    for (String complementWord : dictionary.getWordsForPermutation(complement)) {
                        // Éviter les doublons (si le mot est identique à son complément)
                        if (!word.equals(complementWord)) {
                            results.add(new WordPair(word, complementWord));
                        }
                    }
                }
            }
        }
        
        return results;
    }


    private String cleanPhrase(String phrase) {
        return Dictionary.cleanWord(phrase);
    }


    private Map<Character, Integer> getCharFrequency(String str) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : str.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        return freq;
    }
    

    private Map<Character, Integer> calculateComplementFreq(
            Map<Character, Integer> phraseFreq, Map<Character, Integer> wordFreq) {
        Map<Character, Integer> complementFreq = new HashMap<>(phraseFreq);
        
        // Pour chaque caractère du mot
        for (Map.Entry<Character, Integer> entry : wordFreq.entrySet()) {
            char c = entry.getKey();
            int count = entry.getValue();
            
            // Vérifier si le caractère existe dans la phrase avec une fréquence suffisante
            if (!complementFreq.containsKey(c) || complementFreq.get(c) < count) {
                return null;
            }
            

            int newCount = complementFreq.get(c) - count;
            if (newCount > 0) {
                complementFreq.put(c, newCount);
            } else {
                complementFreq.remove(c);
            }
        }
        
        return complementFreq;
    }
    

    private String freqMapToSortedString(Map<Character, Integer> freqMap) {
        StringBuilder sb = new StringBuilder();
        

        List<Character> chars = new ArrayList<>(freqMap.keySet());
        chars.sort(null); // l'ordre naturel des caractères
        

        for (char c : chars) {
            int count = freqMap.get(c);
            for (int i = 0; i < count; i++) {
                sb.append(c);
            }
        }
        
        return sb.toString();
    }

    public static class WordPair {
        private String word1;
        private String word2;
        
        public WordPair(String word1, String word2) {
            this.word1 = word1;
            this.word2 = word2;
        }
        
        public String getWord1() {
            return word1;
        }
        
        public String getWord2() {
            return word2;
        }
        
        @Override
        public String toString() {
            return "(" + word1 + ", " + word2 + ")";
        }
    }
}