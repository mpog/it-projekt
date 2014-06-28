package at.itprojekt;

import at.itprojekt.konjunktiv.KonjParser;
import at.itprojekt.statbuddy.StatParser;
import at.itprojekt.xml.generated.Report;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.OutputStream;

public class Tester extends Thread {
    private final OutputStream out;
    private final DataPair whole;
    private final DataPair[] single;
    private final Language language;
    private final static DataPair[] kvpA = new DataPair[0];
    private final String[] glossar;

    /**
     * @param out      OutputStream used to print the results in
     * @param levels   All levels of the project as an array
     * @param headings All headings of the project as an array
     * @param texts    All full-texts of the project as an array
     * @param language Language of the project
     */
    public Tester(OutputStream out, int[] levels, String[] headings, String[] texts, Language language) {
        this(out, levels, headings, texts, null, language);
    }

    /**
     * @param out      OutputStream used to print the results in
     * @param levels   All levels of the project as an array
     * @param headings All headings of the project as an array
     * @param texts    All full-texts of the project as an array
     * @param glossar  A list of words, which are ok
     * @param language Language of the project
     */
    public Tester(OutputStream out, int[] levels, String[] headings, String[] texts, String[] glossar, Language language) {
        //region Check for valid input
        assert out != null && levels != null && headings != null && texts != null && language != null : "Parameter must not be null.";
        assert levels.length == headings.length && headings.length == texts.length : "Length of all arrays has to be the same";
        //endregion
        single = new DataPair[levels.length];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < levels.length; i++) {
            single[i] = new DataPair(levels[i], headings[i], texts[i]);
            stringBuilder.append(texts[i]);
            stringBuilder.append(headings[i]);
        }

        whole = new DataPair(0, "Whole project", stringBuilder.toString());
        this.out = out;
        this.language = language;
        this.glossar = glossar;

    }

    /**
     * Prints test-output to System.out
     */
    @Override
    public void run() {
        int maxWords1SentenceText = 20, maxWords1SentenceHeadings = 8;
        int tooLongSentences = 0;
        float zipped;
        final String abbreviation = "abbreviation";

        //region Analyse whole document
        Report rep = new Report();
        Report.Project pro = new Report.Project();
        Report.Project.Result res = new Report.Project.Result();

        pro.setResult(new StatParser(whole.value, language, whole, null).getResult());
        //endregion

        //Preparations for analysing a single document
        int sentenceSignsInKeys = 0, abbreviationsUsed = 0, valueEndSignMissing = 0, headingLongerEqualThenText = 0;

        //region Add each line to the report
        for (int lineNumber = 0; lineNumber < single.length; lineNumber++) {
            Report.Line line = new Report.Line();
            Report.Line.Result.Value val = new Report.Line.Result.Value();
            Report.Line.Result.Key key = new Report.Line.Result.Key();
            val.setSubjunctives(new KonjParser(single[lineNumber].value, language).getKonjunktive());
            line.setNr(lineNumber);
            // Make the statistics
            StatParser statParserKey = new StatParser(single[lineNumber].key, language, glossar), statParserValue = new StatParser(single[lineNumber].value, language, glossar);
            key = new StatParser(single[lineNumber].key, language, null).getKey();
            val = new StatParser(single[lineNumber].value, language, null).getValue();
            // If the key has a sentence separator, report it
            if (statParserKey.allSentenceSeperators > 0) {
                sentenceSignsInKeys++;
                //  System.out.println(single[lineNumber].key + " has a sentence sign in it");
            }
            //Log to long sentences
            if (statParserKey.longestSentenceNumberOfWords >= maxWords1SentenceText) {
                tooLongSentences++;
                //  System.out.println(single[lineNumber].key + " is too long. # of words: " + statParserKey.longestSentenceNumberOfWords);
            }
            if (statParserValue.longestSentenceNumberOfWords >= maxWords1SentenceHeadings) {
                tooLongSentences++;
                //  System.out.println(single[lineNumber].value + " is too long. # of words: " + statParserValue.longestSentenceNumberOfWords);
            }
            // Log the # of abbreviations
            abbreviationsUsed += statParserValue.abbreviations;
            abbreviationsUsed += statParserKey.abbreviations;
           /* if (statParserKey.abbreviations > 0)
                System.out.println(single[lineNumber].key + " uses " + statParserKey.abbreviations + " " + abbreviation + "(s)");
            if (statParserValue.abbreviations > 0)
                System.out.println(single[lineNumber].value + " uses " + statParserValue.abbreviations + " " + abbreviation + "(s)");*/
            //Log the # of missing end sentence signs
            if (!statParserValue.sentenceSignAtEnd) {
                valueEndSignMissing++;
                // System.out.println(single[lineNumber].value + " lacks an end sentence sign");
            }
            // Check if the heading is shorter then the text
            if (single[lineNumber].key.length() >= single[lineNumber].value.length()) {
                headingLongerEqualThenText++;
                //  System.out.println("Line number " + (lineNumber + 2) + "(" + single[lineNumber].key + ") has a shorter heading then text");
            }
            //out.println("Text analyser's result: Key:" + statParserKey + " Value:" + statParserValue);
            Report.Line.Result linRes = new Report.Line.Result();
            linRes.setKey(key);
            linRes.setValue(val);
            line.setResult(linRes);
            rep.getLine().add(line);

        }
        //endregion
        // Print the tips section
        //region Add tips TODO DEPRICATE
        Report.Project.Tips tip = new Report.Project.Tips();
        if (sentenceSignsInKeys > 0) {
            tip.getTip().add("All in all " + sentenceSignsInKeys + " places have been found in headings, where a sentence ends or a subsentence starts/ends.");
        }
        if (abbreviationsUsed > 0) {
            tip.getTip().add("All in all " + abbreviationsUsed + " abbreviations have been found. Make your language clearer and use full words only.");
        }
        if (valueEndSignMissing > 0) {
            tip.getTip().add("All in all " + valueEndSignMissing + " sentence signs are missing at the end of texts.");
        }
        if (headingLongerEqualThenText > 0) {
            tip.getTip().add("All in all " + headingLongerEqualThenText + " headings have maximum the same length compard to the corresponding text.");
        }
        if (tooLongSentences > 0) {
            tip.getTip().add("All in all " + tooLongSentences + " entries have at least one too long sentence.");
        }
        //endregion
        pro.setTips(tip);
        rep.setProject(pro);


        //region Export XML
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Report.class);
            javax.xml.bind.Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(
                    javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            jaxbMarshaller.marshal(rep, out);
        } catch (JAXBException ex) {
            System.err.println("Could not create XML file. Reason: " + ex.getMessage());
        }
        //endregion
    }
}
