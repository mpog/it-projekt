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
     * @param out:     A PrintStream
     * @param Swhole   Full CSV-file
     * @param Ssingle  Single lines as CSV
     * @param language language of the text
     */
    public Tester(PrintStream out, String Swhole, String[] Ssingle, Language language) throws ParseException {
        try {
            if (language == null || out == null)
                throw new IllegalArgumentException(new NullPointerException("Parameter is NULL"));
            if (Swhole == null && Ssingle == null)
                throw new IllegalArgumentException("You must either pass a String or String[]");
            this.out = out;
            if (Ssingle == null) {
// Initialize Swhole
                StringBuilder stringBuilder = new StringBuilder();
                String[] lines = Swhole.split(RegEx.Newline);
                single = new DataPair[lines.length - 1];
                for (int i = 1; i < lines.length; i++) {
                    String[] tmp = lines[i].split("\",\"", 3);
                    stringBuilder.append(tmp[2]).append('\n');
                    single[i - 1] = new DataPair(Integer.parseInt(tmp[0].substring(1)), tmp[1], tmp[2]);
                }
                whole = new DataPair(-1, "Projekt", stringBuilder.toString());
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                single = new DataPair[Ssingle.length - 1];
                for (int i = 1; i < Ssingle.length; i++) {
                    String[] tmp = Ssingle[i].split("\",\"", 3);
                    stringBuilder.append(tmp[2]).append('\n');
                    single[i - 1] = new DataPair(Integer.parseInt(tmp[0].substring(1)), tmp[1], tmp[2]);
                }
                whole = new DataPair(-1, "Projekt", stringBuilder.toString());
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new ParseException(e);
        }
        this.language = language;
    }

    public Tester(String whole, String[] single, Language language) {
        this(System.out, whole, single, language);
    }

    public Tester(String whole, Language language) {
        this(whole, null, language);
    }

    public Tester(String whole) {
        this(whole, Language.De);
    }

    public Tester(String[] single, Language language) {
        this(null, single, language);
    }

    public Tester(String[] single) {
        this(single, Language.De);
    }

    @Override
    public void run() {
        float zipped = 0;
        final String start = " start", end = " end", project = "Projekt", report = "Report", tips = "Tips";
        //Analyse whole document
        out.println(report + start);
        if (whole != null) {
            out.println(project + start);
            out.println("# of konjunktives found: " + new KonjParser(whole.value, language).toString());
            zipped = new Zipper(whole.value).getSizeFactor();
            if (zipped > -1f)
                out.println("ZIP result: " + zipped);
            out.println("Text analyser's result:" + new StatParser(whole.value));
            out.println(project + end);
        }
        int sentenceSignsInKeys = 0, abbrevisionsUsed = 0;
        if (single != null)
            for (int lineNumber = 0; lineNumber < single.length; lineNumber++) {
                out.println((lineNumber + 2) + start);
                out.println("# of konjunktives found: " + new KonjParser(single[lineNumber].value, language).toString());
                zipped = new Zipper(single[lineNumber].value).getSizeFactor();
                if (zipped > -1f)
                    out.println("ZIP result: " + zipped);
                StatParser statParserKey = new StatParser(single[lineNumber].key), statParserValue = new StatParser(single[lineNumber].value);
                abbrevisionsUsed += statParserValue.abbrevisions;
                abbrevisionsUsed += statParserKey.abbrevisions;
                if (statParserKey.allSentenceSeperators >= 0) {
                    sentenceSignsInKeys++;
                    System.out.println(single[lineNumber].key + " has a sentence sign in it");
                }
                if (statParserKey.abbrevisions > 0)
                    System.out.println(single[lineNumber].key + " uses " + statParserKey.abbrevisions + " abbrvation(s)");
                if (statParserValue.abbrevisions > 0)
                    System.out.println(single[lineNumber].value + " uses " + statParserValue.abbrevisions + " abbrvation(s)");
                out.println("Text analyser's result: Key:" + statParserKey + " Value:" + statParserValue);
                out.println((lineNumber + 2) + end);
            }

        out.println(tips + start);
        if (sentenceSignsInKeys > 0) {
            out.println("All in all " + sentenceSignsInKeys + " places have been found in headings, where a sentence ends or a subsentence starts/ends");
        }
        if (abbrevisionsUsed > 0){
            out.println("All in all "+ abbrevisionsUsed +" abbreviations have been found. Make your language clearer and use full words only");
        }
        out.println(tips + end);
        out.println(report + end);

    }
}
