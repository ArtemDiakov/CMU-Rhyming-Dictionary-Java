# CMU Rhyming Dictionary – Java

A Java rhyming dictionary project that parses the CMU Pronouncing Dictionary and finds words that rhyme based on ARPABET phoneme sequences.

This project was developed for the CS21120 Algorithm Design and Data Structures module at Aberystwyth University.

## Overview

The program represents words, pronunciations and phonemes using Java classes that implement provided interfaces. It loads entries from the CMU Pronouncing Dictionary, stores them in memory using Java collections, and finds rhymes by comparing pronunciation endings from the final stressed vowel onwards.

## Features

- Parses CMU Pronouncing Dictionary lines
- Handles multiple pronunciations for the same word
- Represents ARPABET phonemes and stress values
- Validates vowel/consonant stress rules
- Finds the final stressed vowel in a pronunciation
- Compares pronunciations to determine whether they rhyme
- Searches for all rhyming words in the dictionary
- Includes JUnit tests for phonemes, pronunciations, words, dictionary parsing and rhyme detection
- Includes a simple command-line interface for loading a dictionary and searching for rhymes

## Technologies Used

- Java
- Java Collections Framework
- JUnit 5
- IntelliJ IDEA

## Data Structures Used

- `ArrayList` for ordered phoneme sequences in a pronunciation
- `HashSet` for storing unique pronunciations of a word
- `HashMap` for fast dictionary lookup by word spelling

## How Rhyming Works

Two pronunciations are considered to rhyme if they contain the same phoneme sequence from the final stressed vowel onwards. Stress is ignored during the final phoneme comparison.

For example, `cat` and `bat` rhyme because their final stressed vowel and following phonemes match.

## Running the Project

1. Clone the repository:

   ```bash
   git clone https://github.com/ArtemDiakov/CMU-Rhyming-Dictionary-Java.git

2. Open the project in IntelliJ IDEA or another Java IDE.

3. Download the CMU Pronouncing Dictionary file separately.

4. Place the dictionary file in the project root or update the dictionary path used by the tests/CLI.

5. Run the JUnit tests or launch RhymeDictionaryCLI.

## Notes

The assignment framework included provided interfaces and JUnit tests. The solution classes and command-line interface were implemented by me.
