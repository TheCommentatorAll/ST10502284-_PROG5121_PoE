/**
 * Unit Tests for Login Logic.
 * Test data sourced from Part 1 Requirement Table (Page 9).
 * Reference: Google Gemini (2026) ‘Response to query regarding JUnit implementation 
 * and test data mapping for Java login validation’. 26 March. 
 * Available at: https://gemini.google.com/ (Accessed: 26 March 2026).
 * See reference index [1] for more
 */
package poe.part1.logic;

import static org.junit.Assert.*;
import org.junit.Test;

public class LoginTest {

    // Create one instance of Login to use for all tests
    Login login = new Login();

    @Test
    public void testCheckUserName() {
        //"kyl_1" should return true
        assertTrue(login.checkUserName("kyl_1"));

        //"kyle!!!!!!" should return false
        assertFalse(login.checkUserName("kyle!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexity() {
        //"Ch&&sec@ke99!" should return true
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));

        //"password" should return false
        assertFalse(login.checkPasswordComplexity("password"));
    }
    
    @Test
    public void testCellPhone(){
        
        //+27838968976 should return true
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
        
        //08966553 should return false
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testLoginUser() {
        // Setup: We MUST register a user first so the 'stored' variables aren't empty
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");

        // Test 1: Correct credentials (Expected: true)
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));

        // Test 2: Incorrect credentials (Expected: false)
        assertFalse(login.loginUser("wrong_user", "wrong_pass"));
    }

    @Test
    public void testReturnLoginStatus() {
        // 1. Setup: Register a user with specific names
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");

        // 2. Define the string
        String expected = "Welcome John Doe, it is great to see you again.";

        // 3. Run the login and get the status message
        boolean loginSuccess = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        String actual = login.returnLoginStatus(loginSuccess);

        // 4. Compare them
        assertEquals(expected, actual);
    }
}
