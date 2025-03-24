package com.example.groupproject;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.groupproject.dataStreamFunctions.UserActions;

/**
 * Tests the UserActions class which is responsible for handling user interactions in the application.
 * This class includes tests for both getter and setter methods, ensuring that data is properly managed and updated.
 *
 * @author ${Muhammad Arslan Amjad Qureshi}
 * @studentId ${u7632488}
 */


/**
 * The UserActionsTest class is designed to rigorously test the functionality of the UserActions class.
 * It does so by validating the getter, setter, and toString() methods using predefined test data, ensuring correctness and consistency in behaviour
 * Suggested and collaborated with teammate to implement it showcasing teamwork.
 * @author ${Tohfa Siddika Barbhuiya}-co authored
 * @studentId :- ${u7665856}
 */
public class UserActionsTest {
    // Instance of UserActions to be used in tests
    private UserActions userActions;

    // Test data fields
    private final String userID = "Coles Management";
    private final String typeAction = "announcement";
    private final String content = "Coles added 35 free bakery items";
    private final long timeMarker = System.currentTimeMillis();

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes a new instance of UserActions with predefined test data.
     */
    @Before
    public void setUp() {
        userActions = new UserActions(userID, typeAction, content, timeMarker);
    }

    /**
     * Tests the getter methods of the UserActions class.
     * Asserts that each getter returns the correct value as set in the setUp method.
     */
    @Test
    public void testGetters() {
        assertEquals("UserID should match", userID, userActions.getUserId());
        assertEquals("TypeAction should match", typeAction, userActions.getTypeAction());
        assertEquals("Content should match", content, userActions.getContent());
        assertEquals("TimeMarker should match", timeMarker, userActions.getTimeMarker());
    }

    /**
     * Tests the setter methods of the UserActions class.
     * Modifies the instance properties using setters and then checks if the getters return the updated values.
     */
    @Test
    public void testSetters() {
        String newUserID = "newUser";
        String newTypeAction = "question";
        String newContent = "Updated content";
        long newTimeMarker = System.currentTimeMillis();

        userActions.setUserId(newUserID);
        userActions.setTypeAction(newTypeAction);
        userActions.setContent(newContent);
        userActions.setTimeMarker(newTimeMarker);

        assertEquals("UserID should be updated", newUserID, userActions.getUserId());
        assertEquals("TypeAction should be updated", newTypeAction, userActions.getTypeAction());
        assertEquals("Content should be updated", newContent, userActions.getContent());
        assertEquals("TimeMarker should be updated", newTimeMarker, userActions.getTimeMarker());
    }

    /**
     * Tests the toString method of the UserActions class.
     * Asserts that the string representation of the user action is formatted correctly.
     */
    @Test
    public void testToString() {
        String expectedString = userID + ',' + typeAction + "," + content + "," + timeMarker;
        assertEquals("toString should format correctly", expectedString, userActions.toString());
    }
}
