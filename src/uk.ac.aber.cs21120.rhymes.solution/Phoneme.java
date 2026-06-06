package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;

public class Phoneme implements IPhoneme {

    // The ARPABET phoneme (e.g., IH1, AE0)
    private final Arpabet phoneme;

    // The stress value, or -1 if the phoneme is consonant
    private final int stress;

    /**
     * Constructor for Phoneme.
     *
     * @param phoneme the ARPABET phoneme
     * @param stress the stress value (-1, 0, 1, or 2)
     * @throws IllegalArgumentException if stress is invalid or incompatible with the phoneme
     */
    public Phoneme(Arpabet phoneme, int stress) {
        // Check if phoneme is null
        if (phoneme == null) {
            throw new IllegalArgumentException("Phoneme cannot be null.");
        }

        // Validate the stress value
        if (stress < -1 || stress > 2) {
            throw new IllegalArgumentException("Stress must be in the range -1 to 2.");
        }

        // Validate that consonants have stress value of -1
        if (stress != -1 && !phoneme.isVowel()) {
            throw new IllegalArgumentException("Non-vowel phonemes cannot have a stress other than -1.");
        }

        // Validate that vowels must have a stress value of 0, 1, or 2
        if (stress == -1 && phoneme.isVowel()) {
            throw new IllegalArgumentException("Vowel phonemes must have a stress of 0, 1, or 2.");
        }

        // Set the phoneme and stress
        this.phoneme = phoneme;
        this.stress = stress;
    }

    /**
     * Returns the ARPABET phoneme.
     * @return the ARPABET phoneme
     */
    @Override
    public Arpabet getArpabet() {
        return phoneme;
    }

    /**
     * Returns the stress value of the phoneme.
     * @return the stress value, or -1 if the phoneme is not a vowel
     */
    @Override
    public int getStress() {
        return stress;
    }

    /**
     * Compares the ARPABET values of two phonemes, ignoring stress.
     * @param other the other phoneme to compare against
     * @return true if the ARPABET values are the same
     * @throws IllegalArgumentException if the other phoneme is null
     */
    @Override
    public boolean hasSameArpabet(IPhoneme other) {
        if (other == null) {
            throw new IllegalArgumentException("Other phoneme cannot be null.");
        }
        return this.phoneme == other.getArpabet();
    }
}
