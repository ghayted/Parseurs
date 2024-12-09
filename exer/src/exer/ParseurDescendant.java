package exer;

import java.util.*;

public class ParseurDescendant {
    private String input;   // La chaîne à analyser
    private int pos;        // Position actuelle dans la chaîne

    public ParseurDescendant(String input) {
        this.input = input;
        this.pos = 0;
    }

    // Méthode principale pour analyser la chaîne selon la règle S
    public boolean parseS() {
        // Si la chaîne est vide et nous avons réussi à consommer toute la chaîne, on accepte S comme valide
        if (pos == input.length()) {
            return true;
        }

        // Essayons de correspondre à la règle S → bSb
        if (match('b')) {
            if (parseS()) {
                if (match('b')) {
                    return true;
                }
            }
        }

        // Essayons de correspondre à la règle S → cAc
        if (match('c')) {
            if (parseA()) {
                if (match('c')) {
                    return true;
                }
            }
        }
        return false;  // Si aucune règle ne correspond
    }

    // Méthode pour analyser A
    public boolean parseA() {
        // Si la chaîne est vide et nous avons réussi à consommer toute la chaîne, on accepte A comme valide
        if (pos == input.length()) {
            return true;
        }

        // Essayons de correspondre à la règle A → bAA
        if (match('b')) {
            if (parseA() && parseA()) {
                return true;
            }
        }

        // Essayons de correspondre à la règle A → cASAb
        if (match('c')) {
            if (parseA() && parseS() && parseA() && match('b')) {
                return true;
            }
        }

        // Essayons de correspondre à la règle A → dcb
        if (match('d')) {
            if (match('c') && match('b')) {
                return true;
            }
        }
        return false;  // Si aucune règle ne correspond
    }

    // Fonction pour faire correspondre un caractère et avancer dans la chaîne
    private boolean match(char c) {
        if (pos < input.length() && input.charAt(pos) == c) {
            pos++;
            return true;
        }
        return false;
    }

    // Fonction pour vérifier si nous avons parcouru toute la chaîne
    public boolean parse() {
        boolean result = parseS(); // Commence par analyser S
        return result && pos == input.length(); // Si toute la chaîne a été consommée, c'est valide
    }

    public static void main(String[] args) {
        List<String> testCases = Arrays.asList(
            "cdcbc",         // Valide (S → cAc → c(dcb)c)
            "bcdcbcb",       // Valide (S → bSb → b(cAc)b → b(dcb)b)
            "cbdcbdcbc",     // Valide (S → cAc → c(cASAb)c → c(c(dcb)c)c)
            "ccdcbcdcbcdcbbcr", // Invalide (trop complexe, dépasse les règles)
            "cdcbbb",        // Invalide (ne correspond à aucune règle)
            "cdcb",          // Invalide (ne correspond à aucune règle)
            ""               // Valide (la chaîne vide est maintenant valide)
        );

        for (String testCase : testCases) {
            ParseurDescendant parser = new ParseurDescendant(testCase);
            System.out.println("Chaîne : \"" + testCase + "\" -> " + (parser.parse() ? "Valide" : "Invalide"));
        }
    }
}
