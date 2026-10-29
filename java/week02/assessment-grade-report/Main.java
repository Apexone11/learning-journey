import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.IO.println;

public class Main {

    public static int parseScore(String text) throws InvalidScoreException {
        String trimmed = text.trim();
        int score;
        try {
            score = Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            throw new InvalidScoreException("Not a number: " + trimmed, e);
        }

        if (score < 0 || score > 100) {
            throw new InvalidScoreException("Out of range: " + score);
        }
        return score;
    }

    public static char letter(int score) {
        if (score >= 90) {
            return 'A';
        } else if (score >= 80) {
            return 'B';
        } else if (score >= 70) {
            return 'C';
        } else if (score >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    static void main() {
        ArrayList<String> saved = new ArrayList<>();
        int total = 0;

        try (Scanner scanner = new Scanner(new File("assessment-grade-report/students.csv"))) {
            int lineNumber = 1;
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // skip the header (line 1)
            }

            while (scanner.hasNextLine()) {
                lineNumber++;
                String[] parts = scanner.nextLine().split(",");

                if (parts.length < 2 || parts[0].isBlank()) {
                    continue;
                }

                try {
                    int score = parseScore(parts[1]);
                    saved.add(parts[0].trim() + "," + score + "," + letter(score));
                    total += score;
                } catch (InvalidScoreException e) {
                    println("Line " + lineNumber + " skipped: " + e.getMessage());
                }
            }

            try (PrintWriter writer = new PrintWriter("report.txt")) {
                for (String line : saved) {
                    writer.println(line);
                }
                double average = (double) total / saved.size();
                writer.println("Average: " + String.format("%.2f", average));
            }

            System.out.println(saved.size() + " students written to report.txt");
        } catch (FileNotFoundException e) {
            println("Could not find students.csv");
        } finally {
            println("Processing complete.");
        }
    }
}
