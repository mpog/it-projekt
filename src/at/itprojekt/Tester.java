package at.itprojekt;

import at.itprojekt.konjunktiv.Parser;
import at.itprojekt.zip.Zipper;

import java.io.PrintStream;

public class Tester extends Thread {
    private final PrintStream out;
    private final String whole;
    private final String[] single;
    private final Language language;

    public Tester(PrintStream out, String whole, String[] single, Language language) {
        if (language == null || out == null)
            throw new IllegalArgumentException(new NullPointerException("Parameter is NULL"));
        this.out = out;
        this.whole = whole;
        this.single = single;
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
        //Analyse whole document
        out.println("Report start");
        if (whole != null) {
            out.println("ALL start");
            out.println("# of konjunktives found:\t" + new Parser(whole, language).toString());
            zipped = new Zipper(whole).getSizeFactor();
            if (zipped > -1f)
                out.println("ZIP result:\t\t\t" + zipped);
            out.println("ALL end");
        }
        if (single != null)
            for (int lineNumber = 0; lineNumber < single.length; lineNumber++) {
                out.println(lineNumber + " start");
                out.println("# of konjunktives found:\t\t\t" + new Parser(single[lineNumber], language).toString());
                zipped = new Zipper(single[lineNumber]).getSizeFactor();
                if (zipped > -1f)
                    out.println("ZIP result:\t" + zipped);
                out.println(lineNumber + " end");
            }
        out.println("Report end");

    }
}
