package at.itprojekt;

public class ParseException extends IllegalArgumentException {
    public ParseException() {
        this(null);
    }

    public ParseException(Throwable cause) {
        super("Could not parse the file" + ((cause == null) ? "" : "\n"+cause));
    }
}
