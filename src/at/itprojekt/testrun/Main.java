package at.itprojekt.testrun;

import at.itprojekt.Language;
import at.itprojekt.RegEx;
import at.itprojekt.Tester;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Main {
    // Sample implementation of loading a file, parsing it, starting a tester and writing the result to a file.
    private static final String url = ClassLoader.getSystemClassLoader().getResource(".").getPath().substring(1);

    public static void main(String[] args) {
        System.out.println("In/Output @ " + url);
        Tester de = getTesterForFile("de.txt", new String[]{"WG", "PKW", "LKW", "MB", "PIN", "AG"}, Language.De);
        de.start();
        String[] glosarEN = readAllLines("glossar.en.txt");
        Tester en = getTesterForFile("en.txt", glosarEN, Language.En);
        try {
            de.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //   System.out.println("\n\nEN\n\n");
            en.start();
            try {
                en.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\t\tDone!");
    }

    private static Tester getTesterForFile(String filename, String[] glossar, Language language) {
        File inF = new File(url, filename);

        File outF = new File(url, "out." + filename.substring(0, filename.indexOf('.')) + ".xml");
        PrintStream output = null;
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(inF)));
            StringBuilder stringBuilder = new StringBuilder();
            String tmp = null;
            while ((tmp = input.readLine()) != null) {
                stringBuilder.append(tmp);
                stringBuilder.append('\n');
            }
            String whole = stringBuilder.toString();
            //  System.out.println(whole);
            if (!outF.exists())
                outF.createNewFile();
            output = new PrintStream(outF);
            // Do actual checking
            String[] lines = whole.split(RegEx.Newline);
            int[] tmpLevels = new int[lines.length - 1];
            String[] tmpHadings = new String[tmpLevels.length], tmpTexts = new String[tmpLevels.length];
            for (int i = 1; i < lines.length; i++) {
                String[] split = lines[i].split("\",\"");
                tmpLevels[i - 1] = Integer.parseInt(split[0].substring(1));
                tmpHadings[i - 1] = split[1];
                tmpTexts[i - 1] = split[2].substring(0, split[2].length() - 1); // Take the whole string, except of the last char
                // System.out.println(lines[i]+"\tis ok");
            }
            Tester tester = new Tester(output, tmpLevels, tmpHadings, tmpTexts, glossar, language, url + "rules.xml", url + "rules.xsd");
            return tester;
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String[] readAllLines(String filename) {
        try {
            List<String> lines = new LinkedList<>();
            File file = new File(url, filename);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null)
                lines.add(line);
            return lines.toArray(new String[lines.size()]);
        } catch (IOException e) {
            return null;
        }
    }
}
