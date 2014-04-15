package at.itprojekt.statbuddy;

import at.itprojekt.Helper;
import at.itprojekt.RegEx;

public class StatParser {
    final public int chars, words, allSentenceSeperators, endSentenceSeperators, innerSentenceSeperators, sentences, abbrevisions;

    public StatParser(String toparse) {
        if (toparse == null)
            throw new IllegalArgumentException(new NullPointerException("toparse"));
        chars = toparse.length();
        words = Helper.countArray(Helper.splitWords(toparse)) + 1;
        endSentenceSeperators = (sentences = Helper.countArray(toparse.split(RegEx.EndSentenceSeperators))) - 1;
        innerSentenceSeperators = Helper.countArray(toparse.split(RegEx.InnerSentenceSeperators));
        allSentenceSeperators = innerSentenceSeperators + endSentenceSeperators;
        abbrevisions = Helper.countArray(toparse.split(RegEx.Abbrevisions));
    }

    @Override
    public String toString() {
        return "{chars=" + chars +
                ", words=" + words +
                ", allSentenceSeperators=" + allSentenceSeperators +
                ", innerSentenceSeperators=" + innerSentenceSeperators +
                ", sentences=" + sentences +
                ", abbrevisions=" + abbrevisions +
                '}';
    }
}
