package com.example.groupproject.searchFunction;
/**
 * @author Ke Wen
 * @studentId u7588635
 */
public interface TokenIterator {
    boolean hasNext();
    void next();
    Token current();
}
