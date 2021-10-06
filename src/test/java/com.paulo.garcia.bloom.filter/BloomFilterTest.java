package com.paulo.garcia.bloom.filter;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;

public class BloomFilterTest {

    private BloomFilter bloomFilter;
    private final int SET_BIT_SIZE = 1000000;

    private static final String URL_WORDS_FILE = "src/main/resources/words.txt";

    @Before
    public void setup() throws NoSuchAlgorithmException {
        bloomFilter = new BloomFilter(SET_BIT_SIZE);
    }

    @Test
    public void shouldAddAllAndCheckIfContainsWordsInSetBit() throws Exception {
        addWords();
        shouldSuccessWhenCheckValidWordInSetBit();
    }

    @Test
    public void shouldFailsWhenCheckIfContainsNotAddedWord() throws Exception {
        addWords();
        shouldFailsWhenCheckInvalidWord();
    }

    @Test
    public void shouldGenerateHashFromSameWord() {

        String word = "ATPases";

        int hashOne = bloomFilter.generateFourByteHash(word.getBytes(UTF_8));
        int hashTwo = bloomFilter.generateFourByteHash(word.getBytes(UTF_8));

        assertEquals(hashOne, hashTwo);
    }

    @Test
    public void shouldGenerateHashFromOtherWords() {
        String wordOne = "ATPases";
        int hashOne = bloomFilter.generateFourByteHash(wordOne.getBytes(UTF_8));

        String wordTwo = "ASL";
        int hashTwo = bloomFilter.generateFourByteHash(wordTwo.getBytes(UTF_8));

        assertNotSame(hashTwo, hashOne);
    }

    private void shouldSuccessWhenCheckValidWordInSetBit() {
        assert (bloomFilter.isContainsWord("ABA"));
        assert (bloomFilter.isContainsWord("Sanhedrim"));
        assert (bloomFilter.isContainsWord("Laurasia"));
        assert (bloomFilter.isContainsWord("Grimke's"));
    }

    private void shouldFailsWhenCheckInvalidWord() {
        assertFalse (bloomFilter.isContainsWord("NO VALID WORD!"));
    }

    private void addWords() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(URL_WORDS_FILE))) {

            br.lines().forEach(word -> {
                bloomFilter.addWord(word);
                assert (bloomFilter.isContainsWord(word));
            });

        }
    }

}