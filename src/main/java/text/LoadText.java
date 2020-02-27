package text;

import java.io.*;

public class LoadText {
    private static final int LETTER_INDEX = 1;

    public static void main(String[] args) {
        BufferedReader reader;
        BufferedWriter writer;
        try {
            reader = new BufferedReader(new FileReader(
                    "src/com/latin/Aeneid/book12.txt"));
            writer = new BufferedWriter(new FileWriter(
                    "src/com/latin/Aeneid/12.txt"));

            System.out.println("Reading Aeneidos:");

            String s = reader.readLine();
            while (s != null) {
                if (s.length() > 1) {
                    String letter = findLetter(s, LETTER_INDEX);
                    if (letter != null) {
                        writer.write(letter.toLowerCase(), 0, 1);
                    }
                }
                s = reader.readLine();
            }
            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String findLetter(String s, int index) {
        // Get rid of the whitespaces before words in each line.
        int start = 0;
        while (s.charAt(start) == ' ') {
            start++;
        }

        int count = 1;
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ',' && c != ' ' && c != '.' && c != '?' && c != '!'
                    && c != '-' && c != '\'' && c != '[' && c != ']') {
                if (count == index) {
                    return "" + c;
                } else {
                    count++;
                }
            }
        }
        System.out.println("We cannot find such great index " +
                "of a letter in a line");
        return null;
    }
}
