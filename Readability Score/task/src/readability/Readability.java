package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Readability {

    int wordCount;
    int sentenceCount;
    int characterCount;
    String[] words;
    int syllables;
    int polySyllables;
    Scanner scanner;
    String text;

    public Readability(String filename, Scanner scanner) throws IOException {
        this.scanner = scanner;
        text = new String(Files.readAllBytes(Paths.get(filename)));
        wordCount = text.split("\\s+").length;
        sentenceCount = text.split("[.?!]").length;
        characterCount = text.replaceAll("\\s+", "").length();
        words = text.replaceAll("e[,.?\\s]{1}", " ").toLowerCase().split("\\s+");
        syllables = syllableCount(words);
        polySyllables = polysyllableCount(words);


    }

    private int syllableCount(String[] words) {
        int syllableCount = 0;
        for (String word : words) {
            syllableCount += countWordSyllables(word);
        }
        return syllableCount;
    }

    int polysyllableCount(String[] words) {
        int polySyllableCount = 0;
        for (String word : words) {
            if (countWordSyllables(word) > 2) {
                polySyllableCount++;
            }
        }
        return polySyllableCount;
    }

    private  int countWordSyllables(String word) {
        char[] letters = word.toCharArray();
        boolean previousIsConsonant= true;
        int count = 0;
        for (char letter : letters) {

            if (isVowel(letter) && previousIsConsonant) {
                ++count;
                previousIsConsonant = false;
            } else {
                previousIsConsonant = true;
            }

        }


        return count;
    }

    private boolean isVowel(char character) {
        return character == 'a'
                || character == 'e'
                || character == 'i'
                || character == 'o'
                || character == 'u'
                || character == 'y';
    }

    public void start() {
        System.out.println(countWordSyllables("is"));
        System.out.printf("The text is:%n%s%n%n" +
                "Words: %d%n" +
                "Sentences: %d%n" +
                "Characters: %d%n" +
                "Syllables: %d%n" +
                "Polysyllables: %d%n" +
               "Enter the score you want to calculate (ARI, FK, SMOG, CL, all): " , text, wordCount, sentenceCount, characterCount,syllables, polySyllables );
       String input = scanner.nextLine();
        System.out.println();
        menu(input);
    }

    private void menu(String option) {
        switch (option) {
            case "ARI":
                System.out.printf("Automated Readability Index: %.2f (about " + getAge(getARI()) + " year olds).%n", getARI());
                break;
            case "FK":
                System.out.printf("Flesch–Kincaid readability tests: %.2f (about " + getAge(getFK()) + " year olds).%n", getFK());
                break;
            case "SMOG":
                System.out.printf("Simple Measure of Gobbledygook: %.2f (about " + getAge(getSMOG()) + " year olds).%n", getSMOG());
                break;
            case "CL":
                System.out.printf("Coleman–Liau: %.2f (about" + getAge(getCL()) + " year olds).%n", getCL());
                break;
            case "all":
                System.out.printf("Automated Readability Index: %.2f (about " + getAge(getARI()) + " year olds).%n", getARI());
                System.out.printf("Flesch–Kincaid readability tests: %.2f (about " + getAge(getFK()) + " year olds).%n", getFK());
                System.out.printf("Simple Measure of Gobbledygook: %.2f (about " + getAge(getSMOG()) + " year olds).%n", getSMOG());
                System.out.printf("Coleman–Liau: %.2f (about " + getAge(getCL()) + " year olds).%n", getCL());
                break;
        }
        double average = (getAge(getARI()) + getAge(getFK()) + getAge(getSMOG()) + getAge(getCL())) / 4.0;
        System.out.printf("%nThis text should be understood in average by %.2f year olds.", average);
    }

    private double getARI() {
        return 4.71 * characterCount / wordCount + 0.5 * wordCount / sentenceCount - 21.43;
    }

    private double getFK() {
        return 0.39 * wordCount / sentenceCount + 11.8 * syllables / wordCount - 15.59;
    }

    private double getSMOG() {
        double v = 1.043 * Math.sqrt(polySyllables * (30 / sentenceCount)) + 3.1291;
        return v;
    }

    private double getCL() {
        double L = 100.0 * characterCount/wordCount;
        double S = 100.0 * sentenceCount/wordCount;
        return 0.0588 * L - 0.296 * S - 15.8;
    }

    private int getAge(double index) {
        int[] age = {6, 7, 9, 10, 11, 12, 13, 14,
                15, 16, 17, 18, 24, 24};

        int roundedScore = (int) Math.ceil(index);
      if (roundedScore > 14) {
            roundedScore = 14;
      }
        return age[roundedScore - 1];
    }
}