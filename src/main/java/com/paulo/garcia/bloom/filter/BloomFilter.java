package com.paulo.garcia.bloom.filter;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import static java.nio.charset.Charset.forName;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.security.MessageDigest.getInstance;

public class BloomFilter {

    private BitSet bitset;
    private int bitSetSize;

    final Charset charset = forName(valueOf(UTF_8));
    final String MD5_HASH_TYPE = "MD5";
    final MessageDigest digestFunction;

    public BloomFilter(int bitSetSize) throws NoSuchAlgorithmException {
        this.bitSetSize = bitSetSize;
        this.bitset = new BitSet(this.bitSetSize);
        digestFunction = getInstance(MD5_HASH_TYPE);
    }

    public void addWord(String word) {
        byte[] wordToBytes = word.getBytes(charset);
        int hash = generateFourByteHash(wordToBytes);

        bitset.set(abs(hash % bitSetSize), true);
    }

    public boolean isContainsWord(String word) {
        byte[] wordToBytes = word.getBytes(charset);
        int hash = generateFourByteHash(wordToBytes);

        return !bitset.get(abs(hash % bitSetSize))
                ? false
                : true;

    }

    public int generateFourByteHash(byte[] data) {
        byte[] digestFunction = this.digestFunction.digest(data);
        
        int hashCode = 0;
        for (int j = 0; j < 4; j++) {
            hashCode <<= 8;
            hashCode |= ((int) digestFunction[j]) & 0xFF;
        }
        return hashCode;
    }

}
