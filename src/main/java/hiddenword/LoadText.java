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
    private static int checkStyle = -1;

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
                int lineIndex = 0;
                while (s != null) {
                    if (s.length() > 1) {
                        String letter = findLetter(s);
                        if (letter != null) {
                            lineIndex++;
                            // Write the first-word sequence to the file in
                            // all lower cases.
                            if (checkStyle == 1 && lineIndex % 2 == 1) {
                                writer.write(letter.toLowerCase(), 0, 1);
                            } else if (checkStyle == 0 && lineIndex % 2 == 0) {
                                writer.write(letter.toLowerCase(), 0, 1);
                            } else if (checkStyle == -1) {
                                writer.write(letter.toLowerCase(), 0, 1);
                            }
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

    // return the check acrostic style; return 1 if we want to check the odd-number
    // lines (hexameter verse); return 0 if we want to check the even-number lines
    // (pentameter lines); return -1 if we normally check every line.
    static int getCheckStyle() {
        return checkStyle;
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
        System.out.println("Please make sure the books are named in the same format. " +
                "(\"book\" + number + \".txt\")");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("Does the text contains letter 'v' " +
                "('yes' or 'no'): ");
        hasV = (console.nextLine().toLowerCase().startsWith("y"));
        System.out.print("Does the text contains letter 'j' " +
                "('yes' or 'no'): ");
        hasJ = (console.nextLine().toLowerCase().startsWith("y"));
        System.out.print("Is the text written in Elegiac Couplet " +
                "('yes' or 'no'): ");
        boolean isEpic = (console.nextLine().toLowerCase().startsWith("y"));
        if (isEpic) {
            System.out.print("Check hexameter verse or check pentameter verse " +
                    "or normally check every line ('h' or 'p' or 'n'): ");
            String check = console.nextLine().toLowerCase();
            if (check.startsWith("h")) {
                checkStyle = 1;
            } else if (check.startsWith("p")) {
                checkStyle = 0;
            }
        }
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
