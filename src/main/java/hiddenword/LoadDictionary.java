package hiddenword;

import java.io.*;

// We load Latin or other dictionaries and transcribe them into our preferred form.
public class LoadDictionary {
    private static final int WORD_LENGTH = 30;

    public static void main(String[] args) {
        BufferedReader reader;
        BufferedWriter writer;
        try {

            reader = new BufferedReader(new FileReader(
                    "src/com/latin/lewis-short.txt"));
            writer = new BufferedWriter(new FileWriter(
                    "src/com/latin/dict.txt"));

            System.out.println("Reading Latin dictionary, Lewis and Short...");

            String s = reader.readLine();

            // Take of the standard word form by extract the first word in
            // each dictionary entry until EOF.
            // OUTPUT: word + " (" + (explanation length (letters) of the word + ")"
            while (s != null) {
                int len = s.length();
                // Get rid of the whitespaces before words in each line.
                int start = 0;
                while (start < len && s.charAt(start) == ' ') {
                    start++;
                }
                s = s.substring(start);

                if (s.length() > 1) {
                    s = truncate(s.substring(0, Math.min(s.length(), WORD_LENGTH)));
                    // Test whether String s is not an abbreviation of a word and
                    // the word length is long enough to have representations.
                    if (!s.endsWith(".") && s.length() > 1) {
                        s = s + " (" + len + ")";
                        writer.write(s, 0, s.length());
                        writer.newLine();
                    }
                }
                s = reader.readLine();
            }

            // char[] buffer = new char[20];
            // int len;
            // while ((len = reader.read(buffer, 0, 20)) != -1) {
            //     System.out.println(len);
            //     printCharacters(buffer, len);
            // }
            reader.close();
            writer.close();
            System.out.println("Finish reading dictionary.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Change a vowel in macron or breve to its standard form.
    private static char letterHelper(char c) {
        if (c == 'ă' || c == 'ā' || c == 'Ā' || c == 'Ă') {
            return 'a';
        } else if (c == 'ĕ' || c == 'ē' || c == 'Ē' || c == 'Ĕ') {
            return 'e';
        } else if (c == 'ĭ' || c == 'ī' || c == 'Ī' || c == 'Ĭ') {
            return 'i';
        } else if (c == 'ŏ' || c == 'ō' || c == 'Ō' || c == 'Ŏ') {
            return 'o';
        } else if (c == 'ŭ' || c == 'ū' || c == 'Ū' || c == 'Ŭ') {
            return 'u';
        } else if (c == 'ў' || c == 'ȳ' || c == 'Ȳ' || c == 'Ῠ') {
            return 'y';
        } else {
            return c;
        }
    }

    // Grasp the first word of each dictionary entry.
    private static String truncate(String s) {
        // Check whether the word is a prefix.
        if (s.charAt(0) == '-' || s.charAt(0) == '†') {
            return "";
        }
        String s1 = "";

        // Take the first word of each line as the standard form
        // from the dictionary.
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ',' || c == ' ' || c == '!' || c == ':') {
                break;
            }
            c = letterHelper(c);
            // Delete the connecting dash between prefix and word.
            if (c != '-') {
                s1 += c;
            }
        }

        // Check whether the word is a suffix.
        if (s1.endsWith("-")) {
            return "";
        } else {
            return s1;
        }
    }

    // Print the first word of each dictionary entry if needed.
    private static void printCharacters(char[] arr, int len) {
        for (int i = 0; i < len; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }
}
