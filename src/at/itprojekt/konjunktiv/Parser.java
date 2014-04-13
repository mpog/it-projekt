package at.itprojekt.konjunktiv;

import at.itprojekt.Helper;
import at.itprojekt.Language;
import at.itprojekt.RegEx;

public class Parser {
    int konjunktiveFound = 0;

    public Parser(String s, Language language) {
        String[] words = Helper.splitWords(s);
        if (words == null || language == null)
            throw new NullPointerException("Parameter cannot be null");
        for (String word : words)
            if (isKonjunktiv(word, language))
                konjunktiveFound++;
    }

    private boolean isKonjunktiv(String w, Language language) {
        boolean isKonj = false;
        switch (language) {
            case En:
                int i = Helper.countArray(w.split(RegEx.EnglishKonjAsVagueness));
                if (i > 0)
                    isKonj = true;
                break;
            default: // GERMAN
                // Try if it is a modalverb
                int y = Helper.countArray(w.split(RegEx.GermanModalVerbs));
                if (y > 0)
                    isKonj = true;
                if (!isKonj) {
                    String baseform = w.split(RegEx.GermanVerbEnds)[0];
                    if (!w.equals(baseform)) {
                        // This has to be tested, might be a konjunktiv. A dictionary would be important here. Check for haben/sein/werden in the meantime
                        isKonj = baseform.matches(RegEx.GermanKonjStrongBasic);
                    }
                }
                break;
        }
        if (isKonj)
            System.out.println("\"" + w + "\" seems to be a konjunktiv");
        return isKonj;
    }


    @Override
    public String toString() {
        return Integer.toString(konjunktiveFound);
    }

}
