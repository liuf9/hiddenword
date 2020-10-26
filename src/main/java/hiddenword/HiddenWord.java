package hiddenword;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Main function to find the candidates of hidden acrostics.
public class HiddenWord {
    // We suppose the least length of an acrostic is 4.
    private static final int LEAST_LEN = 4;

    public static void main(String[] args) {
        // LoadDictionary.main(args);
        LoadText.main(args);
        int numOfBooks = LoadText.getNumOfBooks();
        String dict = LoadText.getDict();
        String output = LoadText.getOutput();

        BufferedReader reader;
        BufferedWriter writer;
        Map<String, List<String>> map;
        try {

            // The words in "dict.txt" are sorted.
            reader = new BufferedReader(new FileReader(
                    dict + "dict.txt"));
            map = new HashMap<>();

            // Store the dictionary words with word length >= 2 into the map
            // where we use the first two letters of the word as a key.
            System.out.println("Loading Latin dictionary...");

            String s = reader.readLine();
            while (s != null) {
                if (s.length() > 1) {
                    String firstTwoLetters = s.substring(0, 2).toLowerCase();
                    if (!map.containsKey(firstTwoLetters)) {
                        map.put(firstTwoLetters, new ArrayList<String>());
                    }
                    map.get(firstTwoLetters).add(s);
                }
                s = reader.readLine();
            }
            reader.close();
            System.out.println("Finish loading Latin dictionary...");

            // Search potential candidates for acrostics from a text string sequence.
            System.out.println("Start finding acrostic words...");
            BufferedReader reader2;
            for (int i = 1; i <= numOfBooks; i++) {
                // Import the vertical word sequence.
                reader2 = new BufferedReader(new FileReader(
                        output + i + ".txt"));
                // a string sequence that contains all first letters of the text lines
                String sequence = reader2.readLine();
                reader2.close();

                // Create an output file buffer for writing the potential acrostic words.
                writer = new BufferedWriter(new FileWriter(
                        output + "book" + i + "_output.txt"));

                // in English version, replace 10 with 15.
                // Our assumption is that the English acrostic has a length
                // at most 15.
                for (int j = 10; j >= LEAST_LEN; j--) {
                    wordProcession(map, sequence, j, writer);
                }

                writer.close();
            }
            System.out.println("Output has been generated.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This function searches through a given "map" of words and finds a qualified candidate word
    // of acrostics with length "len" corresponding to the substring of the given text string "seq"
    // and writes into the given buffer "writer".
    private static void wordProcession(Map<String, List<String>> map, String seq,
                                       int len, BufferedWriter writer) throws IOException {
        for (int i = 0; i < seq.length() - 1; i++) {
            // Check whether the first two letters are in the keys of maps;
            // if not we change to the next index.
            if (map.containsKey(seq.substring(i, i + 2))) {
                List<String> subdict = map.get(seq.substring(i, i + 2));

                int endIndex = i + len;
                if (endIndex < seq.length()) {
                    int isInDict = contains(subdict, seq.substring(i, endIndex));
                    if (isInDict != -1) {
                        // data structure starts from index 0, but line starts
                        // from index 1.
                        int startIndex = actualLineNumber(i + 1);
                        int finalIndex = actualLineNumber(endIndex);
                        String output = String.format("Find word \"%s\" in form \"%s\" " +
                                        "from line %d to line %d.\n", subdict.get(isInDict),
                                seq.substring(i, endIndex), startIndex, finalIndex);
                        writer.write(output, 0, output.length());
                    }
                }
            }
        }
    }

    // This function returns the actual line number in the poem when we have different
    // check acrostic style (e.g., we search acrostics by lines or by alternating lines).
    private static int actualLineNumber(int n) {
        int checkStyle = LoadText.getCheckStyle();
        if (checkStyle == 1) {
            return 2 * n - 1;
        } else if (checkStyle == 0) {
            return 2 * n;
        } else {
            return n;
        }
    }

    // This function searches through a given "subdict" and returns the index of a word
    // that is closest to the given string "substr" (identical for more than first half
    // of the letters and different in the last few letters by inflection).
    private static int contains(List<String> subdict, String substr) {
        for (int i = 0; i < subdict.size(); i++) {
            String word = subdict.get(i).toLowerCase();
            word = word.substring(0, word.indexOf(' '));
            word = word.replace('v', 'u');
            word = word.replace('j', 'i');

            // Latin version:
            // use "substr.length() / 2 + <1, 2>"
            if (word.length() >= substr.length() / 2 + 2
                    && word.length() <= substr.length() + 1) {
                int sepIndex = substr.length() / 2 + 2;

                if (word.substring(0, sepIndex).equals(substr.substring(0, sepIndex))) {
                    return i;
                }
            }

            // // English Version
            // if (word.equals(substr)) {
            //     return i;
            // }

        }
        return -1;
    }
}
