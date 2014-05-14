package at.itprojekt.statbuddy;

import at.itprojekt.Helper;
import at.itprojekt.RegEx;

public class StatParser {
    final public int chars, words, allSentenceSeperators, endSentenceSeperators, innerSentenceSeperators, abbreviations, sentences;
    final public boolean sentenceSignAtEnd;

    /**
     * @param toparse The String which to parse
     */
    public StatParser(String toparse) {
        this(toparse, null);
    }

    /**
     * @param toparse The String which to parse
     * @param glossar A list of words, which are ok
     */
    public StatParser(String toparse, String[] glossar) {
        if (toparse == null)
            throw new IllegalArgumentException(new NullPointerException("toparse"));
        //# of detected abbreviations
        String tmp = " " + toparse + " ";
        String nonWordChar = "\\W";
        if (glossar != null) {
            for (String entry : glossar) {
                String regex =   nonWordChar + entry + nonWordChar ;
                tmp = tmp.replaceAll(regex, "");
            }
        }
        String[] Sabbreviations1 = tmp.split(RegEx.Abbreviations), Sabbreviations2 = tmp.split(RegEx.Acronyms);
       /* int abbCorrection = 0;
        if (glossar != null)
            for (String entry : glossar) {
                for (String a : Sabbreviations1) {
                    a = a.replace(" ", "");
                    if (/*a.startsWith(entry) || a.endsWith(entry)a.contains(entry))
                        abbCorrection++;
                }
                for (String b : Sabbreviations2) {
                    b = b.replace(" ", "");
                    if (/*b.startsWith(entry) || b.endsWith(entry)b.contains(entry))
                        abbCorrection++;
                }
            }*/
        abbreviations = Helper.countArray(Sabbreviations1) + Helper.countArray(Sabbreviations2)/* - abbCorrection*/;
        chars = toparse.length();
        if (chars > 0)
            words = Helper.countArray(Helper.splitWords(toparse)) + 1; // As a sentence has at least one word, if there is more than 0 chars
        else
            words = 0; // If there are no chars, tehre cannot be words
        // # of sentence nonWordChar, which seperate full sentences from each other
        endSentenceSeperators = Helper.countArray(toparse.split(RegEx.EndSentenceSeperators)) - abbreviations;
        //# of sentence nonWordChar, which seperate subsentences from the main sentence or from each other
        innerSentenceSeperators = Helper.countArray(toparse.split(RegEx.InnerSentenceSeperators));
        //# combination of both
        allSentenceSeperators = Helper.countArray(toparse.split(RegEx.AllSententenceSeperators)) - abbreviations;
        // boolean value, if there is a sentence sign at the end of the String
        sentenceSignAtEnd = toparse.matches(RegEx.EndSentenceSign);
        //If there is no sentence sign at the end, this sentence would not be counted, add it to the total # of sentences
        sentences = allSentenceSeperators + (sentenceSignAtEnd ? 0 : 1);
    }

    @Override
    public String toString() {
        return "StatParser{" +
                "chars=" + chars +
                ", words=" + words +
                ", allSentenceSeperators=" + allSentenceSeperators +
                ", endSentenceSeperators=" + endSentenceSeperators +
                ", innerSentenceSeperators=" + innerSentenceSeperators +
                ", abbreviations=" + abbreviations +
                ", sentences=" + sentences +
                ", sentenceSignAtEnd=" + sentenceSignAtEnd +
                '}';
    }
}
