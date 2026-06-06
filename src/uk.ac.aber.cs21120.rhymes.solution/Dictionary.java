package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Dictionary implements IDictionary {
    // Map to store words by their English spelling
    private final Map<String, IWord> words;

    // Counter for pronunciations
    private int pronunciationCount;

    public Dictionary() {
        this.words = new HashMap<>();
        this.pronunciationCount = 0;
    }

    /**
     * Returns the word with the given spelling.
     * @param word the spelling of the word
     * @return the word or null if it is not in the dictionary
     */
    @Override
    public IWord getWord(String word) {
        return words.get(word);
    }

    /**
     * Adds a word to the dictionary. If the word already exists,
     * IllegalArgumentException is thrown.
     * @param word the word to add
     * @throws IllegalArgumentException if the word already exists
     */
    @Override
    public void addWord(IWord word) {
        if (words.containsKey(word.getWord())) {
            throw new IllegalArgumentException("Word already exists in the dictionary.");
        }
        words.put(word.getWord(), word);

        // Update pronunciation count by the number of pronunciations in this word
        pronunciationCount += word.getPronunciations().size();
    }

    // Increment pronunciation count
    public void incrementPronunciationCount() {
        pronunciationCount++;
    }



    /**
     * Returns the number of words in the dictionary.
     * @return the number of words
     */
    @Override
    public int getWordCount() {
        return words.size();
    }

    /**
     * Returns the number of pronunciations in the dictionary.
     * @return the number of pronunciations
     */
    @Override
    public int getPronunciationCount() {
        return pronunciationCount;
    }

    /**
     * Parse a line from the CMU Pronouncing Dictionary.
     */
    @Override
    public void parseDictionaryLine(String line) {
        // Remove comments
        int commentIndex = line.indexOf("#"); // Find index where comment begins
        // If comment found, remove it (assuming comments are in the end of the line)
        if (commentIndex != -1) {
            line = line.substring(0, commentIndex).trim();
        }
        // Check for reaching the end of the file
        if (line.isEmpty()) return;

        // Split the line by whitespace
        String[] parts = line.split("\\s+");

        // Extract the base word, removing any "(n)" suffix, where n - is a stress value
        String rawWord = parts[0];
        String word = rawWord.replaceAll("\\(\\d+\\)", "");  // Removes "(1)", "(2)", etc.

        // Retrieve or create the Word object
        IWord wordObj = words.get(word);
        if (wordObj == null) {
            wordObj = new Word(word);
            words.put(word, wordObj);
        }

        // Create a new Pronunciation object
        IPronunciation pronunciation = new Pronunciation();

        // Populate the Pronunciation object with phonemes
        for (int i = 1; i < parts.length; i++) {
            String phonemeStr = parts[i];
            String arpabet;
            int stress = -1;

            // Check if last character is a digit (indicating stress)
            if (Character.isDigit(phonemeStr.charAt(phonemeStr.length() - 1))) {
                arpabet = phonemeStr.substring(0, phonemeStr.length() - 1);
                stress = Character.getNumericValue(phonemeStr.charAt(phonemeStr.length() - 1));
            } else {
                arpabet = phonemeStr; // No stress, treat as consonant or unstressed vowel
            }

            // Convert to Arpabet enum and create Phoneme object
            Arpabet arpabetEnum = Arpabet.valueOf(arpabet);
            IPhoneme phoneme = new Phoneme(arpabetEnum, stress);
            pronunciation.add(phoneme);
        }

        // Add the pronunciation to the word and update the pronunciation count
        wordObj.addPronunciation(pronunciation);
        //increment number of pronunciations in the dictionary
        pronunciationCount++;
    }




    /**
     * Load the CMU Pronouncing Dictionary from a file.
     */
    @Override
    public void loadDictionary(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseDictionaryLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all the rhymes for all pronunciations of a word (task 5)
     * Should throw an IllegalArgumentException if the word is not in the dictionary,
     * or is null.
     * @param word the word to get rhymes for
     * @return a set of words that rhyme with the given word
     */
    @Override
    public Set<String> getRhymes(String word) {
        // Check if word is found
        if (word == null || !words.containsKey(word)) {
            throw new IllegalArgumentException("Word not found in the dictionary.");
        }

        Set<String> rhymes = new HashSet<>();

        // Get the chosen word's pronunciations
        IWord chosenWord = words.get(word);
        Set<IPronunciation> chosenPronunciations = chosenWord.getPronunciations();

        // Add the word to the rhyme set
        rhymes.add(word);

        // Compare with other words in the dictionary
        for (Map.Entry<String, IWord> entry : words.entrySet()) {
            IWord otherWord = entry.getValue();
            if (otherWord.getWord().equals(word)) {
                continue;  // Skip the word itself
            }

            // Check if any pronunciation of the other word rhymes with the chosen word
            for (IPronunciation chosenPronunciation : chosenPronunciations) {
                for (IPronunciation otherPronunciation : otherWord.getPronunciations()) {
                    if (chosenPronunciation.rhymesWith(otherPronunciation)) {
                        rhymes.add(otherWord.getWord());  // Add rhyming word to the set
                        break;
                    }
                }
            }
        }

        return rhymes;
    }
}
