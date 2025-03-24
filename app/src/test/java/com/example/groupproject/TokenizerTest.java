package com.example.groupproject;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.groupproject.searchFunction.Token;
import com.example.groupproject.searchFunction.Tokenizer;
/**
 * @author Ke Wen
 * @studentId u7588635
 */
public class TokenizerTest {
    @Test
    public void testEmptyInput() {
        Tokenizer tokenizer = new Tokenizer("");
        assertFalse(tokenizer.hasNext());
        assertEquals("", tokenizer.current().getToken());
        assertEquals(Token.Type.EOF, tokenizer.current().getType());
    }

    @Test
    public void testStringsWithOperators() {
        Tokenizer tokenizer = new Tokenizer("hello+world-search");
        assertTrue(tokenizer.hasNext());
        assertEquals("hello", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals("+", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals("world", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals("-", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals("search", tokenizer.current().getToken());
    }

    @Test
    public void testLeadingAndTrailingOperators() {
        Tokenizer tokenizer = new Tokenizer("+hello-");
        assertTrue(tokenizer.hasNext());
        assertEquals("+", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals("hello", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals("-", tokenizer.current().getToken());
    }

    @Test
    public void testConsecutiveOperators() {
        Tokenizer tokenizer = new Tokenizer("hello++world--search");
        assertTrue(tokenizer.hasNext());
        assertEquals("hello", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals("+", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals("+", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals("world", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals("-", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals("-", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals("search", tokenizer.current().getToken());
    }

}
