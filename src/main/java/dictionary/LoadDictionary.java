package dictionary;

import java.io.*;

public class LoadDictionary {
    private static final int WORD_LENGTH = 20;

    public static void main(String[] args) {
        BufferedReader reader;
        BufferedWriter writer;
        try {

            reader = new BufferedReader(new FileReader(
                    "src/com/latin/lewis-short.txt"));
            writer = new BufferedWriter(new FileWriter(
                    "src/com/latin/dict.txt"));

            System.out.println("Reading Latin dictionary, Lewis and Short:");

            String s = reader.readLine();
            while (s != null) {
                if (s.length() > 1) {
                    s = truncate(s.substring(0, Math.min(s.length(), WORD_LENGTH)));
                    if (!s.endsWith(".")) {
                        writer.write(s, 0, s.length());
                    }
                    writer.newLine();
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // change a vowel in macron or breve to its standard form.
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
        // Get rid of the whitespaces before words in each line.
        int start = 0;
        while (s.charAt(start) == ' ') {
            start++;
        }
        String s1 = "";

        // Take the first word of each line as the standard form
        // from the dictionary.
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ',' || c == ' ') {
                break;
            }
            c = letterHelper(c);
            if (c != '-') {
                s1 += c;
            }
        }
        return s1.toLowerCase();
    }

    private static void printCharacters(char[] arr, int len) {
        for (int i = 0; i < len; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }
}
