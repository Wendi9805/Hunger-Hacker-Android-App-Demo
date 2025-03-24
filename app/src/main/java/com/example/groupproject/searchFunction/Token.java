package com.example.groupproject.searchFunction;

/**
 * This class is the instance of token and related method we need
 *
 * @author ${Wendi Fan}
 * @studentId ${u7041989}
 */

public class Token {
    public enum Type {AND, NOT, STRING,EOF}
    // Use EOF (End Of File) token to indicate the end
    private final String token;
    private final Type type;

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

    //use to test if two tokens are the same
    @Override
    public boolean equals(Object other) {
        if (this == other) return true; // Same hashcode.
        if (!(other instanceof Token)) return false; // Null or not the same type.
        return this.type == ((Token) other).getType() && this.token.equals(((Token) other).getToken()); // Values are the same.
    }

    /**
     * The following exception should be thrown if a tokenizer attempts to tokenize something that is not of one
     * of the types of tokens.
     */
    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }
}
