package at.itprojekt.zip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class Zipper {
    private float zipFactorSmaller, origSize, newSize;

    /**
     * @param s A not-null string, which will be zipped and the difference in size is measured
     */
    public Zipper(String s) {
        if (s == null)
            throw new IllegalArgumentException(new NullPointerException("String s must not ber null"));
        try {
            origSize = s.length();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(baos);
            gzip.write(s.getBytes());
            gzip.close();
            String zipped = baos.toString();
            newSize = zipped.length() - 10; //10 seems to be the zip header
            zipFactorSmaller = 1f - newSize / origSize;
            if (zipFactorSmaller < 0)
                zipFactorSmaller = -1f;
        } catch (IOException e) {
            zipFactorSmaller = -1f;
        }
    }

    /**
     * @return Normed between 0 and 1. 0 --> Highly complicated text (or too short text)| 1 --> (Not reachable) Easy text. -1 --> Error or too short for zipping
     */
    public float getSizeFactor() {
        return zipFactorSmaller;
    }

    @Override
    public String toString() {
        return "Zipper{" +
                "zipFactorSmaller=" + zipFactorSmaller +
                ", origSize=" + origSize +
                ", newSize=" + newSize +
                '}';
    }
}
