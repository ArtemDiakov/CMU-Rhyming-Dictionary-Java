package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;

import java.util.List;
import java.util.ArrayList;

public class Pronunciation implements IPronunciation {
    // List to store the phonemes for single pronunciation
    private final List<IPhoneme> phonemes;

    // Constructor creates the phonemes list
    public Pronunciation() {
        this.phonemes = new ArrayList<>();
    }

    /**
     * Adds a phoneme to the end of the pronunciation.
     * Throws IllegalArgumentException if the phoneme is null.
     * @param phoneme the phoneme to add
     */
    @Override
    public void add(IPhoneme phoneme) {
        if (phoneme == null) {
            throw new IllegalArgumentException("Phoneme cannot be null.");
        }
        phonemes.add(phoneme);
    }

    /**
     * Returns the list of phonemes in this pronunciation.
     * @return the list of phonemes
     */
    @Override
    public List<IPhoneme> getPhonemes() {
        return phonemes;
    }

    /**
     * Finds the index of the final stressed vowel in the pronunciation.
     * If no stressed vowel, returns the index of the last vowel or -1 if no vowels exist.
     * @return the index of the final stressed vowel, or -1 if no vowels exist
     */
    @Override
    public int findFinalStressedVowelIndex() {
        // Variables to store the index of the last primary stressed vowel and the last secondary stressed vowel, respectively
        int lastVowelIndex = -1;
        int lastSecondaryVowelIndex = -1;

        // Iterate through the phonemes in reverse to find the last stressed vowel
        for (int i = phonemes.size() - 1; i >= 0; i--) {
            IPhoneme phoneme = phonemes.get(i);
            // If current phoneme is vowel proceed else move to the next phoneme
            if (phoneme.getArpabet().isVowel()) {
                if (phoneme.getStress() == 1) {
                    return i; // Return the index of the last primary stressed vowel
                }
                if (phoneme.getStress() == 2 && lastSecondaryVowelIndex == -1) {
                    lastSecondaryVowelIndex = i; // Mark the last secondary stressed vowel
                }
                if (lastVowelIndex == -1) {
                    lastVowelIndex = i; // Mark the last vowel if no stressed vowels are found
                }
            }
        }

        // If no primary stressed vowel, return the last secondary stressed vowel or the last vowel
        return lastSecondaryVowelIndex != -1 ? lastSecondaryVowelIndex : lastVowelIndex;
    }

    /**
     * Stub for rhyming logic, to be implemented later.
     * Returns false for now.
     * @param other the other pronunciation to compare with
     * @return false as a placeholder
     */
    @Override
    public boolean rhymesWith(IPronunciation other) {
        if (other == null) {
            throw new IllegalArgumentException("Other pronunciation cannot be null.");
        }

        // Find the index of the final stressed vowel in both pronunciations
        int thisStressedIndex = this.findFinalStressedVowelIndex();
        int otherStressedIndex = other.findFinalStressedVowelIndex();

        // Get the list of phonemes for both pronunciations
        List<IPhoneme> thisPhonemes = this.getPhonemes();
        List<IPhoneme> otherPhonemes = other.getPhonemes();

        // Ensure both pronunciations have a stressed vowel to compare from
        if (thisStressedIndex == -1 || otherStressedIndex == -1) {
            return false;  // No stressed vowel found in one or both pronunciations
        }

        // Take all phonemes from the last stressed vowel onward in both pronunciations
        List<IPhoneme> thisRhymePart = thisPhonemes.subList(thisStressedIndex, thisPhonemes.size());
        List<IPhoneme> otherRhymePart = otherPhonemes.subList(otherStressedIndex, otherPhonemes.size());

        // If the rhyme parts are of different lengths, they do not rhyme
        if (thisRhymePart.size() != otherRhymePart.size()) {
            return false;
        }

        // Check that all phonemes match (ignoring stress)
        for (int i = 0; i < thisRhymePart.size(); i++) {
            if (!thisRhymePart.get(i).hasSameArpabet(otherRhymePart.get(i))) {
                return false;  // Phonemes don't match, so no rhyme
            }
        }

        return true;  // All phonemes match from the final stressed vowel onward
    }
}
