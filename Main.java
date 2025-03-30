import java.util.List;
import java.util.Scanner;

/**
 * Classe principale pour tester la résolution du problème de la 2-somme
 */
public class Main {
    public static void main(String[] args) {
        // Chemin vers le fichier dictionnaire
        String dictionaryPath = "minidico.txt";

        System.out.println("Chargement du dictionnaire...");
        Dictionary dictionary = new Dictionary();
        dictionary.loadFromFile(dictionaryPath);
        System.out.println("Dictionnaire chargé avec " + dictionary.size() + " mots.");

        // Créer le solveur
        TwoSumSolver solver = new TwoSumSolver(dictionary);

        // Interface utilisateur interactive
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nEntrez une phrase pour chercher sa 2-somme (ou 'q' pour quitter) :");
            String phrase = scanner.nextLine();

            if (phrase.equalsIgnoreCase("q")) {
                break;
            }

            long startTime = System.currentTimeMillis();
            List<TwoSumSolver.WordPair> results = solver.findTwoSumPairs(phrase);
            long endTime = System.currentTimeMillis();

            if (results.isEmpty()) {
                System.out.println("Aucune 2-somme trouvée pour \"" + phrase + "\"");
            } else {
                System.out.println("2-sommes trouvées pour \"" + phrase + "\" :");
                for (TwoSumSolver.WordPair pair : results) {
                    System.out.println("- " + pair);
                }
            }

            System.out.println("Temps de calcul : " + (endTime - startTime) + " ms");
        }

        scanner.close();
        System.out.println("Au revoir !");
    }

}