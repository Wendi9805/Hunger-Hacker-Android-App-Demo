package com.example.groupproject.searchFunction;
/**
 * @author Ke Wen
 */
public interface TokenIterator {
    boolean hasNext();
    void next();
    Token current();
}
