package hiddenword;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class HiddenWord {

    public static void main(String[] args) {
        // LoadDictionary.main(args);
        LoadText.main(args);
        int numOfBooks = LoadText.getNumOfBooks();
        String address = LoadText.getAddress();

        BufferedReader reader;
        BufferedWriter writer;
        Map<String, List<String>> map;
        try {

            // The words in "dict.txt" are sorted.
            reader = new BufferedReader(new FileReader(
                    "src/com/latin/dict.txt"));
            map = new HashMap<>();

            // Store the dictionary words with word length >= 2 into the map.
            System.out.println("Loading Latin dictionary...");

            String s = reader.readLine();
            while (s != null) {
                if (s.length() > 1) {
                    if (!map.containsKey(s.substring(0, 2))) {
                        map.put(s.substring(0, 2), new ArrayList<String>());
                    }
                    map.get(s.substring(0, 2)).add(s);
                }
                s = reader.readLine();
            }
            reader.close();
            System.out.println("Finish loading Latin dictionary...");

            System.out.println("Start finding acrostic words...");
            BufferedReader reader2;
            for (int i = 1; i <= numOfBooks; i++) {
                // Import the vertical word sequence.
                reader2 = new BufferedReader(new FileReader(
                        address + i + ".txt"));
                String sequence = reader2.readLine();
                reader2.close();

                // Write the potential acrostic words into the output file.
                writer = new BufferedWriter(new FileWriter(
                        address + "book" + i + "_output.txt"));
                for (int j = 10; j > 2; j--) {
                    wordProcession(map, sequence, j, writer);
                }

                writer.close();
            }
            System.out.println("Output has been generated.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                        String output = String.format("Find word %s in form %s " +
                                        "from line %d to line %d.\n", subdict.get(isInDict),
                                seq.substring(i, endIndex), i + 1, endIndex);
                        writer.write(output, 0, output.length());
                    }
                }
            }
        }
    }

    private static int contains(List<String> subdict, String substr) {
        // Check whether the substr is long enough.
        boolean longLen = (substr.length() > 3);

        for (int i = 0; i < subdict.size(); i++) {
            String word = subdict.get(i).toLowerCase();
            word = word.replace('j', 'i');
            word = word.replace('v', 'u');
            if (!longLen && substr.equals(word)) {
                return i;
            } else if (longLen && word.length() >= substr.length() / 2 + 2
                    && word.length() <= substr.length()) {
                int sepIndex = substr.length() / 2 + 2;

                if (word.substring(0, sepIndex).equals(substr.substring(0, sepIndex))) {
                    return i;
                }
            }

        }
        return -1;
    }
}
