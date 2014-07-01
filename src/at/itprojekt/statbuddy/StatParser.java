package at.itprojekt.statbuddy;


import at.itprojekt.DataPair;
import at.itprojekt.Helper;
import at.itprojekt.Language;
import at.itprojekt.RegEx;
import at.itprojekt.konjunktiv.KonjParser;
import at.itprojekt.xml.generated.Report;
import at.itprojekt.xml.generated.Report.Project;
import at.itprojekt.xml.generated.Report.Project.Result;
import at.itprojekt.zip.Zipper;

public class StatParser {
    final public int chars, words, allSentenceSeperators, endSentenceSeperators, innerSentenceSeperators, abbreviations, sentences, longestSentenceNumberOfWords, subjunctives;
    final public boolean sentenceSignAtEnd;
    final public float zipped;

    /**
     * @param toparse The String which to parse
     * @param glossar A list of words, which are ok
     */
    public StatParser(String toparse, Language language, DataPair whole, String[] glossar) {
        if (toparse == null)
            throw new IllegalArgumentException(new NullPointerException("toparse"));
        //zip
        zipped = new Zipper(whole.value).getSizeFactor();
        //# of subjunctives
        subjunctives = new KonjParser(toparse, language).getKonjunktive();
        //# of detected abbreviations
        String tmp = " " + toparse + " ";
        String nonWordChar = "\\W";
        if (glossar != null) {
            for (String entry : glossar) {
                String regex = nonWordChar + entry + nonWordChar;
                tmp = tmp.replaceAll(regex, "");
            }
        }
        String[] Sabbreviations1 = tmp.split(RegEx.Abbreviations), Sabbreviations2 = tmp.split(RegEx.Acronyms);
        abbreviations = Helper.countArray(Sabbreviations1) + Helper.countArray(Sabbreviations2);
        chars = toparse.length();
        if (chars > 0)
            words = Helper.countArray(Helper.splitWords(toparse)) + 1; // As a sentence has at least one word, if there is more than 0 chars
        else
            words = 0; // If there are no chars, tehre cannot be words
        String[] sentenceArray = toparse.split(RegEx.EndSentenceSeperators);
        // # of sentence nonWordChar, which seperate full sentences from each other
        endSentenceSeperators = Helper.countArray(sentenceArray) - abbreviations;
        //# of sentence nonWordChar, which seperate subsentences from the main sentence or from each other
        innerSentenceSeperators = Helper.countArray(toparse.split(RegEx.InnerSentenceSeperators));
        //# combination of both
        allSentenceSeperators = Helper.countArray(toparse.split(RegEx.AllSententenceSeperators)) - abbreviations;
        // boolean value, if there is a sentence sign at the end of the String
        sentenceSignAtEnd = toparse.matches(RegEx.EndSentenceSign);
        //If there is no sentence sign at the end, this sentence would not be counted, add it to the total # of sentences
        sentences = allSentenceSeperators + (sentenceSignAtEnd ? 0 : 1);
        //Max sentence length
        int i = -1;
        for (String e : sentenceArray) {
            int length = Helper.countArray(e.split(RegEx.Space));
            if (length > i)
                i = length;
        }

        longestSentenceNumberOfWords = i + 1; //Zählfehler ausgleichen
    }

    /**
     * @param toparse The String which to parse
     * @param glossar A list of words, which are ok
     * @param glossar If glossar == null, abbreviation-whitelist is disabled
     */
    public StatParser(String toparse, Language language, String[] glossar) {
        if (toparse == null)
            throw new IllegalArgumentException(new NullPointerException("toparse"));
        zipped = 0;
        //# of subjunctives
        subjunctives = new KonjParser(toparse, language).getKonjunktive();
        //# of detected abbreviations
        String tmp = " " + toparse + " ";
        String nonWordChar = "\\W";
        if (glossar != null) {
            for (String entry : glossar) {
                String regex = nonWordChar + entry + nonWordChar;
                tmp = tmp.replaceAll(regex, "");
            }
        }
        String[] Sabbreviations1 = tmp.split(RegEx.Abbreviations), Sabbreviations2 = tmp.split(RegEx.Acronyms);
        abbreviations = Helper.countArray(Sabbreviations1) + Helper.countArray(Sabbreviations2);
        chars = toparse.length();
        // boolean value, if there is a sentence sign at the end of the String
        sentenceSignAtEnd = toparse.matches(RegEx.EndSentenceSign);
        if (chars > 0)
            words = Helper.countArray(Helper.splitWords(toparse)) + 1; // As a sentence has at least one word, if there is more than 0 chars
        else
            words = 0; // If there are no chars, there cannot be words
        String[] sentenceArray = toparse.split(RegEx.EndSentenceSeperators);
        // # of sentence nonWordChars, which separate full sentences from each other
        endSentenceSeperators = Helper.countArray(sentenceArray) - abbreviations + 1;
        //# of sentence nonWordChars, which separate subsentence from the main sentence or from each other
        innerSentenceSeperators = Helper.countArray(toparse.split(RegEx.InnerSentenceSeperators));
        //# combination of nonWordChars which separate sentences or sentences from each other
        allSentenceSeperators = Helper.countArray(toparse.split(RegEx.AllSententenceSeperators)) - abbreviations + 1;

        //If there is no sentence sign at the end, this sentence would not be counted, add it to the total # of sentences
        sentences = allSentenceSeperators + (sentenceSignAtEnd ? 0 : 1);
        //Max sentence length
        int i = -1;
        for (String e : sentenceArray) {
            int length = Helper.countArray(e.split(RegEx.Space));
            if (length > i)
                i = length;
        }

        longestSentenceNumberOfWords = i + 1; //Zählfehler ausgleichen
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
                ", longestSentenceNumberOfWords=" + longestSentenceNumberOfWords +
                ", sentenceSignAtEnd=" + sentenceSignAtEnd +
                '}';
    }


    public Report.Project.Result getResult() {
        Result result = new Project.Result();
        result.setZip(zipped);
        result.setSubjunctives(subjunctives);
        result.setAbbreviations(abbreviations);
        result.setAllSentenceSeperators(allSentenceSeperators);
        result.setChars(chars);
        result.setInnerSentenceSeperators(innerSentenceSeperators);
        result.setSentences(sentences);
        result.setWords(words);
        result.setRuleResults(this.getRuleResults());
        result.setSentenceMaxLengthWords(longestSentenceNumberOfWords);
        return result;
    }

    public Report.Project.Result.RuleResults getRuleResults() {
        Report.Project.Result.RuleResults result = new Report.Project.Result.RuleResults();
        return result;
    }

    public Report.Line.Result.Key getKey() {
        Report.Line.Result.Key key = new Report.Line.Result.Key();
        key.setSubjunctives(subjunctives);
        key.setAbbreviations(abbreviations);
        key.setAllSentenceSeperators(allSentenceSeperators);
        key.setChars(chars);
        key.setInnerSentenceSeperators(innerSentenceSeperators);
        key.setSentenceSignAtEnd(sentenceSignAtEnd);
        key.setSentences(sentences);
        key.setWords(words);
        key.setSentenceMaxLengthWords(longestSentenceNumberOfWords);
        if (key.getRuleResults() == null)
            key.setRuleResults(new Report.Line.Result.Key.RuleResults());
        return key;
    }

    public Report.Line.Result.Key.RuleResults.RuleResult getKeyRuleResult() {
        Report.Line.Result.Key.RuleResults.RuleResult result = new Report.Line.Result.Key.RuleResults.RuleResult();
        return result;
    }

    public Report.Line.Result.Value getValue() {
        Report.Line.Result.Value value = new Report.Line.Result.Value();
        value.setSubjunctives(subjunctives);
        value.setAbbreviations(abbreviations);
        value.setAllSentenceSeperators(allSentenceSeperators);
        value.setChars(chars);
        value.setInnerSentenceSeperators(innerSentenceSeperators);
        value.setSentenceSignAtEnd(sentenceSignAtEnd);
        value.setSentences(sentences);
        value.setWords(words);
        value.setSentenceMaxLengthWords(longestSentenceNumberOfWords);
        
        if (value.getRuleResults() == null)
            value.setRuleResults(new Report.Line.Result.Value.RuleResults());
        return value;
    }

    public Report.Line.Result.Value.RuleResults.RuleResult getValRuleResults() {
        Report.Line.Result.Value.RuleResults.RuleResult result = new Report.Line.Result.Value.RuleResults.RuleResult();
        return result;
    }
    
}
