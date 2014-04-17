package at.itprojekt.testrun;

import at.itprojekt.Language;
import at.itprojekt.RegEx;
import at.itprojekt.Tester;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        final String url = ClassLoader.getSystemClassLoader().getResource(".").getPath().substring(1);
        final String filename = "in.txt";
        File inF = new File(url, filename);
        File outF = new File(url, "out.txt");
        System.out.println("In/Output @ " + url);
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
            //System.out.println(whole);
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
                tmpTexts[i - 1] = split[2].substring(0, split[2].length());
            }
            //System.out.println(tmpLevels[0] + "\t" + tmpHadings[0] + '\t' + tmpTexts[0]);
            Tester tester = new Tester(output, tmpLevels, tmpHadings, tmpTexts, Language.De);
            tester.start();
            tester.join();
        } catch (IOException | InterruptedException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if (output != null)
                output.close();
        }
    }
}
