package at.itprojekt.statbuddy;

import at.itprojekt.Helper;
import at.itprojekt.RegEx;

public class StatParser {
    final public int chars, words, allSentenceSeperators, endSentenceSeperators, innerSentenceSeperators, abbreviations, sentences;
    final public boolean sentenceSignAtEnd;

    public StatParser(String toparse) {
        if (toparse == null)
            throw new IllegalArgumentException(new NullPointerException("toparse"));
        //# of detected abbreviations
        abbreviations = Helper.countArray(toparse.split(RegEx.Abbreviations));
        chars = toparse.length();
        if (chars > 0)
            words = Helper.countArray(Helper.splitWords(toparse)) + 1; // As a sentence has at least one word, if there is more than 0 chars
        else
            words = 0; // If there are no chars, tehre cannot be words
        // # of sentence seperators, which seperate full sentences from each other
        endSentenceSeperators = Helper.countArray(toparse.split(RegEx.EndSentenceSeperators)) - abbreviations;
        //# of sentence seperators, which seperate subsentences from the main sentence or from each other
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
