package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.IWord;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;

import java.util.HashSet;
import java.util.Set;

public class Word implements IWord {
    // Field to store the English spelling of the word
    private final String word;

    // Field to store unique pronunciations of the word
    private final Set<IPronunciation> pronunciations;

    /**
     * Constructor to initialize the word and an empty set of pronunciations.
     * @param word the English spelling of the word
     */
    public Word(String word) throws IllegalArgumentException {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be null or empty.");
        }
        this.word = word;
        this.pronunciations = new HashSet<>();
    }

    /**
     * Returns the standard English spelling of the word.
     * @return the word
     */
    @Override
    public String getWord() {
        return word;
    }

    /**
     * Adds a pronunciation to the word.
     * @param pronunciation the pronunciation to add
     * @throws IllegalArgumentException if the pronunciation is null
     */
    @Override
    public void addPronunciation(IPronunciation pronunciation) {
        if (pronunciation == null) {
            throw new IllegalArgumentException("Pronunciation cannot be null.");
        }
        // Try adding the word to the HashSet
        if (!pronunciations.add(pronunciation)) {
            // Log or print a message indicating that a duplicate was attempted
            System.out.println("Warning: Duplicate pronunciation not added.");
        }
    }

    /**
     * Returns the unique set of pronunciations of the word.
     * @return the pronunciations
     */
    @Override
    public Set<IPronunciation> getPronunciations() {
        return pronunciations;
    }
}
