package hiddenword;

import java.io.*;
import java.util.Scanner;

public class LoadText {
    // Search the "LETTER_INDEX"-th letter of the line.
    private static final int LETTER_INDEX = 1;

    private static int numOfBooks;
    private static String address;
    private static boolean hasV;
    private static boolean hasJ;

    public static void main(String[] args) {
        formAddress();

        BufferedReader reader;
        BufferedWriter writer;
        try {
            for (int i = 1; i <= numOfBooks; i++) {
                reader = new BufferedReader(new FileReader(
                        address + "book" + i + ".txt"));
                writer = new BufferedWriter(new FileWriter(
                        address + i + ".txt"));

                System.out.println("Loading book " + i + "...");

                String s = reader.readLine();
                while (s != null) {
                    if (s.length() > 1) {
                        String letter = findLetter(s);
                        if (letter != null) {
                            // Write the sequence to the file in all lower cases.
                            writer.write(letter.toLowerCase(), 0, 1);
                        }
                    }
                    s = reader.readLine();
                }
                reader.close();
                writer.close();
                System.out.println("Finish loading book " + i + ".");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int getNumOfBooks() {
        return numOfBooks;
    }

    static String getAddress() {
        return address;
    }

    private static void formAddress() throws NumberFormatException {
        Scanner console = new Scanner(System.in);

        System.out.print("What is the Latin text for acrostic analysis " +
                "(first letter capitalized, no space): ");
        String bookname = console.nextLine();
        address = "src/com/latin/" + bookname + "/";
        System.out.print("How many books does it have " +
                "(enter an integer): ");
        numOfBooks = Integer.parseInt(console.nextLine());
        System.out.print("Does the text contains letter 'v' " +
                "('yes' or 'no'): ");
        hasV = (console.nextLine().equals("yes"));
        System.out.print("Does the text contains letter 'j' " +
                "('yes' or 'no'): ");
        hasJ = (console.nextLine().equals("yes"));
        System.out.println("Please make sure the books are named in the same format. " +
                "(\"book\" + number + \".txt\")");
    }

    private static String findLetter(String s) {
        // Get rid of the whitespaces before words in each line.
        int start = 0;
        while (s.charAt(start) == ' ') {
            start++;
        }

        int count = 1;
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ',' && c != ' ' && c != '.' && c != '?') {
                if (c != '!' && c != '-' && c != '\'' && c != '[' && c != ']') {
                    if (c != '\"') {
                        if (count == LETTER_INDEX) {
                            return returnLetterHelper(c);
                        } else {
                            count++;
                        }
                    }
                }
            }
        }
        System.out.println("We cannot find such great index " +
                "of a letter in a line");
        return null;
    }

    private static String returnLetterHelper(char c) {
        if (c == 'v' && hasV) {
            c = 'u';
        } else if (c == 'j' && hasJ) {
            c = 'i';
        }
        return "" + c;
    }
}
