package uk.ac.aber.cs21120.rhymes.tests;
import uk.ac.aber.cs21120.rhymes.interfaces.*;
import uk.ac.aber.cs21120.rhymes.solution.Dictionary;
import uk.ac.aber.cs21120.rhymes.solution.Phoneme;
import uk.ac.aber.cs21120.rhymes.solution.Pronunciation;
import uk.ac.aber.cs21120.rhymes.solution.Word;

import java.util.Scanner;
import java.util.Set;

public class RhymeDictionaryCLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IDictionary dictionary = new Dictionary();

        System.out.println("Welcome to the Rhyme Dictionary CLI!");
        System.out.println("Commands:");
        System.out.println("1. addword - Add a new word and its pronunciation");
        System.out.println("2. findrhyme - Find rhymes for a word in the dictionary");
        System.out.println("3. loadfile - Load a CMU dictionary file");
        System.out.println("4. exit - Exit the CLI");
        System.out.println();

        boolean running = true;
        while (running) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "addword":
                    addWord(dictionary, scanner);
                    break;
                case "findrhyme":
                    findRhymes(dictionary, scanner);
                    break;
                case "loadfile":
                    loadDictionary(dictionary, scanner);
                    break;
                case "exit":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid command. Try again.");
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private static void addWord(IDictionary dictionary, Scanner scanner) {
        System.out.print("Enter the word: ");
        String wordText = scanner.nextLine().trim();

        IWord word = new Word(wordText);

        System.out.println("Enter the pronunciation phonemes for the word separated by spaces, no quotes (e.g., 'K AE T'): ");
        String pronunciationLine = scanner.nextLine().trim();
        IPronunciation pronunciation = new Pronunciation();

        String[] phonemes = pronunciationLine.split("\\s+");
        for (String phonemeStr : phonemes) {
            String arpabet;
            int stress = -1;

            // Check if last character is a digit (indicating stress)
            if (Character.isDigit(phonemeStr.charAt(phonemeStr.length() - 1))) {
                arpabet = phonemeStr.substring(0, phonemeStr.length() - 1);
                stress = Character.getNumericValue(phonemeStr.charAt(phonemeStr.length() - 1));
            } else {
                arpabet = phonemeStr;  // No stress, treat as consonant or unstressed vowel
            }

            // Create Phoneme object and add it to the pronunciation
            Arpabet arpabetEnum = Arpabet.valueOf(arpabet);
            IPhoneme phoneme = new Phoneme(arpabetEnum, stress);
            pronunciation.add(phoneme);
        }

        word.addPronunciation(pronunciation);

        try {
            dictionary.addWord(word);
            System.out.println("Word added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void findRhymes(IDictionary dictionary, Scanner scanner) {
        System.out.print("Enter the word to find rhymes for: ");
        String word = scanner.nextLine().trim();

        try {
            Set<String> rhymes = dictionary.getRhymes(word);
            System.out.println("Words that rhyme with '" + word + "': " + rhymes);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void loadDictionary(IDictionary dictionary, Scanner scanner) {
        System.out.print("Enter the filename of the CMU dictionary, no quotes (e.g., 'cmudict.dict' ): ");
        String filename = scanner.nextLine().trim();

        try {
            dictionary.loadDictionary(filename);
            System.out.println("Dictionary loaded successfully!");
            System.out.println("Word count: " + dictionary.getWordCount());
            System.out.println("Pronunciation count: " + dictionary.getPronunciationCount());
        } catch (Exception e) {
            System.out.println("Error loading dictionary: " + e.getMessage());
        }
    }
}
