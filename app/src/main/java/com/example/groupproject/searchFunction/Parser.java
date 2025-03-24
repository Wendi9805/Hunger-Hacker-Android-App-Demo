package com.example.groupproject.searchFunction;

import java.util.List;
/**
 * @author Ke Wen
 * @studentId u7588635
 */
/**
 * grammar rules
 * <exp> ::= <term> | <exp> "+" <term>
 * <term> ::= <factor> | <term> "-" <factor>
 * <factor> ::= <string>
 * <string> ::= <productName> | <address> | <provider>
 */
public class Parser {
    public static class IllegalProductException extends IllegalArgumentException {
        public IllegalProductException(String errorMessage) { super(errorMessage); }
    }

    private TokenIterator tokenizer;

    public Parser(String input) {
        this.tokenizer = new Tokenizer(input);
    }

    public Exp performParse() {
        return parseExp();
    }

    private Exp parseExp() {
        Exp result = parseTerm();
        while (tokenizer.hasNext()) {
            Token currentToken = tokenizer.current();
            if (currentToken.getType() == Token.Type.AND) {
                tokenizer.next(); // Consume '+'
                Exp right = parseTerm();
                result = new AndExp(result, right);
            } else {
                break;
            }
        }
        return result;
    }

    private Exp parseTerm() {
        Exp result = parseFactor();
        while (tokenizer.hasNext()) {
            Token currentToken = tokenizer.current();
            if (currentToken.getType() == Token.Type.NOT) {
                tokenizer.next(); // Consume '-'
                Exp right = parseFactor();
                result = new NotExp(result, right);
            } else {
                break;
            }
        }
        return result;
    }

    private Exp parseFactor() {
        if (!tokenizer.hasNext()) {
            return new StringExp(tokenizer.current().getToken());
        }
        Token currentToken = tokenizer.current();
        tokenizer.next();
        if (currentToken.getType() == Token.Type.STRING) {
            return new StringExp(currentToken.getToken());
        } else {
            throw new IllegalProductException("Unexpected token: " + currentToken.getToken());
        }
    }
}