package com.allanweber.customers.infrastructure;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.springframework.stereotype.Service;

@Service
public class PasswordGenerator {

    private static final CharacterRule ALPHABETS = new CharacterRule(EnglishCharacterData.Alphabetical);
    private static final CharacterRule DIGITS = new CharacterRule(EnglishCharacterData.Digit);

    private final org.passay.PasswordGenerator generator;

    public PasswordGenerator() {
        this.generator = new org.passay.PasswordGenerator();
    }

    public String generate() {
        return generator.generatePassword(8, ALPHABETS, DIGITS);
    }
}
