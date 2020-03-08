package analysis;

import java.io.*;
import java.util.Scanner;

public class LetterOccurrence {
    private static int numOfBooks;
    private static String address;
    private static boolean hasV;
    private static boolean hasJ;

    private static int[] firstLetterOccurrence = new int[26];
    private static int[] acrosticOccurrence = new int[26];

    public static void main(String[] args) {
        formAddress();
        BufferedReader reader;
        BufferedWriter writer;
        try {
            for (int i = 1; i <= numOfBooks; i++) {
                reader = new BufferedReader(new FileReader(
                        address + "book" + i + ".txt"));

                System.out.println("Loading book " + i + "...");

                String s = reader.readLine();
                while (s != null) {
                    if (s.length() > 1) {
                        findLetter(s.toLowerCase());
                    }
                    s = reader.readLine();
                }
                reader.close();
                System.out.println("Finish loading book " + i + ".");
            }

            writer = new BufferedWriter(new FileWriter(
                    address + "firstLetterOccurrence.txt"));
            output(writer);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }

    private static void findLetter(String s) {
        // Get rid of the whitespaces before words in each line.
        int start = 0;
        while (!isFirstLetter(s, start)) {
            start++;
        }
        int ascii = (int) returnLetterHelper(s.charAt(start));
        firstLetterOccurrence[cvtAsciiToArrIndex(ascii)] += 1;
        acrosticOccurrence[cvtAsciiToArrIndex(ascii)] += 1;

        for (int i = start + 1; i < s.length(); i++) {
            if (!isFirstLetter(s, i - 1) && isFirstLetter(s, i)) {
                int ascii2 = (int) returnLetterHelper(s.charAt(i));
                acrosticOccurrence[cvtAsciiToArrIndex(ascii2)] += 1;
            }
        }
    }

    private static int cvtAsciiToArrIndex(int num) {
        return num - 97;
    }

    // Return whether the word is the first letter of a word.
    private static boolean isFirstLetter(String s, int num) {
        char c = s.charAt(num);
        return (c <= 122 && c >= 97);
    }

    private static char returnLetterHelper(char c) {
        if (c == 'v' && hasV) {
            return 'u';
        } else if (c == 'j' && hasJ) {
            return 'i';
        }
        return c;
    }

    private static void output(BufferedWriter writer) throws IOException {
        String begin = "The percentage of first-letter occurrence is:\n";
        writer.write(begin, 0, begin.length());
        printLetterOutput(writer, firstLetterOccurrence);
        writer.write("\n", 0, 1);
        String begin2 = "The percentage of acrostic-letter occurrence is:\n";
        writer.write(begin2, 0, begin2.length());
        printLetterOutput(writer, acrosticOccurrence);
        writer.write("\n", 0, 1);
    }

    private static void printLetterOutput(BufferedWriter writer, int[] arr)
            throws IOException {
        int total = sum(arr);
        for (int i = 0; i < 26; i++) {
            String temp = String.format("Letter \'%c\': %.3f\n", (char) (i + 65),
                    arr[i] / (float) total * 100);
            writer.write(temp, 0, temp.length());
        }
    }

    private static int sum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }

}
