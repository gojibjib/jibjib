package de.cdvost.jibjib.repository.converter;

public class WikiTextCleaner {

    public static String cleanData(String wikiText){
        String cleanedText = wikiText;
        cleanedText = cleanedText.replaceAll("(==)([^=]*)(==)", "--$2--");
        cleanedText = cleanedText.replace("\\n", System.getProperty("line.separator"));
        return cleanedText;
    }
}
