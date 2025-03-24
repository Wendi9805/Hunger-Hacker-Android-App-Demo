package com.example.groupproject.searchFunction;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Wendi Fan & Ke Wen
 */
public class Tokenizer implements TokenIterator{
    private String buffer;
    private Token currentToken;

    public Tokenizer(String input) {
        buffer = input.toLowerCase();
        next();
    }

    public void next() {
        buffer = buffer.trim();
        if (buffer.isEmpty()) {
            currentToken = new Token("", Token.Type.EOF);
            return;
        }

        char firstChar = buffer.charAt(0);
        if (firstChar == '+') {
            currentToken = new Token(Character.toString(firstChar), Token.Type.AND);
            buffer = buffer.substring(1);
        } else if (firstChar == '-' && (buffer.length() > 1 && !Character.isWhitespace(buffer.charAt(1)))) {
            currentToken = new Token("-", Token.Type.NOT);
            buffer = buffer.substring(1);
        } else {
            int nextTokenIndex = findNextOperator(buffer);
            String tokenValue = buffer.substring(0, nextTokenIndex);
            currentToken = new Token(tokenValue, Token.Type.STRING);
            buffer = buffer.substring(nextTokenIndex);
        }
    }

    public Token current() {
        return currentToken;
    }

    public boolean hasNext() {
        return !this.buffer.isEmpty();
    }

    private int findNextOperator(String str) {
        for (int i = 1; i < str.length(); i++) { // Start from 1 to allow '-' at start to be part of the string
            char c = str.charAt(i);
            if (c == '+' || c == '-' ) {
                return i;
            }
        }
        return str.length();
    }

}


