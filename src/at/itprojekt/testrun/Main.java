package at.itprojekt.testrun;

import at.itprojekt.Language;
import at.itprojekt.Tester;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        final String url = ClassLoader.getSystemClassLoader().getResource(".").getPath();
        final String filename = "test.txt";
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
            String whole = new String(stringBuilder.toString().getBytes("UTF-8"));
            System.out.println("Read:\n" + whole);
            if (!outF.exists())
                outF.createNewFile();
            output = new PrintStream(outF);
            // Do actual checking
            Tester tester = new Tester(output, whole, whole.split("\\r?\\n"), Language.De);
            tester.start();
            tester.join();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (output != null)
                output.close();
        }
    }
}
