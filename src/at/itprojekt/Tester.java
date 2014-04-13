package at.itprojekt;

import at.itprojekt.konjunktiv.Parser;
import at.itprojekt.zip.Zipper;

import java.io.PrintStream;

public class Tester extends Thread {
    private final PrintStream out;
    private final KeyValuepair whole;
    private final KeyValuepair[] single;
    private final Language language;
    private final static KeyValuepair[] kvpA = new KeyValuepair[0];

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
                single = new KeyValuepair[lines.length];
                for (int i = 0; i < lines.length; i++) {
                    String[] tmp = lines[i].split(";", 2);
                    stringBuilder.append(tmp[1]).append('\n');
                    single[i] = new KeyValuepair(tmp[0], tmp[1]);
                }
                whole = new KeyValuepair("Projekt", stringBuilder.toString());
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                single = new KeyValuepair[Ssingle.length];
                for (int i = 0; i < Ssingle.length; i++) {
                    String[] tmp = Ssingle[i].split(";", 2);
                    stringBuilder.append(tmp[1]).append('\n');
                    single[i] = new KeyValuepair(tmp[0], tmp[1]);
                }
                whole = new KeyValuepair("Projekt", stringBuilder.toString());
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
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
        final String start = " start", end = " end";
        //Analyse whole document
        out.println(whole.key + start);
        if (whole != null) {
            out.println("ALL start");
            out.println("# of konjunktives found:\t" + new Parser(whole.value, language).toString());
            zipped = new Zipper(whole.value).getSizeFactor();
            if (zipped > -1f)
                out.println("ZIP result:\t\t\t" + zipped);
            out.println(whole.key + end);
        }
        if (single != null)
            for (int lineNumber = 0; lineNumber < single.length; lineNumber++) {
                out.println(single[lineNumber].key + start);
                out.println("# of konjunktives found:\t\t\t" + new Parser(single[lineNumber].value, language).toString());
                zipped = new Zipper(single[lineNumber].value).getSizeFactor();
                if (zipped > -1f)
                    out.println("ZIP result:\t" + zipped);
                out.println(single[lineNumber].key + end);
            }
        out.println("Report end");

    }
}
