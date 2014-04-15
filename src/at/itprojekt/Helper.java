package at.itprojekt;

public class Helper {
    public static int countArray(Object[] a) {
        if (a == null)
            return -1;
        return a.length - 1;
    }

    public static String[] splitWords(String s) {
        if (s == null)
            return null;
        return s.split(RegEx.Space);
    }
}
