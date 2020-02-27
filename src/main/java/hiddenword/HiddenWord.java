package hiddenword;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class HiddenWord {
    public static void main(String[] args) {
        BufferedReader reader;
        BufferedWriter writer;
        Map<String, List<String>> map;
        try {

            // the words in "dict.txt" are sorted.
            reader = new BufferedReader(new FileReader(
                    "src/com/latin/dict.txt"));
            writer = new BufferedWriter(new FileWriter(
                    "src/com/latin/Aeneid/book1_output.txt"));
            map = new HashMap<>();

            System.out.println("Reading Latin dictionary, Lewis and Short:");

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

            // Import the vertical word sequence.
            BufferedReader reader2 = new BufferedReader(new FileReader(
                    "src/com/latin/Aeneid/1.txt"));
            String sequence = reader2.readLine();
            reader2.close();

            for (int i = 10; i > 2; i--) {
                wordProcession(map, sequence, i, writer);
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void wordProcession(Map<String, List<String>> map, String seq, int size,
                                       BufferedWriter writer) throws IOException{
        for (int i = 0; i < seq.length() - 1; i++) {
            // Check whether the first two letters are in the keys of maps;
            // if not we change to the next index.
            if (map.containsKey(seq.substring(i, i + 2))) {
                List<String> subdict = map.get(seq.substring(i, i + 2));

                findWord(subdict, seq, i, size, writer);
            }
        }
    }

    // Find a word in the dictionary that starts from the given 'index'
    // and the word has 'size' letters.
    private static void findWord(List<String> subdict, String seq, int index, int size,
                                 BufferedWriter writer) throws IOException {
        int endIndex = index + size;
        if (endIndex < seq.length()) {
            boolean isInDict = subdict.contains(seq.substring(index, endIndex));
            if (isInDict) {
                // data structure starts from index 0, but line starts from index 1.
                String output = String.format("Find word %s from line %d to line %d.\n",
                        seq.substring(index, endIndex), index + 1, endIndex);
                writer.write(output, 0, output.length());
            }
        }
    }
}
