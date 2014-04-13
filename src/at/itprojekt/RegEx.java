package at.itprojekt;

public class RegEx {
    public static final String GermanVerbEnds = "e((s?t)|n)?";
    public static final String GermanModalVerbs = "((dürft)|(könnt)|(möcht)|(müsst)|((s|w)ollt))";
    public static final String GermanKonjStrongBasic = "((wär)|(hätt)|(würd)|(gäb))";
    public static final String EnglishKonjAsVagueness = "(((((sh)|(c)|(w))ould)|('d)))";
    public static final String Newline = "((\\r|\\n)?|(\\r\\n))";
}
