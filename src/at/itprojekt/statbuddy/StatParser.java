package at.itprojekt.statbuddy;

import at.itprojekt.Helper;
import at.itprojekt.RegEx;

public class StatParser {
    final public int chars, words, allSentenceSeperators, endSentenceSeperators, innerSentenceSeperators, abbrevisions, sentences;
    final public boolean sentenceSignAtEnd;

    public StatParser(String toparse) {
        if (toparse == null)
            throw new IllegalArgumentException(new NullPointerException("toparse"));
        chars = toparse.length();
        words = Helper.countArray(Helper.splitWords(toparse)) + 1;
        endSentenceSeperators = Helper.countArray(toparse.split(RegEx.EndSentenceSeperators));
        innerSentenceSeperators = Helper.countArray(toparse.split(RegEx.InnerSentenceSeperators));
        allSentenceSeperators = Helper.countArray(toparse.split(RegEx.AllSententenceSeperators));
        abbrevisions = Helper.countArray(toparse.split(RegEx.Abbrevisions));
        sentenceSignAtEnd = toparse.matches(RegEx.EndSentenceSign);
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
                ", abbrevisions=" + abbrevisions +
                ", sentences=" + sentences +
                ", sentenceSignAtEnd=" + sentenceSignAtEnd +
                '}';
    }
}
