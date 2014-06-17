package at.itprojekt.konjunktiv;

import at.itprojekt.Helper;
import at.itprojekt.Language;
import at.itprojekt.RegEx;

public class KonjParser {
    int konjunktiveFound = 0;

    public KonjParser(String s, Language language) {
        String[] words = Helper.splitWords(s);
        if (words == null || language == null)
            throw new NullPointerException("Parameter cannot be null");
        for (String word : words)
            if (isKonjunktiv(word, language))
                konjunktiveFound++;
    }

    /**
     *
     * @param word A word. (Like "have")
     * @param language The language of the text
     * @return Returns true if a konjunctive of the specified language is found
     */
    private boolean isKonjunktiv(String word, Language language) {
        boolean isKonj = false;
        String w = word += " ";
        switch (language) {
            case En:
                String[] tmp = w.split(RegEx.EnglishKonjAsVagueness);
                isKonj = tmp.length > 0 && !w.equals(tmp[0]);
                break;
            default: // GERMAN
                // Try if it is a modalverb
                int y = Helper.countArray(w.split(RegEx.GermanModalVerbs));
                if (y > 0)
                    isKonj = true;
                // If it is no modalverb, try to cut of the word end and test if it a "schwaches Verb" or not. The regex additionally detects some "starke Verben"
                if (!isKonj) {
                    String baseform = w.split(RegEx.GermanVerbEnds)[0];
                    if (!w.equals(baseform)) {
                        isKonj = baseform.matches(RegEx.GermanKonjStrongBasic);
                    }
                }
                break;
        }
        if (isKonj)
            System.out.println("\"" + word + "\" seems to be a konjunktiv");
        return isKonj;
    }


    @Override
    public String toString() {
        return Integer.toString(konjunktiveFound);
    }
    
    public Integer getKonjunktive() {
    	return konjunktiveFound;
    }
    
}
