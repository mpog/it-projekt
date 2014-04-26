package at.itprojekt;

import at.itprojekt.konjunktiv.KonjParser;
import at.itprojekt.statbuddy.StatParser;
import at.itprojekt.zip.Zipper;

import java.io.PrintStream;

public class Tester extends Thread {
    private final PrintStream out;
    private final DataPair whole;
    private final DataPair[] single;
    private final Language language;
    private final static DataPair[] kvpA = new DataPair[0];

    /**
     * @param out      Printstream used to print the results in
     * @param levels   All levels of the project as an array
     * @param headings All headings of the project as an array
     * @param texts    All full-texts of the project as an array
     * @param language Language of the project
     */
    public Tester(PrintStream out, int[] levels, String[] headings, String[] texts, Language language) {
        if (out == null || levels == null || headings == null || texts == null || language == null)
            throw new IllegalArgumentException(new NullPointerException("Parameter must not be null."));
        if (levels.length != headings.length || headings.length != texts.length)
            throw new IllegalArgumentException("Length of all arrays has to be the same");
        single = new DataPair[levels.length];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < levels.length; i++) {
            single[i] = new DataPair(levels[i], headings[i], texts[i]);
            stringBuilder.append(texts[i]);
        }
        whole = new DataPair(0, "Whole project", stringBuilder.toString());
        this.out = out;
        this.language = language;
    }

    /**
     * Prints test-output to System.out
     */
    @Override
    public void run() {
        float zipped = 0;
        final String start = " start", end = " end", project = "Projekt", report = "Report", tips = "Tips", zipResult = "ZIP result: ", numberKonj = "# of konjunctives found: ", abbreviation = "abbreviation";
        //Analyse whole document
        out.println(report + start);
        out.println(project + start);
        out.println(numberKonj + new KonjParser(whole.value, language).toString());
        zipped = new Zipper(whole.value).getSizeFactor();
        if (zipped > -1f)
            out.println(zipResult + zipped);
        out.println("Text analyser's result:" + new StatParser(whole.value));
        out.println(project + end);
        //Preparations for analysing a single document
        int sentenceSignsInKeys = 0, abbreviationsUsed = 0, valueEndSignMissing = 0, headingLongerEqualThenText = 0;
        for (int lineNumber = 0; lineNumber < single.length; lineNumber++) {
            //System.out.println(single[lineNumber].toString());
            out.println((lineNumber + 2) + start);
            out.println(numberKonj + new KonjParser(single[lineNumber].value, language).toString());
            zipped = new Zipper(single[lineNumber].value).getSizeFactor();
            if (zipped > -1f) // If the ZIP-result is valid, print it
                out.println(zipResult + zipped);
            // Make the statistics
            StatParser statParserKey = new StatParser(single[lineNumber].key), statParserValue = new StatParser(single[lineNumber].value);
            // If the key has a sentence separator, report it
            if (statParserKey.allSentenceSeperators > 0) {
                sentenceSignsInKeys++;
                System.out.println(single[lineNumber].key + " has a sentence sign in it");
            }
            // Log the # of abbreviations
            abbreviationsUsed += statParserValue.abbreviations;
            abbreviationsUsed += statParserKey.abbreviations;
            if (statParserKey.abbreviations > 0)
                System.out.println(single[lineNumber].key + " uses " + statParserKey.abbreviations + " " + abbreviation + "(s)");
            if (statParserValue.abbreviations > 0)
                System.out.println(single[lineNumber].value + " uses " + statParserValue.abbreviations + " " + abbreviation + "(s)");
            //Log the # of missing end sentence signs
            if (!statParserValue.sentenceSignAtEnd) {
                valueEndSignMissing++;
                System.out.println(single[lineNumber].value + " lacks an end sentence sign");
            }
            // Check if the heading is shorter then the text
            if (single[lineNumber].key.length() >= single[lineNumber].value.length()) {
                headingLongerEqualThenText++;
                System.out.println("Line number " + single[lineNumber].level + "(" + single[lineNumber].key + ") has a shorter heading then text");
            }
            out.println("Text analyser's result: Key:" + statParserKey + " Value:" + statParserValue);
            out.println((lineNumber + 2) + end);
        }
        // Print the tips section
        out.println(tips + start);
        if (sentenceSignsInKeys > 0) {
            out.println("All in all " + sentenceSignsInKeys + " places have been found in headings, where a sentence ends or a subsentence starts/ends.");
        }
        if (abbreviationsUsed > 0) {
            out.println("All in all " + abbreviationsUsed + " abbreviations have been found. Make your language clearer and use full words only.");
        }
        if (valueEndSignMissing > 0) {
            out.println("All in all " + valueEndSignMissing + " sentence signs are missing at the end of texts.");
        }
        if (headingLongerEqualThenText > 0) {
            out.println("All in all " + headingLongerEqualThenText + " headings have maximum the same length compard to the corresponding text.");
        }
        out.println(tips + end);
        out.println(report + end);
    }
}
